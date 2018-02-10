String text = ""; //All the text from the File
int currentTextLine = 0; //Number the reader is at
int lastTextLine = 0; //Number the reader was at the last time he closed display
byte loadedRange[2] = {0,0}; 
bool isLastCharANumber = false;

void translatorSetup(){
  pinMode(latchPin, OUTPUT);
  pinMode(dataPin, OUTPUT); 
  pinMode(clockPin, OUTPUT);
  getSymbolsReady();
}

//Setting all the symbols to work
void getSymbolsReady() {
  digitalWrite(latchPin, LOW);
  for (int i = 0; i < nsymbols; i++) {
    shiftOut(dataPin, clockPin, LSBFIRST, B11111100);
  }
  digitalWrite(latchPin, HIGH);
}

//Writes a specific line of the File on the display
void executeLine(int line) {
  //Checks if requested line is before the last line and after or equal to the first line(0)
  if(text.length() > line*nsymbols && line > -1) {
      lastTextLine = line; //wont work with loadLine
      //FILE THAT THE BOOK IS FINISHED if((line + 1)*nsymbols >= text.length()) DO SMTH
      
      digitalWrite(latchPin, LOW);
      for (int i = 0; i < nsymbols; i++) {
        shiftOut(dataPin, clockPin, LSBFIRST, nAlphabet[Alphabet.indexOf(text.charAt(line*nsymbols+i))] );
      }
      digitalWrite(latchPin, HIGH);
      
      delay(1000);
      executeLine(line+1);
    }
}

/*Prepares 10 lines in advance to be executed
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

