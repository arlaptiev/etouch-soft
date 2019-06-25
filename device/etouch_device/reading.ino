/* Name: reading.ino
 * Authors: Artem Laptiev
 */

/*
General Description:
This file handles text display on the device
*/

/* 
 * Binary representation of indexes of characters in mappedBraille string correspond to the following order of dots on the device:
 * 
 *  4 1
 *  5 2
 *  6 3
 * 
 */
String mappedBraille = " A1B'K2L@CIF/MSP\"E3H9O6R^DJG>NTQ,*5<-U8V.%[$+X!&;:4\\0Z7(_?W]#Y)="; // Character in braille => it's index in binary


void readingSetup(){
  
  pinMode(latchPin, OUTPUT);
  pinMode(dataPin, OUTPUT); 
  pinMode(clockPin, OUTPUT);
  pinMode(motorDirPin, OUTPUT);
  
  restartSymbols();
  
}


/* Setting all the symbols to initial status */
void clearSymbols() {
  
  digitalWrite(latchPin, LOW);
  
  for (int i = 0; i < nSymbols; i++) {
    shiftOut(dataPin, clockPin, MSBFIRST, 0);
  }
  
  digitalWrite(latchPin, HIGH);
  
}

/* Lowering all the stepper motors */
void restartSymbols() {

  digitalWrite(motorDirPin, HIGH);
  
  for (int j = 0; j < 200; j++) {

      digitalWrite(latchPin, LOW);
      
      for (int i = 0; i < nSymbols; i++) {
        shiftOut(dataPin, clockPin, MSBFIRST, 255);
      }

      digitalWrite(latchPin, HIGH);

      delayMicroseconds(100);

      clearSymbols();

      delayMicroseconds(100);
    }

  digitalWrite(motorDirPin, LOW);
  
}



/* Writes a given line of the text on the display */
void executeLine(int line, bool up) {

  digitalWrite(motorDirPin, LOW);

  /* Is added to value sent to shift register. Adds additional 0 or 1 to the 7th output depending on the direction */
  if(!up) {
    digitalWrite(motorDirPin, HIGH);
  }
  
  /* Checks if requested line is before the last line and after or equal to the first line(0) */
  if(message.length() > line * nSymbols && line > -1) {
      
    /* ToDo
     * IF THE BOOK IS FINISHED if((line + 1)*nsymbols >= text.length()) DO SMTH 
     * 1. turn off autoscrolling
     * 2. go to the catalog or write 'END'
     */
  
  
    /* Send binary to the cells */      

    for (int j = 0; j < 200; j++) {

      digitalWrite(latchPin, LOW);
      
      for (int i = 0; i < nSymbols; i++) {
        shiftOut(dataPin, clockPin, MSBFIRST, mappedBraille.indexOf( toupper(message.charAt(line * nSymbols + i) )) );
      }

      digitalWrite(latchPin, HIGH);

      delayMicroseconds(100);

      clearSymbols();

      delayMicroseconds(100);
    }
    

    lineToRun = line + 1; //udate lineToRun
  
  /* Does autoscrolling if On */
    if (scrollOn && up) {
      delay(3000);
      if(Serial.available()) {
        return;
      }
      runNextLine();
    }
  }
  
}

void runNextLine(){
  if(lineToRun > 0){
    Serial.println();
    Serial.println("ERASE LINE:");
    executeLine(lineToRun - 1, false);
    Serial.println("PRINT LINE:");
    executeLine(lineToRun, true);
  } else {
    Serial.println("FIRST LINE:");
    executeLine(0, true);
  }
}


void runLastLine(){
    Serial.println();
    Serial.println("ERASE LINE:");
    executeLine(lineToRun - 1, false);
    Serial.println("PRINT LINE:");
    executeLine(lineToRun - 2, true);
}
