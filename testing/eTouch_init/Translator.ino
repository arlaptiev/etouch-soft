/* Name: Translator.ino
 * Authors: Artem Laptiev
 */

/*
General Description:
This file handles text execution on the display
*/


String text = ""; //All the text from the File
byte loadedRange[2] = {0,0}; //? for loadLine()
bool isLastCharANumber = false; //? for loadLine()

void translatorSetup(){
  pinMode(latchPin, OUTPUT);
  pinMode(dataPin, OUTPUT); 
  pinMode(clockPin, OUTPUT);
  getSymbolsReady();
}

/* Setting all the symbols to initial status */
void getSymbolsReady() {
  digitalWrite(latchPin, LOW);
  for (int i = 0; i < nsymbols; i++) {
    shiftOut(dataPin, clockPin, LSBFIRST, 0);
  }
  digitalWrite(latchPin, HIGH);
}

/* Writes a given line of the text on the display */
void executeLine(int line) {
/* Checks if requested line is before the last line and after or equal to the first line(0) */
  if(text.length() > line*nsymbols && line > -1) {
      lastTextLine = line; //udate lastTextLine
      
/* 
 * IF THE BOOK IS FINISHED if((line + 1)*nsymbols >= text.length()) DO SMTH 
 * 1. turn off autoscrolling
 * 2. go to the catalog or write 'END'
 */

/* Send binary to the cells */      
      digitalWrite(latchPin, LOW);
      for (int i = 0; i < nsymbols; i++) {
        shiftOut(dataPin, clockPin, LSBFIRST, Alphabet.indexOf(text.charAt(line*nsymbols+i)) );
      }
      digitalWrite(latchPin, HIGH);

/* Does autoscrolling if On */
      if (scrollOn == true) {
        delay(1000);
        executeLine(line+1);
      }
    }
}

/* Prepares 10 lines in advance to be executed
void loadLine(int line) {
  if (line == lastTextLine) {
    for (int i = - 50; i < 100; i++) {
        if (text[line * nsymbols + i] == null) break;
        //Operation on every single char
        toupper(text[line * nsymbols + i]);
        if (line * nsymbols + i < loadedRange[1]) loadedRange[1] = line * nsymbols + i;
        if (line * nsymbols + i > loadedRange[2]) loadedRange[2] = line * nsymbols + i;
    }
  } else {
    for (int i = 100 - nsymbols; i < 100; i++) {
        if (text[line * nsymbols + i] == null) break;
        //Operation on every single char
        toupper(text[line * nsymbols + i]);
    }
  }
}*/

