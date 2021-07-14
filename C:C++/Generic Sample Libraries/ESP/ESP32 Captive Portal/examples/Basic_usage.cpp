/*INCLUDES ************************************************************************************************************************/
#include "ESP_captivePortal.h"

/*GLOBAL DEFINES ******************************************************************************************************************/
#define AP_SSID                    (String)"Omar_Morsy"      //The ssid of the ap is
#define AP_PASSWORD                                  ""      //The password of the AP (Hotspot)
#define AP_CHANNEL                                    1      //The channel of the AP
#define AP_HIDDEN                                     0      //1 for hidden AP SSID
#define AP_MAX_CONNECTION                             4      //The max. connection of stations to the AP
#define CONNECTION_TIMEOUT                        30000      //Connection to the wifi network timeout in ms
#define CP_TIMEOUT                               300000      //Captive portal timeout in ms

/*GLOBAL VARIABLES ****************************************************************************************************************/
bool cp_status;
ESP_ESP32 esp;
ESP_CP esp_cp = {&cp_status, CONNECTION_TIMEOUT};
ESP32WebServer server(80);
DNSServer dnsServer;
IPAddress apIP(192,168,4,1);
IPAddress apGateway(192,168,4,1);
IPAddress apSubnet(255,255,255,0);

/*PROGRAM *************************************************************************************************************************/
void setup() {
  Serial.begin(115200);
  while(!Serial);
  Serial.println("Program Start..."); 

  Serial.println(esp32_init(&esp)); 

  esp32_clearEEPROM();

  Serial.println("\n");
  String ssid = AP_SSID;
  const char * ap_ssid = ssid.c_str();
  bool check = esp32_accessPoint(ap_ssid, AP_PASSWORD, apIP, apGateway, apSubnet, AP_CHANNEL, AP_HIDDEN, AP_MAX_CONNECTION);
  Serial.println(check);

  captivePortal_init(&cp);
  Serial.println(cp_status);

  captivePortal_open(apGateway);

  unsigned long timeout = millis() + CP_TIMEOUT;
  while((((cp_status == false)) && (millis() < timeout)) || WiFi.softAPgetStationNum() > 0){
    dnsServer.processNextRequest();
    server.handleClient();
  }

  if (cp_status == true) {
    esp32_closeAP();
  } else {
    Serial.println("CP Timeout");
  }

  String wifiSSID;
  String password;
  check = esp32_loadWifiCredentials(&wifiSSID, &password);
  Serial.println(check);
  Serial.println(wifiSSID);
  Serial.println(password);
}

void loop() {
}
