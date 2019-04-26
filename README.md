![eTouch device](https://raw.githubusercontent.com/arlaptiev/etouch-soft/master/device.png)
Arduino based eTouch project.

eTouch is an affordable electronic Braille reader for the blind, developed by a group of enthusiasts from Ukraine. This repo contains the software for the project.

It includes:
* the device code
    1. receives a message to be displayed from a user's phone application
    2. converts the message to Braille-corresponding binary signals
    3. drives the stepper motors to display the message in Braille
    4. handles the buttons
* the android app code
    1. connects to the device's Bluetooth module
    2. navigates the user to sending the message and controlling the device
    3. reads and sends to the device the text from a local storage file
    4. intercepts the browser links as a mean of communication with Google Assistant
* the DialogFlow code:
    1. a code to communicate a Google Assistant Action with the android app
    2. parses the name of the literature to read and passes it to the app

## Useful resources:

### Shift registers:
http://codius.ru/articles/Arduino_Сдвиговый_регистр_74НС595_или_размножаем_экономим_выходы_платы
http://arduino.ru/Tutorial/registr_74HC595

### SD card:
https://stackoverflow.com/questions/44225379/esp8266-12e-nodemcu-1-0-pin-mapping-for-micro-sd-card
https://forum.arduino.cc/index.php?topic=508777.0
http://www.avislab.com/blog/esp8266-nodemcu-file_ru/
http://www.avislab.com/blog/esp8266-nodemcu-start_ru/


### Web server and file upload handling:
https://github.com/esp8266/Arduino/blob/master/libraries/ESP8266WebServer/examples/SDWebServer/SDWebServer.ino

### Stepper motor:
https://howtomechatronics.com/tutorials/arduino/how-to-control-stepper-motor-with-a4988-driver-and-arduino/
https://ru.aliexpress.com/item/5x-DC-5V-30-Ohm-2-Phase-4-Wire-Mini-Slider-Screw-Stepper-Motor/32701490852.html?spm=a2g0v.search0104.3.1.2e362cc6H33Tna&ws_ab_test=searchweb0_0,searchweb201602_1_10152_10151_10065_10344_10068_10342_10343_5722611_10340_10341_10696_5722911_5711411_5722811_10084_5722711_10083_10618_10307_10059_306_100031_10103_5711511_524_10624_10623_10622_10621_10620_5722511_10125,searchweb201603_2,ppcSwitch_2&algo_expid=68eb6e63-78a1-4801-a68e-65610f32b515-0&algo_pvid=68eb6e63-78a1-4801-a68e-65610f32b515&priceBeautifyAB=0


## Version 2.0:

### Android Studio + Arduino via HC-05
https://www.youtube.com/watch?v=cHj7kfLQyGo

### Bluetooth Helper
https://github.com/BasicAirData/BluetoothHelper

### Use of intent-filter to open text files:
https://www.youtube.com/watch?v=KdSofEevOhE

### Read Text File from Storage in Android Studio:
https://www.youtube.com/watch?v=J6azVvt-9KE

### Interecepting links from browser to open in the app
https://stackoverflow.com/questions/1609573/intercepting-links-from-the-browser-to-open-my-android-app?rq=1
https://github.com/mzule/ActivityRouter/blob/master/README-en.md

### Google Assistant Actions
https://console.actions.google.com/u/0/?pli=1
