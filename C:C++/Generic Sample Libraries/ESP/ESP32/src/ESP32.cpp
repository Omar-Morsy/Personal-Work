/*INCLUDES*****************************************************************************************************************************************************************************************/
#include "ESP32.h"

/* GLOBAL VARIABLES********************************************************************************************************************************************************************************/
static ESP32 * esp32;
static espWifiScanOutput_t esp32_wifiScan_output;
static WiFiClient client;

/* FUNCTION DEFINITIONS****************************************************************************************************************************************************************************/
bool esp32_init(ESP32 * pesp32) {
    //Copy passed attributes
    esp32 = pesp32;

    //EEPROM initialization
    if (!EEPROM.begin(EEPROM_SIZE)) {
        #if DEBUG
        Serial.println("EEPROM initialization error");
        #endif
        return false;
    } 

    //return true
    return true;
}

bool esp32_accessPoint(const char * ssid, const char * password, IPAddress ap_ip, IPAddress ap_gateway, IPAddress ap_subnet, uint8_t channel, bool isHidden, uint8_t maxConnection){
    //Serial print the mode
    #if DEBUG
    Serial.println("ESP open AP mode");
    delay(100);
    #endif

    //Enable AP
    WiFi.enableAP(true);
    WiFi.mode(WIFI_AP);

    //Configure access point ipAddress, gateway and subnet
    bool check = WiFi.softAPConfig(ap_ip, ap_gateway, ap_subnet); if(!check){return false;}

    //Open the hotspot with the given ssid, password and channel
    check = WiFi.softAP(ssid, password , channel, isHidden, maxConnection); if(!check){return false;}

    //Save the ip address given to the device in case needs to be printed
    #if DEBUG
    IPAddress IP = WiFi.softAPIP();
    Serial.println(IP);
    Serial.println("Done.");
    delay(100);
    #endif

    //If all passed return true
    return true;
}

bool esp32_closeAP(void) {
    //Serial print the mode
    #if DEBUG
    Serial.println("ESP close AP mode");
    delay(100);
    #endif

    //Disable AP
    bool check = WiFi.enableAP(false);

    //Serial print
    #if DEBUG
    Serial.println("Done.");
    delay(100);
    #endif

    //return the output
    return check;
}

espWifiScanOutput_t esp32_wifiScan(void) {
    //Print to serial
    #if DEBUG
    Serial.println("Scanning available WiFi networks");
    delay(100);
    #endif

    //Empty all attributes
    esp32_wifiScan_output.available_wifi_no = 0;
    for (int i = 0; i < AVAILABLE_WIFI_MAX_NO; i++) {
        esp32_wifiScan_output.availabe_wifi[i] = "";
    }

    //Scan for available networks nearby
    esp32_wifiScan_output.available_wifi_no = WiFi.scanNetworks();

    //Check if no available networks nearby
    if (esp32_wifiScan_output.available_wifi_no <= 0) {
        return esp32_wifiScan_output;
    }

    //Store all networks nearby
    for (int i = 0; i < esp32_wifiScan_output.available_wifi_no; i++) {
        esp32_wifiScan_output.availabe_wifi[i] = WiFi.SSID(i);
    }

    //Print to serial
    #if DEBUG
    Serial.println("Done.");
    delay(100);
    #endif

    //return the output struct
    return esp32_wifiScan_output;
}

bool esp32_connectToNetwork(const char * ssid, const char * password, unsigned long timeout, bool changeMode) {
    //Print to serial
    #if DEBUG
    Serial.println("Connecting to the Given Network");
    Serial.print("SSID = "); Serial.println(ssid);
    Serial.print("Password = "); Serial.println(password);
    delay(100);
    #endif

    //Switch WiFi mode
    if (changeMode) {
        WiFi.mode(WIFI_STA);
    }
    WiFi.disconnect();

    //Connect to the Given networks
    WiFi.begin(ssid, password);

    //Ensure that the device is connected to the network
    int i = 0;
    unsigned long connecting_timeout = millis() + timeout;
    while ((WiFi.status() != WL_CONNECTED) && (millis() < connecting_timeout)) {
        delay(500);
        if ((++i % 16) == 0) {
            if (changeMode) {
                WiFi.mode(WIFI_STA);
            }
            WiFi.disconnect();
            WiFi.begin(ssid, password);
            delay(500);
        }
    }

    //Check if a timeout has been triggered
    if (WiFi.status() != WL_CONNECTED) {
        return false;
    }

    //If all passed return true;
    WiFi.setAutoConnect(true);
    WiFi.setAutoReconnect(true);

    //Print the ip address
    #if SERIAL_DBG
    Serial.print(F("Connected. My IP address is: "));
    Serial.println(WiFi.localIP());
    delay(100);
    #endif

    //return true
    return true;    
}

bool esp32_httpGetRequest(String request, const char * host, const char * path, uint16_t port, unsigned long timeout) {
    //Print to serial
    #if DEBUG
    Serial.println("Sending data to the Cloud");
    delay(100);
    #endif

    //Connect to the host
    int response = 0;
    unsigned long connecting_timeout = millis() + (timeout * 2);
    while ((response == 0) && (millis() < connecting_timeout)) {
        client.stop();
        response = client.connect(host, port, timeout);
    }

    //Check the status of the host connection
    if (response == 0) {
        return false;
    }

    //Make a HTTP GET request (Send data)
    String url = "";
    url = "GET " + String(path) + request + " HTTP/1.1\r\nHost: " + String(host) + "\r\nConnection: close\r\n\r\n";         
    client.println(url);

    //Close the connection
    client.stop();

    //Print to serial
    #if DEBUG
    Serial.println("Done.");
    delay(100);
    #endif

    //If all passed return true
    return true;
}

void esp32_storeWifiCredentials(String ssid, String password) {
    //Print to serial
    #if DEBUG
    Serial.println("Storing wifi credentials in EEPROM");
    delay(100);
    #endif

    //Save credentials for future connection
    EEPROM.writeString(EEPROM_SSID_ADDRESS, ssid);
    EEPROM.writeString(EEPROM_PASSWORD_ADDRESS, password);
    EEPROM.commit();

    //Print to serial
    #if DEBUG
    Serial.println("Done.");
    delay(100);
    #endif           
}

bool esp32_loadWifiCredentials(String * ssid, String * password) {
    //Print to serial
    #if DEBUG
    Serial.println("Loading wifi credentials from EEPROM");
    delay(100);
    #endif

    //Copy the ssid and password from the eeprom
    String s = EEPROM.readString(EEPROM_SSID_ADDRESS);
    String p = EEPROM.readString(EEPROM_PASSWORD_ADDRESS);

    //Check for emty eeprom
    if ((s.equals(NULL)) || (s.equals(""))) {
        return false;
    }

    //If all passed save data in the pointers
    * ssid = s;
    * password = p;

    //return true
    return true;
}

void esp32_clearEEPROM(void) {
    //Print to serial
    #if DEBUG
    Serial.println("Clearing EEPROM");
    delay(100);
    #endif

    //Clearing EEPROM
    EEPROM.writeString(EEPROM_SSID_ADDRESS, "");
    EEPROM.writeString(EEPROM_PASSWORD_ADDRESS, "");
    EEPROM.commit();

    //Print to serial
    #if DEBUG
    Serial.println("Done.");
    delay(100);
    #endif
}
