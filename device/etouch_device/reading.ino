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
  
  getSymbolsReady();
  
}


/* Setting all the symbols to initial status */
void getSymbolsReady() {
  
  digitalWrite(latchPin, LOW);
  
  for (int i = 0; i < nSymbols; i++) {
    shiftOut(dataPin, clockPin, LSBFIRST, 0);
  }
  
  digitalWrite(latchPin, HIGH);
  
}



/* Writes a given line of the text on the display */
void executeLine(int line, bool up) {

  /* Is added to value sent to shift register. Adds additional 0 or 1 to the 7th output depending on the direction */
  int dirInt = 0;
  if(!up) {
    dirInt = 64;
  }
  
  /* Checks if requested line is before the last line and after or equal to the first line(0) */
  if(message.length() > line * nSymbols && line > -1) {
      
    /* ToDo
     * IF THE BOOK IS FINISHED if((line + 1)*nsymbols >= text.length()) DO SMTH 
     * 1. turn off autoscrolling
     * 2. go to the catalog or write 'END'
     */
  
  
    /* Send binary to the cells */      
    digitalWrite(latchPin, LOW);
    
    for (int i = 0; i < nSymbols; i++) {
      shiftOut(dataPin, clockPin, LSBFIRST, mappedBraille.indexOf( message.charAt(line * nSymbols + i) ) + dirInt );
    }
    
    digitalWrite(latchPin, HIGH);
    

    lineToRun = line + 1; //udate lineToRun
  
  /* Does autoscrolling if On */
    if (scrollOn && up) {
      delay(1500);
      runNextLine();
    }
  }
  
}

void runNextLine(){
  if(lineToRun > 0){
    executeLine(lineToRun - 1, false);
    executeLine(lineToRun, true);
  } else {
    executeLine(0, true);
  }
}


void runLastLine(){
    executeLine(lineToRun - 1, false);
    executeLine(lineToRun - 2, true);
}

