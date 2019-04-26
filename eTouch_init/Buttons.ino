/* Name: Buttons.ino
 * Authors: Artem Laptiev
 */

/*
General Description:
This file handles all the responses to the buttons pressing, including initiating new line executions and modes changing
*/


bool buttonControl = true;

void buttonsSetup(){
  pinMode(forwardPin, INPUT_PULLUP);
  pinMode(backwardsPin, INPUT_PULLUP);
  pinMode(scrollPin, INPUT_PULLUP);
}

void buttonsLoop(){
  forwardState = digitalRead(forwardPin);
  backwardsState = digitalRead(backwardsPin);
  scrollState = digitalRead(scrollPin);

/* Turns autoscrolling on/off */
  if (scrollState == LOW && buttonControl == true) {
    scrollOn = !scrollOn;
    executeLine(lastTextLine);
    buttonDelay();
  }

/* Starts next line
 *  if last line, it does nothing
 */

  if (forwardState == LOW && buttonControl == true && scrollOn == false) {
    executeLine(lastTextLine + 1);
    buttonDelay();
  }

/* Starts previous line  
 *  if first line, it does nothing
 */
  if (backwardsState == LOW && buttonControl == true && scrollOn == false) {
    executeLine(lastTextLine - 1);
    buttonDelay();
  }
}

/* Avoiding stack overflaw with numerous calls for function executeLine() */
void buttonDelay(){
  buttonControl = false;
  delay(500);
  buttonControl = true;
}

