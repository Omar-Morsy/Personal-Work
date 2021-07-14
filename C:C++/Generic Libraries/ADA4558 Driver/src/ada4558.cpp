/* INCLUDES *********************************************************************************************************************/
#include "ada4558.h"

/*FUNCTION DEFINITION ***********************************************************************************************************/
bool ada4558_read_eeprom(uint8_t nad, uint8_t address, uint8_t * buffer, uint8_t bufferSize) {
    //Clear the buufer
    for(int i = 0; i < bufferSize; i++){
        buffer[i] = 0;
    }

    //Construct the data bytes
    uint8_t request[8] = {(uint8_t)(0x80 + nad), 0x02, 0x05, (uint8_t)address, 0xFF, 0xFF, 0xFF, 0xFF};

    //Write to the lin the diagnostic frame using 0x3c as pid (Diagnostic frame always use classic checksum)
    lin_write(0x3C, request, 8, true);
    delay(RESPONSE_FRAME_DELAY);

    //Read the response of the lin slave and return the response array including the checksum and the checksum validation
    return lin_read(0x7D, buffer, 8, true);
}

bool ada4558_write_password(uint8_t nad) {
    //Construct the data bytes
    uint8_t request[8] = {(uint8_t)(0x80 + nad), 0x06, 0x08, 0x01, 0x23, 0x45, 0x67, 0x89};

    //Write to the lin the diagnostic frame using 0x3c as pid (Diagnostic frame always use classic checksum)
    lin_write(0x3C, request, 8, true);
    delay(RESPONSE_FRAME_DELAY);

    //Read the response of the lin slave and return the response array including the checksum and the checksum validation
    uint8_t password_validation[8] = {(uint8_t)(0x80 + nad), 0x01, 0x48, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF};
    uint8_t buffer [9];
    bool checksum = lin_read(0x7D, buffer, 8, true);

    //Check for a valid checksum
    if (!checksum) {
        return false;
    }

    //Check for a positive response
    for (int i = 0; i < 8; i++) {
        if (buffer[i] != password_validation[i]) {
            return false;
        }
    }
    return true;
}

bool ada4558_write_testMode(uint8_t nad, bool enable) { 
    //Construct the data bytes
    uint8_t request[8] = {(uint8_t)(0x80 + nad), 0x02, 0x07, (uint8_t)enable, 0xFF, 0xFF, 0xFF, 0xFF};

    //Write to the lin the diagnostic frame using 0x3c as pid (Diagnostic frame always use classic checksum)
    lin_write(0x3C, request, 8, true);
    delay(RESPONSE_FRAME_DELAY);

    //Read the response of the lin slave and return the response array including the checksum and the checksum validation
    uint8_t testMode_validation[8] = {(uint8_t)(0x80 + nad), 0x02, 0x47, (uint8_t)enable, 0xFF, 0xFF, 0xFF, 0xFF};
    uint8_t buffer [9];
    bool checksum = lin_read(0x7D, buffer, 8, true);

    //Check for a valid checksum
    if (!checksum) {
        return false;
    }

    //Check for a positive response
    for (int i = 0; i < 8; i++) {
        if (buffer[i] != testMode_validation[i]) {
            return false;
        }
    }
    return true;
}

static bool ada4558_write_eeprom(uint8_t nad, uint8_t address, uint8_t * data) {
    //Construct the data bytes
    uint8_t request[8] = {(uint8_t)(0x80 + nad), 0x06, 0x04, address, data[0], data[1], data[2], data[3]};

    //Write to the lin the diagnostic frame using 0x3c as pid (Diagnostic frame always use classic checksum)
    lin_write(0x3C, request, 8, true);
    delay(RESPONSE_FRAME_DELAY);

    //Read the response of the lin slave and return the response array including the checksum and the checksum validation
    uint8_t writeEeprom_validation[8] = {(uint8_t)(0x80 + nad), 0x03, 0x44, address, 0x99, 0xFF, 0xFF, 0xFF};
    uint8_t buffer [9];
    bool checksum = lin_read(0x7D, buffer, 8, true);

    //Check for a valid checksum
    if (!checksum) {
        return false;
    }

    //Check for a positive response
    for (int i = 0; i < 8; i++) {
        if (writeEeprom_validation[i] != 0x99) {
            if (buffer[i] != writeEeprom_validation[i]) {
                return false;
            }
        }
    }
    return true;
}

uint8_t ada4558_check_eepromStatus(uint8_t nad) {
    //Construct the data bytes
    uint8_t request[8] = {(uint8_t)(0x80 + nad), 0x02, 0x02, 0xBB, 0xFF, 0xFF, 0xFF, 0xFF};

    //Write to the lin the diagnostic frame using 0x3c as pid (Diagnostic frame always use classic checksum)
    lin_write(0x3C, request, 8, true);
    delay(RESPONSE_FRAME_DELAY);

    //Read the response of the lin slave and return the response array including the checksum and the checksum validation
    uint8_t buffer [9];
    bool checksum = lin_read(0x7D, buffer, 8, true);

    //Check for a valid checksum
    if (!checksum) {
        return 0xFF;
    } else {
        return buffer[3];
    }
}

bool ada4558_write_eeprom_nvmCacheDisabled(uint8_t nad, uint8_t address, uint8_t * data, uint8_t dataSize) {
    //Check for the data if less or greater than 4
    if (dataSize != 4){
        return false;
    }

    //Send the test password frame and check for a negative response
    bool recv = false;
    recv = ada4558_write_password(nad);
    if(!recv){return false;}

    //Send the test mode frame (1) to avoid nvm caching (config mode) and check for a negative response
    recv = ada4558_write_testMode(nad, true);
    if(!recv){return false;}

    //Send the write eeprom frame and check for a negative response
    recv = ada4558_write_eeprom(nad, address, data);
    if(!recv){return false;}
    delay(3000);

    //Send the test password frame and check for a negative response
    recv = ada4558_write_password(nad);
    if(!recv){return false;}

    //Send the test mode frame (0) to be back to normal operation mode and check for a negative response
    recv = ada4558_write_testMode(nad, false);
    if(!recv){return false;}

    //In case all frames receive positive response
    return true;
}

bool ada4558_disable_sleep(uint8_t nad, uint8_t inad) {
    //Construct the data bytes
    uint8_t data[4] = {130, 0, inad, nad};

    //write to the eeprom and return the response (sending frame twice in case module in sleep mode) first frame is a wake up
    ada4558_write_eeprom_nvmCacheDisabled(nad, 13, data, 4);
    return ada4558_write_eeprom_nvmCacheDisabled(nad, 13, data, 4);
}

bool ada4558_enable_sleep(uint8_t nad, uint8_t inad) {
    //Construct the data bytes
    uint8_t data[4] = {2, 0, inad, nad};

    //write to the eeprom and return the response
    return ada4558_write_eeprom_nvmCacheDisabled(nad, 13, data, 4);
}

bool ada4558_update_function_id(uint8_t nad, uint8_t functionIdByte1, uint8_t functionIdByte2) {
    //Construct the data bytes
    uint8_t data[4] = {0, 1, functionIdByte1, functionIdByte2};

    //write to the eeprom and return the response
    return ada4558_write_eeprom_nvmCacheDisabled(nad, 27, data, 4);
}

bool ada4558_update_supplier_id(uint8_t nad, uint8_t supplierIdByte1, uint8_t suplierIdByte2) {
    //Construct the data bytes
    uint8_t data[4] = {supplierIdByte1, suplierIdByte2, 64, 64};

    //write to the eeprom and return the response
    return ada4558_write_eeprom_nvmCacheDisabled(nad, 25, data, 4);
}

bool ada4558_update_nad(uint8_t nad, uint8_t newNad) {
    //Construct the data bytes
    uint8_t data[4] = {130, 0, 0, newNad};

    //write to the eeprom and return the response
    return ada4558_write_eeprom_nvmCacheDisabled(nad, 13, data, 4);
}

bool ada4558_update_linPid1(uint8_t nad, uint8_t linpid1) {
    //Construct the data bytes
    uint8_t data[4] = {100, linpid1, 64, 64};

    //write to the eeprom and return the response
    return ada4558_write_eeprom_nvmCacheDisabled(nad, 24, data, 4);
}

uint8_t ada4558_get_linPid1(uint8_t nad) {
    //Read address 24 where linpid is stored
    uint8_t buffer[9];
    bool f = ada4558_read_eeprom(nad, 24, buffer, 9);

    //Return the linpid1 in case of a correct reading and return 0 if not
    if (!f) {
        return 0;
    }
    return buffer[5];
}

uint16_t ada4558_get_sensorData(uint8_t linpid1) {
    //Read the sensor data
    uint8_t buffer[9];
    bool f = lin_read(linpid1, buffer, 4, true);

    //return the sensor data and return 0 in case of any error
    uint8_t sensorData1 = 0;
    uint8_t sensorData2 = 0;
    uint16_t sensorData = 0;
    if (!f){return 0;}
    sensorData1 = buffer[0];
    sensorData2 = (uint8_t)(buffer[1] & 0x0F);
    sensorData = (uint16_t)(sensorData1 | (sensorData2 << 8));
    return sensorData;
}

bool ada4558_update_pgaGain(uint8_t nad) {
    //Construct the data bytes
    uint8_t data[4] = {43, 13, 8, 36};

    //write to the eeprom and return the response
    return ada4558_write_eeprom_nvmCacheDisabled(nad, 11, data, 4);
}