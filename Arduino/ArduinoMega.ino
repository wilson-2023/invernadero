#include <MQ135.h>
#include <ArduinoJson.h>
#include <Wire.h>      
#include <LiquidCrystal_I2C.h>    
#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <DHT_U.h>
#include <SoftwareSerial.h>
#define DHTTYPE DHT11
//#define RELE 13    

int temperaturaAmbiente;
int humedadAmbiente;
int humedadSuelo;
int porcentajeSuelo;
int estadoSuelo;
int estadoTemperatura;
int sensorVentilacion = 6;
int sensorRiego = 7;
int nivelCO2;
int ppm = A1;

char inbyte = 0;
int DHTPin = 8;
int led = 12;

char character = 0;
int intruso;

int sensorValue[7] = {0,0,0,0,0,0,0};
int voltageValue[7] = {0,0,0,0,0,0,0};


MQ135 gasSensor = MQ135(ppm);

SoftwareSerial serialRas(51, 53); // RX, TX
SoftwareSerial serialJson(10, 11); // RX, TX

DHT dht(DHTPin, DHTTYPE);
 
LiquidCrystal_I2C lcd(0x27,16,2);

void setup() {
  // initialise serial communications at 9600 bps:
  Serial.begin(9600);

  serialJson.begin(9600);
  while (!serialJson) continue;

  serialRas.begin(9600);
  //while (!serialRas) continue;

    lcd.begin(16,2);
    lcd.init(); //initialize the lcd
    lcd.backlight(); //open the backlight
    
  pinMode(led, OUTPUT);
  digitalWrite(led, LOW);

  pinMode(sensorVentilacion, OUTPUT);
  digitalWrite(sensorVentilacion, HIGH);
  
  pinMode(sensorRiego, OUTPUT);
  digitalWrite(sensorRiego, HIGH);

  dht.begin();

}
 
void loop() {
  
   nivelCO2 = gasSensor.getPPM();

  
   humedadAmbiente = dht.readHumidity();
   temperaturaAmbiente = dht.readTemperature();
 
   if (isnan(humedadAmbiente) || isnan(temperaturaAmbiente)) {
      Serial.println("Failed to read from DHT sensor!");
      return;
   }

    if (temperaturaAmbiente >= 34 ||nivelCO2 >= 900 || humedadAmbiente >= 70){
    estadoTemperatura = 3; // ESTADO DE ventilador
    digitalWrite(sensorVentilacion, LOW);
      
      }else{
        estadoTemperatura = 2;
        digitalWrite(sensorVentilacion, HIGH);
        }

   humedadSuelo =analogRead(A0);
   porcentajeSuelo = map(humedadSuelo, 1023, 0, 0, 100);
   
   if (porcentajeSuelo <= 10){
    estadoSuelo = 5;
    digitalWrite(sensorRiego, LOW);
   
    }else{
    estadoSuelo = 4;
    digitalWrite(sensorRiego, HIGH);
    }

 
   
  readSensors();
  getVoltageValue();
  printLCD();
  sendAndroidValues();
  enviarJson();
  leerSerial();


  
  //when serial values have been received this will be true
  
  if (Serial.available() > 0)
  {
    inbyte = Serial.read();
    if (inbyte == '0')
    {
      //LED off
      digitalWrite(led, LOW);
    }
    if (inbyte == '1')
    {
      //LED on
      digitalWrite(led, HIGH);
     
    }
  }

  delay(2000);
}
 
void readSensors()
{
  // read the analog in value to the sensor array
  
  sensorValue[0] = estadoTemperatura;
  sensorValue[1] = estadoSuelo;
  sensorValue[2] = intruso;
  sensorValue[3] = porcentajeSuelo;
  sensorValue[4] = humedadAmbiente;
  sensorValue[5] = temperaturaAmbiente;
  sensorValue[6] = nivelCO2;
}

//sends the values from the sensor over serial to BT module
void sendAndroidValues()
 {
  //puts # before the values so our app knows what to do with the data
  Serial.print('#');
  //for loop cycles through 4 sensors and sends values via serial
  for(int k=0; k<7; k++)
  {
    Serial.print(voltageValue[k]);
    Serial.print('+');
    //technically not needed but I prefer to break up data values
    //so they are easier to see when debugging
  }
 Serial.print('~'); //used as an end of transmission character - used in app for string length
 Serial.println();
 delay(10);        //added a delay to eliminate missed transmissions

}


void getVoltageValue(){
  
  for (int x = 0; x < 7; x++)
  {
    voltageValue[x] = (sensorValue[x]);
  }
}

void enviarJson(){

    String json;
    
    StaticJsonDocument<64> doc;
    
    doc["temperatura"] = temperaturaAmbiente;
    doc["humedad"] = humedadAmbiente;
    doc["suelo"] = porcentajeSuelo;
    doc["ventilacion"] = estadoTemperatura;
    doc["agua"] = estadoSuelo;
    doc["CO2"] = nivelCO2;
    doc["intruso"] = intruso;
 
    serializeJson(doc, json);
    serialJson.println(json);
    //Serial.println(json);
        
}

void leerSerial(){
  
  if (serialRas.available() > 0) {
     character = serialRas.read();
     int convertido = String(character).toInt();

     intruso = convertido;
 
  }
   
   else if (serialRas.available() < 1) {
    //Serial.println("sin datos");
    intruso = 0;
 
   }
 }
  
void printLCD()
{
    lcd.setCursor(0, 0);
    lcd.print("TeA=");
    lcd.print(temperaturaAmbiente);
    lcd.print("\337C");
    //lcd.print("C");

    lcd.setCursor(9, 0);
    lcd.print("HuA=");
    lcd.print(humedadAmbiente);
    lcd.print("%");
    
    lcd.setCursor(0, 1);
    lcd.print("HuS=");
    lcd.print(porcentajeSuelo);
    lcd.print("%");

    lcd.setCursor(8, 1);
    lcd.print("Gas=");
    lcd.print(nivelCO2);
    lcd.print("ppm");

}
