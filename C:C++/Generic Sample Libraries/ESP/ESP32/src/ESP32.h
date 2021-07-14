#ifndef ESP32_H_
#define ESP32_H_

/* INCLUDES ***********************************************************************************************************************************************************************************/
#include <Arduino.h>
#include <WiFi.h>
#include "EEPROM.h"

/* LOCAL DEFINES ******************************************************************************************************************************************************************************/
#define DEBUG                               0                        //Turn on and off the serial debug
#define AVAILABLE_WIFI_MAX_NO             100                        //Max no of available wifi no
#define EEPROM_SIZE                       128                        //Size of the EEPROM
#define EEPROM_SSID_ADDRESS                 0                        //Address where ssid is stored
#define EEPROM_PASSWORD_ADDRESS            55                        //Address where password is stored
  
/* LOCAL ENUMS ********************************************************************************************************************************************************************************/
/* Class ESP32 */
typedef struct ESP32_s ESP32; 
struct ESP32_s {
};

/* Class ESP32 WiFi Scan Attributes */
typedef struct {
    int available_wifi_no;                                           //Number of available wifi no after wifi scan
    String availabe_wifi [AVAILABLE_WIFI_MAX_NO];                     //String with all available wifi networks
}espWifiScanOutput_t;

/* FUNCTION PROTOTYPES ************************************************************************************************************************************************************************/
/**
 * Initialize ESP32 Class and EEPROM
 * @param pesp23            is the instance of the class with the attributes
 * @return                  bool to indicate a successful initilization
 */
bool esp32_init(ESP32 * pesp32);

/**
 * Generate an access point
 * @param ssid              Pointer to the SSID (max 63 char).
 * @param password          For WPA2 min 8 char, for open use NULL
 * @param channel           WiFi channel number, 1 - 13.
 * @param ssid_hidden       Network cloaking (0 = broadcast SSID, 1 = hide SSID)
 * @param max_connection    Max simultaneous connected clients, 1 - 4.
 * @return                  bool to indicate a successful generation of hotspot
 */
bool esp32_accessPoint(const char * ssid, const char * password, IPAddress ap_ip, IPAddress ap_gateway, IPAddress ap_subnet, uint8_t channel, bool isHidden, uint8_t maxConnection);

/**
 * Close already opened access point
 * @return                  bool to indicate a successful disconnection of hotspot
 */
bool esp32_closeAP(void);

/**
 * Scan for different AP nearby and store them in an Array
 * @return                  struct with all available networks nearby
 */
espWifiScanOutput_t esp32_wifiScan(void);

/**
 * Connect the esp to a given network (station)
 * @param ssid              Pointer to the SSID (max 63 char).
 * @param password          For WPA2 min 8 char, for open use NULL
 * @param timeout           Unsigned long with connection timeout
 * @param changeMode        Bool to change mode to station mode (off in cp mode only)
 * @return                  bool to indicate a successful connection to the network
 */
bool esp32_connectToNetwork(const char * ssid, const char * password, unsigned long timeout, bool changeMode);

/**
 * Send sensors' data to the cloud using http get requests
 * @param request           Sting URL of the GET request
 * @param host              Pointer URL of the host
 * @param path              Pointer URL of path
 * @param port              uint16_t port of http
 * @param timeout           Unsigned long with connection timeout
 * @return                  bool to indicate a successful transmission to the data
 */
bool esp32_httpGetRequest(String request, const char * host, const char * path, uint16_t port, unsigned long timeout);

/**
 * Store wifi credentials to the EEPROM
 * @param ssid              String wifi ssid to be stored in EEPROM
 * @param password          String password to be stored in EEPROM
 */
void esp32_storeWifiCredentials(String ssid, String password);

/**
 * Load wifi credentials from the EEPROM
 * @param ssid              Pointer string to save the loaded ssid in
 * @param password          Pointer string to save the loaded password in
 * @return                  bool to indicate if there is stored credentials in eeprom
 */
bool esp32_loadWifiCredentials(String * ssid, String * password);

/**
 * Clear all data in the EEPROM
 */
void esp32_clearEEPROM(void);

#endif /* ESP32_H_ */