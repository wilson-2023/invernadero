package com.example.invernaderointeligente;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class VerSensores extends AppCompatActivity {

    Button btnOnLuz, btnOffLuz;
    TextView txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3,sensorView4,sensorView5,sensorView6;
    Handler bluetoothIn;
    TextView estadoRiego1,estadoVentilacion1, estadoIntruso1;

    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ver_sensores);

        //Link the buttons and textViews to respective views
        btnOnLuz = (Button) findViewById(R.id.botonOnLuz);
        btnOffLuz = (Button) findViewById(R.id.botonOffLuz);


        txtString = (TextView) findViewById(R.id.txtString);
        txtStringLength = (TextView) findViewById(R.id.txtTamaño);
        sensorView0 = (TextView) findViewById(R.id.temp);
        sensorView1 = (TextView) findViewById(R.id.hum_ambiente);
        sensorView2 = (TextView) findViewById(R.id.sensor2);
        sensorView3 = (TextView) findViewById(R.id.sensor3);
        sensorView4 = (TextView) findViewById(R.id.hum_suelo);
        sensorView5 = (TextView) findViewById(R.id.nivel_co2);
        sensorView6 = (TextView) findViewById(R.id.sensor6);

        estadoRiego1 = (TextView) findViewById(R.id.estadoRiego);
        estadoVentilacion1 = (TextView)findViewById(R.id.estadoVentilacion);
        estadoIntruso1 = (TextView)findViewById(R.id.estadoIntruso);
        // txtOn  = (TextView)findViewById(R.id.vtOn);
        //txtOff=(TextView)findViewById(R.id.vtOff);


        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {										//if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);      								//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        txtString.setText("  Datos Recividos = " + dataInPrint);
                        int dataLength = dataInPrint.length();							//get length of data received
                        txtStringLength.setText("  Tamaño String  = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')								//if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(13, 15);             //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(10, 12);            //same again...
                            String sensor2 = recDataString.substring(1, 2);
                            String sensor3 = recDataString.substring(3, 4);
                            String sensor4 = recDataString.substring(7, 9);
                            String sensor5 = recDataString.substring(16,19);
                            String sensor6 = recDataString.substring(5, 6);

                            sensorView0.setText("" + sensor0 + "°C");	// tenperatura
                            sensorView1.setText("" + sensor1 + "%");    // humedad ambiente
                            sensorView2.setText("" + sensor2 + "");     // estado ventilacion
                            sensorView3.setText("" + sensor3 + "");     // estado riego
                            sensorView4.setText("" + sensor4 + "%");    // humedad suelo
                            sensorView5.setText("" + sensor5 + "ppm");  // nivel CO2
                            sensorView6.setText("" + sensor6 + "");     // estado camara

                            if (sensorView3.getText().toString().equals("5")){
                                estadoRiego1.setBackgroundColor(Color.parseColor("#00BB2D"));
                                estadoRiego1.setText("ENCENDIDO");

                            }else if (sensorView3.getText().toString().equals("4")){
                                estadoRiego1.setBackgroundColor(Color.parseColor("#FF0000"));
                                estadoRiego1.setText("APAGADO");
                            }

                            if (sensorView2.getText().toString().equals("3")){
                                estadoVentilacion1.setBackgroundColor(Color.parseColor("#00BB2D"));
                                estadoVentilacion1.setText("ENCENDIDO");

                            }else if (sensorView2.getText().toString().equals("2")){
                                estadoVentilacion1.setBackgroundColor(Color.parseColor("#FF0000"));
                                estadoVentilacion1.setText("APAGADO");
                            }

                            if (sensorView6.getText().toString().equals("8")){
                                estadoIntruso1.setBackgroundColor(Color.parseColor("#00BB2D"));
                                estadoIntruso1.setText("INTRUSO");

                            }else if (sensorView6.getText().toString().equals("9")){
                                estadoIntruso1.setBackgroundColor(Color.parseColor("#00BB2D"));
                                estadoIntruso1.setText("LIBRE");

                            }else if (sensorView6.getText().toString().equals("0")){
                                estadoIntruso1.setBackgroundColor(Color.parseColor("#0000FF"));
                                estadoIntruso1.setText("DESCONECTADO");
                            }




                            //if (sensorView3.getText().toString().equals("5")){
                                //btnOn.setBackgroundColor(Color.parseColor("#00BB2D"));

                                //txtColor.setBackgroundColor(Color.parseColor("#00BB2D"));

                            //} else if (sensorView3.getText().toString().equals("4")) {
                                //btnOn.setBackgroundColor(Color.parseColor("#B5B8B1"));
                           // }

                            //if (sensorView3.getText().toString().equals("5")){
                                //btnOff.setBackgroundColor(Color.parseColor("#B5B8B1"));


                           // }else if (sensorView3.getText().toString().equals("4")) {
                               // btnOff.setBackgroundColor(Color.parseColor("#FF0000"));
                           // }


                        }


                        recDataString.delete(0, recDataString.length()); 					//clear all string data
                        //strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();


        // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
        btnOffLuz.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("0");    // Send "0" via Bluetooth
                Toast.makeText(getBaseContext(), "Luz apagado", Toast.LENGTH_SHORT).show();
                btnOffLuz.setBackgroundColor(Color.parseColor("#bb0076"));
                btnOnLuz.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        btnOnLuz.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("1");    // Send "1" via Bluetooth
                Toast.makeText(getBaseContext(), "Luz encendido", Toast.LENGTH_SHORT).show();
                btnOnLuz.setBackgroundColor(Color.parseColor("#80bb00"));
                btnOffLuz.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

    }


    @SuppressLint("MissingPermission")
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(ListaDispositivos.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    @SuppressLint("MissingPermission")
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }
}