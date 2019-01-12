
/* Name: eTouch_init.ino
 * Authors: Artem Laptiev
 * Creation Date: 2018-02-10
 * Tabsize: 2

 * Copyright (c) 2018, eTouch
 * All rights reserved.
 * 
 * Credits to M. Ray Burnette for the "Captive Portal" part of the code.
 */

/*
General Description:
This file is the main file in the Arduino based eTouch device, which initiates all the processes in it.
*/

/* To create a WiFi access point and provide a web server on it. */
#include <ESP8266WiFi.h>
#include "./DNSServer.h"
#include <ESP8266WebServer.h>
#include <WiFiClient.h>
#include <FS.h>

/* Sets up a web server. */
const byte        DNS_PORT = 53;          // Capture DNS requests on port 53
IPAddress         apIP(10, 10, 10, 1);    // Private network for server
DNSServer         dnsServer;              // Create the DNS object
ESP8266WebServer  webServer(80);          // HTTP server

/* File Upload handling. */
File fsUploadFile;                        // a File object to temporarily store the received file
String getContentType(String filename);   // convert the file extension to the MIME type
bool handleFileRead(String path);         // send the right file to the client (if it exists)
void handleFileUpload();                  // upload a new file to the SPIFFS

/* Initializing pins. */
/* Pins for the shift registers to pass signals to the letters. */
int clockPin = D0; //SH_CP
int latchPin = D1; //ST_CP
int dataPin = D2;  //DS
/* Pins for the control buttons. */
int forwardPin = D6; //Page up
int forwardState = 0;
int backwardsPin = D7; //Page down
int backwardsState = 0;

/* Other settings. */
byte nsymbols = 2; //Number of symbols on display
byte displayMode = 1; //0-picking file; 1-reading text


void setup() {
  Serial.begin(115200);
  webServerSetup();
  translatorSetup();
  buttonsSetup();
}

void loop() {
  /* Web server handling. */
  dnsServer.processNextRequest();
  webServer.handleClient();

  /* Control buttons handling. */
  buttonsLoop();
}

