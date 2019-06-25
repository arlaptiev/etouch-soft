/* Name: etouch_device.ino
 * Authors: Artem Laptiev
 * Creation Date: 2019-01-14
 * Tabsize: 2

 * Copyright (c) 2019, eTouch Project
 * All rights reserved.
 * 
 */

/*
General Description:
This file is the main file in the Arduino based eTouch device, which initiates all the processes in it.
*/



/* Initializing pins */

/* Pins for the shift registers to pass signals to the letters */
int clockPin = 13; //SH_CP
int latchPin = 12; //ST_CP
int dataPin = 11;  //DS

/* Pins for the control buttons */
int forwardPin = 10; //Page up pin
int forwardState = 0;
int backwardsPin = 9; //Page down pin
int backwardsState = 0;
int scrollPin = 8; // Autoscrolling pin
int scrollState = 0;
int restartPin = 7; // Nulling pin
int restartState = 0;

int motorDirPin = 5;


/* Settings */
byte nSymbols = 6; //Number of symbols on display


/* Global variables */
String message = ""; // All the uploaded text
bool scrollOn = false; // State of automatic scrolling
int lineToRun = 0; //Number of the line the display is to run next


void setup() {
  Serial.begin(9600);
  Serial.println(">> Device started! <<");
  
  readingSetup();
  interfaceSetup();
}

void loop() {
  /* Control buttons handling. */
  interfaceLoop();
}
