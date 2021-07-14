/* INCLUDES **********************************************************************************************************************/
#include "lin.h"

/*FUNCTION DEFINITION ***********************************************************************************************************/
void lin_init() {
    //begin lin tranciever serial communication
    LIN_SERIAL.begin(LIN_BAUD_RATE);

    //Empty the serial bus to avoid any false bytes
    while(LIN_SERIAL.available()){LIN_SERIAL.read();}
}

static void lin_serialBreak() {
    //End the serial communication
    LIN_SERIAL.end();

    //Transmit low bits > 13bits
    pinMode(TX_PIN, OUTPUT);
    digitalWrite(TX_PIN, LOW);
    unsigned long int brkend = (1000000UL/((unsigned long int)LIN_BAUD_RATE));
    unsigned long int brkbegin = brkend*LIN_BREAK_DURATION;
    if (brkbegin > 16383){
        delay(brkbegin/1000);
    } else{
        delayMicroseconds(brkbegin);
    } 

    //Break delimiter
    digitalWrite(TX_PIN, HIGH);

    //Delay required after break (delayMicroseconds unreliable above 16383)
    if (brkend > 16383){
        delay(brkend/1000);
    } else {
        delayMicroseconds(brkend);
    }

    //Start the serial communication for transmitting and receiving
    LIN_SERIAL.begin(LIN_BAUD_RATE);
    LIN_SERIAL.flush();
}

static uint8_t lin_calcParity(uint8_t pid) {
    //Parity calculation
    byte p0 = BIT(pid,0) ^ BIT(pid,1) ^ BIT(pid,2) ^ BIT(pid,4);
    byte p1 = ~(BIT(pid,1) ^ BIT(pid,3) ^ BIT(pid,4) ^ BIT(pid,5));
    return (p0 | (p1<<1))<<6;
}

static uint8_t lin_calcChecksum(uint8_t pid, const uint8_t * data, uint8_t data_size, bool isClassic) {
    //Check if enhanced or classic checksum
    int sum = 0;
    if (isClassic) {
        int sum = 0;
    } else {
        sum = pid;
    }

    //Add all bytes in the data
    for (int i=0; i < data_size; i++) {
        sum += data[i];
    }

    //calculate the appropriate checksum
    while(sum > CHECKSUM_CAL) {
        sum -= CHECKSUM_CAL;
    }

    return (uint8_t)(CHECKSUM_CAL - sum);
}

static bool lin_validateChecksum(uint8_t pid, const uint8_t * data, uint8_t data_size, uint8_t checksum, bool isClassic) {
    //calculate expected checksum
    uint8_t vChecksum = lin_calcChecksum(pid, data, data_size, isClassic);

    //check for the validity of the checksum
    if (checksum == vChecksum) {
        return true;
    } else {
        return false;
    }
}

void lin_write(uint8_t pid, const uint8_t * data, uint8_t data_size, bool isClassic) {
    //print to serial
    #if SERIAL_DBG
    Serial.println("Requesting.....");
    #endif

    //calculate the checksum
    uint8_t checksum = lin_calcChecksum(pid, data, data_size, isClassic);

    //Calculate the parity and add it to the pid
    uint8_t pidByte = (pid&0x3f) | lin_calcParity(pid);

    //Serial break
    lin_serialBreak();

    //Sync field byte
    LIN_SERIAL.write(0x55);

    //pid value
    LIN_SERIAL.write(pidByte);

    //Send the message
    for(int i=0; i < data_size; i++) {
        LIN_SERIAL.write(data[i]);
    }

    //Send the checksum
    LIN_SERIAL.write(checksum);

    //print to serial
    #if SERIAL_DBG
    Serial.println("Done.\n");
    #endif
}

bool lin_read(uint8_t pid, uint8_t * buffer, uint8_t buffer_size, bool isClassic) {
    //print to serial
    #if SERIAL_DBG
    Serial.println("Reading.....");
    #endif

    //Ensure an empty buffer
    for(int i=0; i < buffer_size; i++) {
        buffer[i] = 0;
    }
    
     //Calculate the parity and add it to the pid
    uint8_t pidByte = (pid&0x3f) | lin_calcParity(pid);

    //Serial break
    lin_serialBreak();
    LIN_SERIAL.flush();

    //Sync field byte
    LIN_SERIAL.write(0x55);

    //pid value
    LIN_SERIAL.write(pidByte);

    //Set the tx pin as an input to avoid hearing yourself
    pinMode(TX_PIN, INPUT);
    digitalWrite(TX_PIN, LOW);
    delay(RESPONSE_SPACE_DELAY);

    //Ensure we are not hearing ourselves
    unsigned long timeout = millis() + LIN_TIMEOUT_MS;
    while((LIN_SERIAL.read() != pid) && (millis() < timeout));
    delay(100);

    //check for a timeout 
    if (millis() > timeout) {
        return false;
    }

    //Store the received bytes in the buffer
    for (int i=0; (i < buffer_size + 1) || (LIN_SERIAL.available()); i++) {
        buffer[i] = LIN_SERIAL.read();
        delay(1);
    }

    //Validate checksum
    uint8_t checksum = buffer[buffer_size];
    bool validate_checksum = lin_validateChecksum(pid, buffer, buffer_size, checksum, isClassic);

    //print the buffer to serial monitor
    #if SERIAL_DBG
    for (int i=0; i < buffer_size; i++) {
        Serial.println(buffer[i]);
    }
    Serial.println(checksum);    
    #endif

    //print to serial
    #if SERIAL_DBG
    Serial.print("Checksum = ");
    Serial.println(validate_checksum);
    Serial.println("Done.\n");
    #endif

    return validate_checksum;
}


