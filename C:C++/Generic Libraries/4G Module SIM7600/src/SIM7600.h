#ifndef SIM7600_H_
#define SIM7600_H_

/* INCLUDES ******************************************************************************************************************/
#include <Arduino.h>

/* LOCAL DEFINES *************************************************************************************************************/
#define DEBUG                               0                        //Turn on and off the serial debug
#define CRITICAL_TIMEOUT               120000                        //Timeout for network registration (ms)
#define RESPONSE_TIMEOUT                30000                        //Timeout for sending and receiving commands(ms)
#define COMMAND_TIMEOUT                 10000                        //Timeout for receiving commands(ms)
#define REQUEST_TIMEOUT                  5000                        //Timeout for receiving commands(ms)
#define SENDRESP_TIMEOUT                 2000                        //Timeout for receiving commands(ms)
#define CRITICAL_COMMAND_TIMEOUT        30000                        //Timeout for expected response (ms)
#define CELLULAR_BAUD_RATE             115200                        //Baud rate of sim5320, (Don't Change!)                
  
/* LOCAL ENUMS ***************************************************************************************************************/
/* Class SIM5320 */
typedef struct SIM7600_s SIM7600; 
struct SIM7600_s {
    uint8_t cellular_reset_pin;                                      //Reset pin of the cellular module 
    String network_apn;                                              //APN of the cellular network      
    String http_host;                                                //HTTP Host url
    String http_path;                                                //HTTP Path
    String http_port;                                                //HTTP port 
    HardwareSerial & cellular_serial;                                //Hardware serial connected to the module
};

/* FUNCTION PROTOTYPES *******************************************************************************************************/
/**
 * Initialize SIM5320 cellular module
 * @param Cellular              is the instance of the class with the attributes
 * @return                      bool to indicate a successful initilization
 */
bool cellular_init(SIM7600 * Cellular);

/**
 * AT Check of SIM7600 cellular module
 * @return                      bool to indicate a successful communication
 */
bool cellular_at_check(void);

/**
 * Disable echo of SIM7600 cellular module
 * @return                      bool to indicate a successful communication
 */
bool cellular_disable_echo(void);

/**
 * Get IMEI of SIM7600 cellular module
 * @return                      String with imei of module
 */
String cellular_get_imei(void);

/**
 * Get batt voltage of SIM7600 cellular module
 * @return                      double value of battery voltage
 */
double cellular_get_battVoltage(void);

/**
 * Disconnect sockets of SIM7600 cellular module
 * @return                      bool to indicate a successful communication
 */
bool cellular_disconnect_sockets(void);

/**
 * Set apn of SIM7600 cellular module
 * @return                      bool to indicate a successful communication
 */
bool cellular_set_apn(void);

/**
 * Enable transparent mode of SIM7600 cellular module
 * @return                      bool to indicate a successful communication
 */
bool cellular_set_transparentMode(void);

/**
 * Open network of SIM7600 cellular module
 * @return                      bool to indicate a successful communication
 */
bool cellular_open_network(void);

/**
 * Enable GPRS of SIM7600 cellular module
 * @return                      bool to indicate a successful communication
 */
bool cellular_enable_gprs(void);

/**
 * Disable GPRS of SIM7600 cellular module
 * @return                      bool to indicate a successful communication
 */
bool cellular_disable_gprs(void);

/**
 * HTTP GET REQUEST of SIM7600 cellular module
 * @return                      bool to indicate a successful communication
 */
bool cellular_http_get_request(String request);

/**
 * HTTP init of SIM7600 cellular module (Done once in setup otherwise can cause error)
 * @return                      bool to indicate a successful communication
 */
bool cellular_http_init(void);

/**
 * Reset config and settings of SIM5320 cellular module
 * @return                      bool to indicate a successful communication
 */
bool cellular_reset(void);

/**
 * Check is the module is connected to the internet
 * @return                      bool to indicate a successful communication
 */
bool cellular_check_network(void);

#endif /* SIM7600_H_ */