#ifndef ESP_CAPTIVEPORTAL_H_
#define ESP_CAPTIVEPORTAL_H_

/*INCLUDES*********************************************************************************************************************************/
#include "ESP_ESP32.h"
#include "ESP32WebServer.h"
#include <DNSServer.h>

/* GLOBAL VARIABLES ***********************************************************************************************************************/
extern ESP32WebServer server;
extern DNSServer dnsServer;

/* LOCAL ENUMS ****************************************************************************************************************************/
/* Class ESP_ESP32 */
typedef struct ESP_CP_s ESP_CP; 
struct ESP_CP_s {
    bool * status;                                  //Pointer to connection status to the wifi network
    unsigned long timeout;                          //Timeout for connection to the user-provided credentials
};

/* FUNCTION PROTOTYPES*********************************************************************************************************************/
/**
 * Initialize captive portal class 
 */
void captivePortal_init(ESP_CP * esp_cp);

/**
 * Open the captive portal, Access point must be opened already
 * @param ip             IPaddress of the dns server where captive portal is found
 */
void captivePortal_open(IPAddress ip);

/**
 * Handles the basic page of the captive portal
 */
void captivePortal_handleRoot(void);

/**
 * hanldes the wifi scan page of the captive portal
 */
void captivePortal_handleWifiScan(void);

/**
 * hanldes the wifi connection page of the captive portal
 */
void captivePortal_handleWifiConnection(void);

/**
 * hanldes the any errors
 */
void captivePortal_handleNotFound(void);

/**
 * Converts the ip address to string
 * @param ip            is the ip address to be converted to String
 * @return              String containing ip address
 */
String toStringIp(IPAddress ip);

#endif /* ESP_CAPTIVEPORTAL_H_ */