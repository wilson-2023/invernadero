package com.example.invernaderointeligente;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button botonBlutooth,botonWeb, botonTeamViewer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonBlutooth = (Button)findViewById(R.id.botonBluetooth);
        botonWeb=(Button)findViewById(R.id.botonWeb);
        botonTeamViewer=(Button)findViewById(R.id.botonTeamViewer);


        botonBlutooth.setOnClickListener(this);
        botonWeb.setOnClickListener(this);
        botonTeamViewer.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v==botonBlutooth){

            Intent intent = new Intent(getApplicationContext(), com.example.invernaderointeligente.ListaDispositivos.class);
            startActivity(intent);
        }

        if(v==botonWeb){

            Intent intent = new Intent(getApplicationContext(), com.example.invernaderointeligente.VerWeb.class);
            startActivity(intent);
        }

        if(v==botonTeamViewer) {
            PackageManager pm = getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage("com.teamviewer.teamviewer.market.mobile");
            if (intent != null)
                startActivity(intent);
            else
                Log.e("LaunchSettings",
                        "La aplicación no está disponible en el dispositivo");

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnBluet:
                Intent intent = new Intent(getApplicationContext(), com.example.invernaderointeligente.ListaDispositivos.class);
                startActivity(intent);
                //Toast.makeText(this, "Bluetooth", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnWeb:
                Intent intent2 = new Intent(getApplicationContext(), com.example.invernaderointeligente.VerWeb.class);
                startActivity(intent2);
                //Toast.makeText(this, "Web", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCamara:
                PackageManager pm = getPackageManager();
                intent = pm.getLaunchIntentForPackage("com.teamviewer.teamviewer.market.mobile");
                if (intent != null)
                    startActivity(intent);
                else
                    Log.e("LaunchSettings",
                            "La aplicación no está disponible en el dispositivo");


        }


        return super.onOptionsItemSelected(item);
    }
}
