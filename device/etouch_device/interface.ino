/* Name: interface.ino
 * Authors: Artem Laptiev
 */

/*
General Description:
This file handles all the responses to the buttons pressing, including initiating new line executions and modes changing
*/



/*
 * TODO check if buttonControl is needed or could be replaced with just delay()!!
 */
bool buttonControl = true;
String receivedMessage;

void interfaceSetup(){
  
  pinMode(forwardPin, INPUT_PULLUP);
  pinMode(backwardsPin, INPUT_PULLUP);
  pinMode(scrollPin, INPUT_PULLUP);
  
}

void interfaceLoop(){

  /* Handling buttons: */
  
  forwardState = digitalRead(forwardPin);
  backwardsState = digitalRead(backwardsPin);
  scrollState = digitalRead(scrollPin);

  /* Turns autoscrolling on/off */
  if (scrollState == LOW && buttonControl == true) {   
    scrollOn = !scrollOn;
    executeLine(lastLine);
    buttonDelay();
  }

  /* Starts next line; if last line, it does nothing */
  if (forwardState == LOW && buttonControl == true && scrollOn == false) {
    executeLine(lastLine + 1);
    buttonDelay();
  }

  /* Starts previous line; if first line, it does nothing */
  if (backwardsState == LOW && buttonControl == true && scrollOn == false) {
    executeLine(lastLine - 1);
    buttonDelay();
  }


  /* Handling Bluetooth module incoming message: */

  /* Puts together a receivedMessage char by char (one at a time) */
  while (Serial.available()) {
    delay(10);  //small delay to allow input buffer to fill

    char data = Serial.read();  //gets one byte from serial buffer
    
    if (data == ',') {
      break;
    }  //breaks out of capture loop

    /*
     * FOR DEBUGGING PURPOSES!! DELETE!!!!
     */

     if (data == '1') {
      digitalWrite(LED, HIGH);
     }

     /* END */
    
    receivedMessage += data; 
  } 

  /* Separates the commands from text input*/
  if (receivedMessage.length() > 0) {
    
    if (strcmp(receivedMessage.c_str(), "btn_forward")){
      executeLine(lastLine + 1);
      Serial.println(">> btn_forward <<");
    }
    
    else if (strcmp(receivedMessage.c_str(), "btn_back")){
      executeLine(lastLine - 1);
      Serial.println(">> btn_back <<");
    }

    else if (strcmp(receivedMessage.c_str(), "btn_autoscroll")){
      scrollOn = !scrollOn;
      executeLine(lastLine);
      Serial.println(">> btn_autoscroll <<");
    } 

    // If bluetooth module receives any value not listed above, it is just submitted text, not command
    else {
      message = receivedMessage;
    }
    
  } 

  
  
}


/* Avoiding stack overflaw with numerous calls for function executeLine() */
void buttonDelay(){
  buttonControl = false;
  delay(500);
  buttonControl = true;
}

