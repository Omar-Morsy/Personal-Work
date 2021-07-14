/*INCLUDES***************************************************************************************************************************************************/
#include "ESP_captivePortal.h"

/* GLOBAL VARIABLES*****************************************************************************************************************************************/
static ESP_CP * cp; 
static espWifiScanOutput_t wifiScanOutput;

/*HTML VARIABLES *******************************************************************************************************************************************/
const char * index_page =
#include "index_v2_c.html"
;

const char * basic_page = 
#include "basic_v2_c.html"
;


/* FUNCTION DEFINITIONS*************************************************************************************************************************************/
void captivePortal_init(ESP_CP * esp_cp) {
    //Copy attributes of CP class
    cp = esp_cp;

    //Reset the boolean flag
    * (cp->status) = false;
}

void captivePortal_open(IPAddress ip) {
    //Set the dns server
    dnsServer.setErrorReplyCode(DNSReplyCode::NoError);
	dnsServer.start(53, "*", ip);

    //Add the handling methods to the server
    server.onNotFound(captivePortal_handleRoot);
	server.on("/_generate_204",HTTP_ANY, captivePortal_handleNotFound);
	server.on("/scanWiFi", HTTP_ANY, captivePortal_handleWifiScan);
	server.on("/setup.html", HTTP_ANY, captivePortal_handleWifiConnection);

    //start the server
    server.begin();
}

void captivePortal_handleRoot() {
    //print to serial
    #if DEBUG
    Serial.println("Handling the root...........");
    delay(100);
    #endif

    //set the content length
    server.setContentLength(CONTENT_LENGTH_UNKNOWN);

    //Send the html (Empty content inhibits Content-length header so we have to close the socket)
    server.send(200, "text/html", "");
    server.sendHeader("Connection", "Keep-Alive");
	server.sendHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	server.sendHeader("Pragma", "no-cache");
	server.sendHeader("Expires", "-1");
    server.sendContent(String(basic_page));
    server.sendContent(WiFi.macAddress());
    server.sendContent("</p>");

    //Close the socket
    server.sendContent("</h4></body></html>");
    delay(500);
}

void captivePortal_handleWifiScan() {
    //print to serial
    #if DEBUG
    Serial.println("Handling wifi scanning...........");
    delay(100);
    #endif

    //Set the content length
    server.setContentLength(CONTENT_LENGTH_UNKNOWN);

    //send the html to the server
    server.send(200, "text/html", "");
    server.sendHeader("Connection", "Keep-Alive");
	server.sendHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	server.sendHeader("Pragma", "no-cache");
	server.sendHeader("Expires", "-1");
	server.sendContent(String(index_page));
    server.sendContent("<script type=text/javascript>toggle('SCANNING');</script>" "</body></html>");
    delay(1000);

    //Call method to scan for wifi and store them in global array
    wifiScanOutput = esp32_wifiScan();

    //print the wifi networks if scan was done
    //send the corresponding html
    if (wifiScanOutput.available_wifi_no > 0) {
        server.sendContent("<label for='networks'>" "<datalist id='networks'>");
        for (uint8_t i = 0; i < wifiScanOutput.available_wifi_no; i++) {
            server.sendContent("<option value = '" + wifiScanOutput.availabe_wifi[i] + "'>"+ wifiScanOutput.availabe_wifi[i] + "</option>");
            delay(10);
        }
        delay(100);
        server.sendContent("<script type=text/javascript>toggle('MANUAL_FORM');</script>" "</body></html>");
    } else {
        server.sendContent("<script type=text/javascript>toggle('NO_VISIBLE_WIFI_AVAILABLE');</script>" "</body></html>");
	}
    delay(500);

    //Close the socket
    server.sendContent("</h4></body></html>");
}

void captivePortal_handleWifiConnection() {
    //print to serial
    #if DEBUG
    Serial.println("Handling wifi connection...........");
    delay(100);
    #endif

    //Listen to the entries of the ssid and password from html
    String s = server.arg("ssid");
	String p = server.arg("pass");
    const char * user_ssid = s.c_str();
    const char * user_password = p.c_str();

    //A work around for the ios
    if (strlen(user_ssid) < 4){ 
        s = server.arg("ssid2");
        user_ssid = s.c_str();
	}

    //Set the content length
    server.setContentLength(CONTENT_LENGTH_UNKNOWN);

    //send the html to server
    server.send(200, "text/html", "");
    server.sendHeader("Connection", "Keep-Alive");
	server.sendHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	server.sendHeader("Pragma", "no-cache");
	server.sendHeader("Expires", "-1");
	server.sendContent(String(index_page));
    server.sendContent("<script type=text/javascript>toggle('AUTH_IN_PROGRESS');</script>" "</body></html>");
    delay(5000);

    //Connect to the user given wifi credentials
    bool check = esp32_connectToNetwork(user_ssid, user_password, cp->timeout, false);

    //check if the status is connected
    if (check) {
        server.sendContent("<script type=text/javascript>toggle('CONNECTED');</script>" "</body></html>");
        delay(1000);
        server.client().stop();
        WiFi.mode(WIFI_STA);
        * (cp->status) = true;
        esp32_storeWifiCredentials(String(user_ssid), String(user_password)); 
    } else {
        server.sendContent("<script type=text/javascript>toggle('UNABLE_TO_CONNECT');</script>" "</body></html>");
        delay(500);
        WiFi.disconnect();
    }
}

void captivePortal_handleNotFound() {
    //print to serial
    #if DEBUG
    Serial.println("Handling not found...........");
    delay(100);
    #endif

    //Send the header to the html
    server.sendHeader("Location", String("http://") + toStringIp(server.client().localIP()));

    //Send html to server
    server.send(302);

    //Stop the server
    server.client().stop();
}

String toStringIp(IPAddress ip) {
    //Empty string for storage
	String res = "";

    //Convert to string
	for (int i = 0; i < 3; i++) {
		res += String((ip >> (8 * i)) & 0xFF) + ".";
	}

    //Return the string
	res += String(((ip >> 8 * 3)) & 0xFF);
	return res;
}