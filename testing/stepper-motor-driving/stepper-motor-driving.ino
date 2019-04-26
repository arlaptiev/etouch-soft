/* Name: stepper-motor-driving.ino
 * Authors: Artem Laptiev
 * Creation Date: 2019-01-14
 * Tabsize: 2

 * Copyright (c) 2019, eTouch Project
 * All rights reserved.
 * 
 */

/*
General Description:
This file is a testing file to develop the system for driving 4-wire stepper motors

Shift register #1:
pin 1 - dot 1 step
pin 2 - dot 2 step
pin 3 - dot 3 step
pin 4 - dot 4 step
pin 5 - dot 5 step
pin 6 - dot 6 step
pin 7 - direction
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

void executeLine(int line, bool forward) {

  int dirInt = 0;
  if(!forward) {
    dirInt = 64;
  }
  
  /* Checks if requested line is before the last line and after or equal to the first line(0) */
  if(message.length() > line * nSymbols && line > -1) {
      lastLine = line; //udate lastLine
      
    /* ToDo
     * IF THE BOOK IS FINISHED if((line + 1)*nsymbols >= text.length()) DO SMTH 
     * 1. turn off autoscrolling
     * 2. go to the catalog or write 'END'
     */
  
  
    /* Send binary to the cells */      
    digitalWrite(latchPin, LOW);
    
    for (int i = 0; i < nSymbols; i++) {
      shiftOut(dataPin, clockPin, LSBFIRST, mappedBraille.indexOf( message.charAt(line * nSymbols + i) ) );
    }
    
    digitalWrite(latchPin, HIGH);
  
  /* Does autoscrolling if On */
    if (scrollOn == true) {
      delay(1000);
      executeLine(line + 1);
    }
  }
  
}

void setup() {
  Serial.begin(9600);
  Serial.println(">> Device started! <<");
  
  readingSetup();
  interfaceSetup();
  
  /*
   * FOR DEBUGGING PURPOSES!! DELETE!!!!
   */

   pinMode(LED, OUTPUT);

   /* END */
}

void loop() {
  /* Control buttons handling. */
  interfaceLoop();
}

