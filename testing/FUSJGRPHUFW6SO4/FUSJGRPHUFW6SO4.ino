int led = 8;
String inputString;
bool stringComplete = false;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.println(">> START<<");  
  
  pinMode(led, OUTPUT);
}

void loop() {
  if(stringComplete) {
    digitalWrite(led, HIGH);
  }
}

void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read(); 
    Serial.write(inChar);
    //Serial.write(inChar);
    // add it to the inputString:
    inputString += inChar;
    // if the incoming character is a newline or a carriage return, set a flag
    // so the main loop can do something about it:
    if (inChar == '\n' || inChar == '\r') {
      stringComplete = true;
    } 
  }
}
