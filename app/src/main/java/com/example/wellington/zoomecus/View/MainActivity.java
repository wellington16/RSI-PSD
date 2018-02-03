package com.example.wellington.zoomecus.View;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.wellington.zoomecus.Control.ControlePrincipal;
import com.example.wellington.zoomecus.R;
import com.example.wellington.zoomecus.Service.Service;

public class MainActivity extends AppCompatActivity {

    ControlePrincipal controlePrincipal = new ControlePrincipal();

    public WifiManager wifi;
    public boolean wasConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifi != null;
        wasConnected = wifi.isWifiEnabled();
        if (!wasConnected) wifi.setWifiEnabled(true);
    }

    public void iniciar(View view)
    {
            Intent intent = new Intent(getApplication(), Service.class);
            controlePrincipal.controlStartMain(getApplication(),intent);
            Toast.makeText(getApplication(),"Aguarde... ;)", Toast.LENGTH_SHORT).show();
    }

    public void sair(View view)
    {
        Intent intent = new Intent(getApplication(), Service.class);
        boolean  result = controlePrincipal.controlStopMain(getApplication(),intent);
        onBackPressed( result);
    }

    public void onBackPressed(boolean result)
    {
        if(result || !result)
        {
            MainActivity.this.finish();
        }

    }
}
