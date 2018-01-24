package com.example.wellington.zoomecus.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wellington.zoomecus.R;
import com.example.wellington.zoomecus.Servico.Servico;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    Button iniciar;
    Button sair;

    public WifiManager wifi;
    public boolean wasConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifi != null;
        wasConnected = wifi.isWifiEnabled();
        if (!wasConnected) wifi.setWifiEnabled(true);

        iniciar = findViewById(R.id.iniciar);
        sair = findViewById(R.id.sair);
        iniciar.setOnClickListener(iniciarServico);
        sair.setOnClickListener(pararServico);

    }

    public View.OnClickListener iniciarServico = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplication(), Servico.class);
            startService(intent);
        }
    };
    public View.OnClickListener pararServico = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplication(), Servico.class);
            boolean result = stopService(intent);
            Log.i("Script", ""+result);
            if (result || !result){
                MainActivity.this.finish();
            }
        }
    };

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        this.finish();
    }
}
