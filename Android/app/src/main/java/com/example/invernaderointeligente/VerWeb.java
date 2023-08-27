package com.example.invernaderointeligente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VerWeb extends AppCompatActivity {

    private WebView miWeb;
    SwipeRefreshLayout actualizarPagina;
    String url= "https://docs.google.com/spreadsheets/d/1hjwGUpEZMoRRyM4R6k6_5MKBAPeuUU_Qf3uWH1v7onI/edit#gid=0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_web);

        miWeb = findViewById(R.id.paginaWeb);
        miWeb.getSettings().setJavaScriptEnabled(true);
        miWeb.setWebViewClient(new WebViewClient());
        miWeb.loadUrl(url);

        actualizarPagina = findViewById(R.id.deslizar);

        actualizarPagina.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                miWeb.loadUrl(url);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        actualizarPagina.setRefreshing(false);

                    }
                },4*1000);

            }
        });
    }
}