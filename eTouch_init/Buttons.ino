bool buttonControl = true;

void buttonsSetup(){
  pinMode(forwardPin, INPUT);
  pinMode(backwardsPin, INPUT); 
  pinMode(D2,OUTPUT);//TEST
}

void buttonsLoop(){
  forwardState = digitalRead(forwardPin);
  backwardsState = digitalRead(backwardsPin);
  
  if (forwardState == 1) {
    //executeLine(lastTextLine + 1); && buttonControl == true)
    digitalWrite(D2, HIGH);
    //buttonDelay();
  }
  if (forwardState == LOW) {
    //executeLine(lastTextLine + 1); && buttonControl == true)
    digitalWrite(D2, LOW);
    //buttonDelay();
  }
  
  if (backwardsState == 0) {
    //executeLine(lastTextLine - 1);
    digitalWrite(D2, HIGH);
    //buttonDelay();
  }
}

//To not cause stack overflaw with numerous calls for function executeLine()
void buttonDelay(){
  buttonControl = false;
  delay(100);
  buttonControl = true;
}

