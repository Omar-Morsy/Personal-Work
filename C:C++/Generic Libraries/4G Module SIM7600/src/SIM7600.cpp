/* INCLUDES ***************************************************************************************************************/
#include "SIM7600.h"

/* GLOCAL VARIABLES *******************************************************************************************************/
static SIM7600 * Cellular_Module;

/*FUNCTION DEFINITION *****************************************************************************************************/
bool static send_command(String request, String expectedResponse, uint16_t timeout_ms) {
    //Flush Serial
    unsigned long timeout = millis() + timeout_ms;
    while((Cellular_Module->cellular_serial.read() != -1) && (millis() < timeout));

    //Send at command and initiate the timeout
    Cellular_Module->cellular_serial.print(request);
    Cellular_Module->cellular_serial.print("\r");
    while((Cellular_Module->cellular_serial.read() != '\r') && (millis() < timeout));

    //Check if the timeout has been fired
    if (millis() > timeout) {
        return false;
    }

    //Wait for response
    char terminate;
    do{
        terminate = Cellular_Module->cellular_serial.read(); 
    }while(((int)terminate == (int)-1) && (millis() < timeout));
    delay(1);

    //Check if the timeout has been fired
    if ((millis() > timeout) || (terminate == (int)-1)) {
        #if DEBUG
        Serial.println("No response");
        #endif
        return false;
    }

    //Read the response from the cellular module (remove all spaces)
    String response = String(terminate);
    while((Cellular_Module->cellular_serial.available()) && (millis() < timeout)) {
        char c = (char)Cellular_Module->cellular_serial.read();
        if (((int)c != (int)10) && ((int)c != (int)13) && ((int)c != (int)-1)) {
            response += String(c);
        }
        delay(1);
    }
    response.trim();

    //Check if the timeout has been fired
    if (millis() > timeout) {
        return false;
    }

    //Print to serial
    #if DEBUG
    Serial.println("\n");
    Serial.print("Request Command : ");Serial.println(request);
    Serial.print("Expected Response : ");Serial.println(expectedResponse);
    Serial.print("Actual Response : ");Serial.println(response);
    delay(100);
    #endif

    //Compare between actual and expected response
    if (expectedResponse.equals(response)) {
        return true;
    } else {
        return false;
    }
}

String static request_data_command(String request, uint16_t timeout_ms) {
    //Flush Serial
    unsigned long timeout = millis() + timeout_ms;
    while((Cellular_Module->cellular_serial.read() != -1) && (millis() < timeout));

    //Send at command and initiate the timeout
    Cellular_Module->cellular_serial.print(request);
    Cellular_Module->cellular_serial.print("\r");
    while((Cellular_Module->cellular_serial.read() != '\r') && (millis() < timeout));

    //Check if the timeout has been fired
    if (millis() > timeout) {
        #if DEBUG
        Serial.println("No response");
        #endif
        return "";
    }

    //Wait for response
    char terminate;
    do{
        terminate = Cellular_Module->cellular_serial.read(); 
    }while(((int)terminate == (int)-1) && (millis() < timeout));
    delay(1);

    //Check if the timeout has been fired
    if (millis() > timeout) {
        return "";
    }

    //Read the response from the cellular module (remove all spaces)
    String response = String(terminate);
    while(Cellular_Module->cellular_serial.available() && millis() < timeout) {
        char c = (char)Cellular_Module->cellular_serial.read();
        if (((int)c != (int)10) && ((int)c != (int)13) && ((int)c != (int)-1)) {
            response += String(c);
        }
        delay(1);
    }
    response.trim();

    //Check if the timeout has been fired
    if (millis() > timeout) {
        return "";
    }

    //Print to serial
    #if DEBUG
    Serial.println("\n");
    Serial.print("Request Command : ");Serial.println(request);
    Serial.print("Actual Response : ");Serial.println(response);
    delay(100);
    #endif

    //return the response
    return response;
}

String static request_data_command(String request, int requestEnd, uint16_t timeout_ms) {
    //Flush Serial
    unsigned long timeout = millis() + timeout_ms;
    while((Cellular_Module->cellular_serial.read() != -1) && (millis() < timeout));

    //Send at command and initiate the timeout
    Cellular_Module->cellular_serial.print(request);
    Cellular_Module->cellular_serial.write(requestEnd);
    Cellular_Module->cellular_serial.print("\r");
    while((Cellular_Module->cellular_serial.read() != '\r') && (millis() < timeout));

    //Check if the timeout has been fired
    if (millis() > timeout) {
        #if DEBUG
        Serial.println("No response");
        #endif
        return "";
    }

    //Wait for response
    char terminate;
    do{
        terminate = Cellular_Module->cellular_serial.read(); 
    }while(((int)terminate == (int)-1) && (millis() < timeout));
    delay(1);

    //Check if the timeout has been fired
    if (millis() > timeout) {
        return "";
    }

    //Read the response from the cellular module (remove all spaces)
    String response = String(terminate);
    while(Cellular_Module->cellular_serial.available() && millis() < timeout) {
        char c = (char)Cellular_Module->cellular_serial.read();
        if (((int)c != (int)10) && ((int)c != (int)13) && ((int)c != (int)-1)) {
            response += String(c);
        }
        delay(1);
    }
    response.trim();

    //Check if the timeout has been fired
    if (millis() > timeout) {
        return "";
    }

    //Print to serial
    #if DEBUG
    Serial.println("\n");
    Serial.print("Request Command : ");Serial.println(request);
    Serial.print("Actual Response : ");Serial.println(response);
    delay(100);
    #endif

    //return the response
    return response;
}

bool cellular_init(SIM7600 * Cellular) {
    //Assign given param to the class param
    Cellular_Module = Cellular;

    //Begin the serial communication
    Cellular_Module->cellular_serial.begin(CELLULAR_BAUD_RATE);
    pinMode(Cellular_Module->cellular_reset_pin, OUTPUT);
    digitalWrite(Cellular_Module->cellular_reset_pin, LOW);
    delay(3000);
    digitalWrite(Cellular_Module->cellular_reset_pin, HIGH);
    delay(10000);

    //Send AT command to check if the module connection is successful
    bool check = cellular_at_check();
    if (check == false) {return false;}

    //Turn off the Echo
    check = false;
    check = cellular_disable_echo();
    if (check == false) {return false;}

    //Reset all module data
    check = false;
    check = cellular_reset();
    if (check == false) {return false;}

    //Turn off the Echo, 2nd after reset
    check = false;
    check = cellular_disable_echo();
    if (check == false) {return false;}

    //Wait for internet connectivity
    check = cellular_check_network();
    if (check == false) {
        check = send_command("AT+CRESET", "OK", COMMAND_TIMEOUT);
        if (check == false) {return false;}
        delay(10000);
        check = cellular_check_network();
        if (check == false) {return false;}
    }

    //Enable the GPRS
    check = false;
    check = cellular_enable_gprs();
    if (check == false) {return false;}

    //HTTP Init
    check = false;
    check = cellular_http_init();
    if (check == false) {return false;}

    //return check
    return true;
}

bool cellular_at_check() {
    //Send AT command to check if the module connection is successful
    unsigned long timeout = millis() + RESPONSE_TIMEOUT;
    bool check = false;
    do {
        String response = request_data_command("AT", COMMAND_TIMEOUT);
        if ((response.equals("OK")) || (response.equals("ATOK"))) {
            check = true;
        }
        delay(500);
    } while((check == false) && (millis() < timeout));
    if (millis() > timeout) {
        return false;
    } else {
        return true;
    }
}

bool cellular_disable_echo() {
    //Send turn off Echo command
    unsigned long timeout = millis() + RESPONSE_TIMEOUT;
    while((send_command("ATE0", "OK", COMMAND_TIMEOUT) == false) && (millis() < timeout)){delay(500);}
    if (millis() > timeout) {
        return false;
    } else {
        return true;
    }
}

String cellular_get_imei() {
    //Send IMEI command
    String response = request_data_command("AT+GSN", COMMAND_TIMEOUT);
    response.replace("OK", "");
    return response;
}

double cellular_get_battVoltage() {
    //Send battery voltage command
    String response = request_data_command("AT+CBC", COMMAND_TIMEOUT);

    //Extract the data from the response string (Structure{batteryPowered, batteryLevel, Voltage, OK})
    String battery = "";
    uint8_t index = 0;
    char terminate = response.charAt(index);
    while (terminate != (char)',') {
        terminate = response.charAt(index);
        index ++;
    }
    terminate = response.charAt(index);
    while (terminate != (char)',') {
        terminate = response.charAt(index);
        index ++;
    }
    terminate = response.charAt(index);
    while (terminate != (char)'V') {
        battery += String(terminate);
        index ++;
        terminate = response.charAt(index);
    }

    //return the battery voltage
    return battery.toDouble();
}

bool cellular_disconnect_sockets() {
    //Send the disable sockets commands and dettach from any network
    send_command("AT+CIPSHUT", "SHUT OK", COMMAND_TIMEOUT);
    send_command("AT+CGATT=1", "OK", COMMAND_TIMEOUT);
    return true;
}

bool cellular_set_apn() {
    //Set bearer profile access point name
    unsigned long timeout = millis() + RESPONSE_TIMEOUT;
    String send = String("AT+CGSOCKCONT=1,\"IP\",");
    send += String('"');
    send += String(Cellular_Module->network_apn);
    send += String('"');
    while((send_command(send, "OK", COMMAND_TIMEOUT) == false) && (millis() < timeout)){delay(500);}
    if (millis() > timeout) {
        return false;
    } else {
        return true;
    }
}

bool cellular_set_transparentMode() {
    //Send the command to set TCP/IP application mode to transparent
    send_command("AT+CIPMODE=1", "OK", COMMAND_TIMEOUT);
    return true;
}

bool cellular_open_network() {
    //Send the net open command
    unsigned long timeout = millis() + RESPONSE_TIMEOUT;
    String send = "AT+NETOPEN";
    String response;
    bool check = false;
    do {
        response = request_data_command(send, COMMAND_TIMEOUT);
        if ((response.equals("OK+NETOPEN: 0")) || (response.equals("+IP ERROR: Network is already openedERROR"))) {
            check = true;
        }
        delay(500);
    } while((check == false) && (millis() < timeout));
    if (millis() > timeout) {
        return false;
    } else {
        return true;
    }
}

bool cellular_enable_gprs() {
    //Disconnect all sockets
    bool check = false;
    check = cellular_disconnect_sockets();
    if (check == false) {return false;}

    //SET APN
    check = false;
    check = cellular_set_apn();
    if (check == false) {return false;}

    //Enable transparent mode
    check = false;
    check = cellular_set_transparentMode();
    if (check == false) {return false;}

    //Open Network
    check = false;
    check = cellular_open_network();
    if (check == false) {return false;}

    //return check
    return true;
}

bool cellular_disable_gprs() {
    //Disconnect all sockets
    bool check = false;
    check = cellular_disconnect_sockets();
    if (check == false) {return false;}
    
    //Close Network
    unsigned long timeout = millis() + RESPONSE_TIMEOUT;
    String send = "AT+NETCLOSE";
    String response;
    check = false;
    do {
        response = request_data_command(send, COMMAND_TIMEOUT);
        if ((response.equals("Network closedOK")) || (response.equals("OK")) || (response.equals("+IP ERROR: Network is already closedERROR"))) {
            check = true;
        }
        delay(500);
    } while((check == false) && (millis() < timeout));
    if (millis() > timeout) {
        return false;
    } else {
        return true;
    }
}

bool cellular_http_init() {
    //Check Connection and socket
    unsigned long timeout = millis() + RESPONSE_TIMEOUT;
    String send = String(("AT+CGDCONT=1,\"IP\",\""));
    send += String(Cellular_Module->network_apn);
    send += String("\",\"0.0.0.0\"");
    while((send_command(send, "OK", COMMAND_TIMEOUT) == false) && (millis() < timeout)){delay(500);}
    if (millis() > timeout) {
        return false;
    } 
    send = String("AT+CGSOCKCONT=1,\"IP\",\"");
    send += String(Cellular_Module->network_apn);
    send += String("\"");
    while((send_command(send, "OK", COMMAND_TIMEOUT) == false) && (millis() < timeout)){delay(500);}
    if (millis() > timeout) {
        return false;
    } 
    while((send_command("AT+CSOCKSETPN=1", "OK", COMMAND_TIMEOUT) == false) && (millis() < timeout)){delay(500);}
    if (millis() > timeout) {
        return false;
    } 
    return true;
}

bool cellular_http_get_request(String request) {
    //Print to serial
    #if DEBUG
    Serial.println("HTTP GET REQUEST SIM7600CE");
    #endif
    
    //Check the host connection
    unsigned long timeout = millis() + REQUEST_TIMEOUT;
    String send;
    String response;
    send = "AT+CHTTPACT=\"";
    send += Cellular_Module->http_host;
    send += "\",";
    send += Cellular_Module->http_port;
    bool check = false;
    while ((check == false) && (millis() < timeout)) {
        check = send_command(send, "+CHTTPACT: REQUEST", SENDRESP_TIMEOUT);
        delay(500);
    }
    if ((millis() > timeout) && (check == false)) {
        return false;
    } 

    //Send the GET request
    send = "GET ";
    send += Cellular_Module->http_path;
    send += request;
    send += " HTTP/1.1\r\n Host: ";
    send += Cellular_Module->http_host;
    send += "\r\nConnection: close\r\n\r\n";
    response = request_data_command(send, 0x1A, SENDRESP_TIMEOUT);

    return true;
}

bool cellular_reset() {
    //Send reset all param to default
    unsigned long timeout = millis() + RESPONSE_TIMEOUT;
    while((send_command("AT&F0", "OK", CRITICAL_COMMAND_TIMEOUT) == false) && (millis() < timeout)){delay(500);}
    if (millis() > timeout) {
        return false;
    } else {
        return true;
    }
}

bool cellular_check_network() {
    //Send the net open command
    unsigned long timeout = millis() + CRITICAL_TIMEOUT;
    String send = "AT+CREG?";
    String response;
    bool check = false;
    do {
        response = request_data_command(send, COMMAND_TIMEOUT);
        if ((response.equals("+CREG: 0,1OK")) || (response.equals("+CREG: 0,5OK"))) {
            check = true;
        }
        delay(500);
    } while((check == false) && (millis() < timeout));
    if (millis() > timeout) {
        return false;
    } else {
        return true;
    }
}

