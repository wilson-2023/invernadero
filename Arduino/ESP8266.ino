
#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <WiFiClientSecure.h>
#include <SoftwareSerial.h>

int temAmbiente;
int humAmbiente;
int humSuelo;
int ventilacion1;
int agua1;
int nivelCO2;
int intruso1;


String estadoVentilacion;
String estadoRiego;
String estadoIntruso;

const char* ssid = "*******";    
const char* password = "******";    
const char* host = "script.google.com";
const int httpsPort = 443;


SoftwareSerial serialJson(D7, D8); // RX, TX
WiFiClientSecure client;

//const char* fingerprint = "46 B2 C3 44 9C 59 09 8B 01 B6 F8 BD 4C FB 00 74 91 2F EF F6";
String GAS_ID = "AKfycbySaoww_opVuVhyezqLi3oaUOS-Oxg60ZcEkOAvaJti0T8Tm1CAY9cg8yUqq6wEU77y";     // id

void setup() 
{
 
  Serial.begin(9600); 
   while (!Serial) continue;
  

  serialJson.begin(9600);
  while (!serialJson) continue;

  Serial.print("conectando ");
  Serial.println(ssid);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
 
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi conectado");
  Serial.println("IP direccion: ");
  Serial.println(WiFi.localIP());

  client.setInsecure();
}

void loop() 
{

  
  if (temAmbiente || humAmbiente || humSuelo|| nivelCO2 > 0){
  
  enviarJson(temAmbiente,humAmbiente,humSuelo,nivelCO2,ventilacion1,agua1,intruso1);
 delay(4000);
  }

extraerJson();

}


void enviarJson(int temAmbiente,int humAmbiente,int humSuelo,int nivelCO2,int ventilacion,int agua,int intruso){

   if(ventilacion1 == 2){
    estadoVentilacion = "Apagado";
    } else if(ventilacion1 == 3){
      estadoVentilacion = "Encendido";
      }
  if(agua1 == 4){
    estadoRiego = "Apagado";
    } else if (agua1 == 5){
      estadoRiego = "Encendido";
      }

  if(intruso1 == 8){
    estadoIntruso = "Intruso";
    } else if (intruso1 == 9){
      estadoIntruso = "Libre";
      } else if (intruso1 == 0){
      estadoIntruso = "Desconectado";
      }
      
  Serial.print("conectando ");
  Serial.println(host);
  
 if (!client.connect(host, httpsPort)) {
    Serial.println("conexion fallida");
    return;
  }

  
  String tempA     =  String(temAmbiente, DEC);
  String humA      =  String(humAmbiente, DEC);
  String humS      =  String(humSuelo, DEC);
  String niCO2     =  String(nivelCO2, DEC);
  String estV      =  String(estadoVentilacion);
  String estR      =  String(estadoRiego);
  String inT       =  String(estadoIntruso);

  
  String url = "/macros/s/" + GAS_ID + "/exec?temperatura=" + tempA + "&ambiente=" + humA + "&suelo=" + humS + "&CO2=" + niCO2 + "&ventilacion=" + estV + "&agua=" + estR + "&intruso=" + inT;
  
  Serial.print("requesting URL: ");
  Serial.println(url);

  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
         "Host: " + host + "\r\n" +
         "User-Agent: BuildFailureDetectorESP8266\r\n" +
         "Connection: close\r\n\r\n");

Serial.println("request sent");
  while (client.connected()) {
  String line = client.readStringUntil('\n');
  if (line == "\r") {
    Serial.println("headers received");
    break;
  }
  }
  String line = client.readStringUntil('\n');
  if (line.startsWith("{\"state\":\"success\"")) {
     Serial.println("esp8266/Arduino CI exitoso!");
  } else {
    Serial.println("esp8266/Arduino CI ha fallado");
  }
  
  Serial.println("reply was:");
  Serial.println("==========");
  Serial.println(line);
  Serial.println("==========");
  Serial.println("cerrando conexion");
   
}

void extraerJson(){
    
    
    StaticJsonDocument<192> doc;
    DeserializationError error = deserializeJson(doc, serialJson);
    if (error) { 
     Serial.print(F("deserializeJson() failed: "));
     Serial.println(error.c_str());
      
      return; 
      }
 
int temperatura = doc["temperatura"];
int humedad = doc["humedad"];
int suelo = doc["suelo"];
int ventilacion = doc["ventilacion"];
int agua = doc["agua"];
int CO2 = doc["CO2"];
int intruso = doc["intruso"]; 

temAmbiente = temperatura;
humAmbiente = humedad;
humSuelo = suelo;
nivelCO2 = CO2;
agua1 = agua;
ventilacion1 = ventilacion;
intruso1 = intruso;

delay(4000);
}
