package com.example.wellington.wfiscan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    public WifiManager wifi;
    BroadcastReceiver mWifiScanReceiver;
    public boolean wasConnected;

    private ArrayList<String> SSID = new ArrayList<String>(new HashSet<String>());
    private ArrayList<Integer> RSSI = new ArrayList<Integer>(new HashSet<Integer>());
    private ArrayList<String> BSSID = new ArrayList<String>(new HashSet<String>());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wasConnected = wifi.isWifiEnabled();
        if (!wasConnected) wifi.setWifiEnabled(true);

        scanAndShow();
    }
    public void scanAndShow(){

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mWifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                String ssidString="";
                String bssidString="";
                String rssiString="";

                TextView ssidView= findViewById(R.id.ssid);
                TextView rssiView= findViewById(R.id.rssi);
                TextView bssidView=  findViewById(R.id.bssid);

                if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                    List<ScanResult> mScanResults = wifi.getScanResults();

                    for (int i = 0; i < mScanResults.size(); i++) {
                        SSID.add(mScanResults.get(i).SSID);
                        RSSI.add(mScanResults.get(i).level);
                        BSSID.add(mScanResults.get(i).BSSID);
                    }

                    for (int i=0;i<SSID.size();i++){
                        ssidString = ssidString+"\n"+SSID.get(i);
                        bssidString = bssidString+"\n"+BSSID.get(i);
                        rssiString = rssiString+"\n"+RSSI.get(i);
                    }

                    ssidView.setText(ssidString);
                    rssiView.setText(rssiString);
                    bssidView.setText(bssidString);

                    int maiorElementoRSSI = Collections.max(RSSI);
                    int indice = RSSI.indexOf(maiorElementoRSSI);

                    encontrarAnimal(indice);

                }
            }
        };
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
        Log.i(TAG,"Started Scanning");
    }

    public void refreshButton(View view) {
        unregisterReceiver(mWifiScanReceiver);
        scanAndShow();
    }
    protected void encontrarAnimal(int indexEncontrarAnimal){
        String animal = SSID.get(indexEncontrarAnimal);
        try {
            chamarTelas(animal);
        }catch (Exception erro){
            Toast.makeText(this, "Não consegui encontrar nenhum animal. :(", Toast.LENGTH_SHORT).show();
            RSSI.removeAll(RSSI);
            SSID.removeAll(SSID);
            BSSID.removeAll(BSSID);
            Intent voltar = new Intent(this, MainActivity.class);
            startActivity(voltar);
            finish();
        }
    }

    protected void chamarTelas(String ssid){
        System.out.println(ssid);
        final ArrayList<String> ANIMAIS = new ArrayList<String>(){{
            add("Macaco");
            add("B.S.I.");
            add("eduroam");
        }};
        int animalNaoEncontrado = 0;
        for (int i =0; i < ANIMAIS.size();i++) {
            if (ssid.equals(ANIMAIS.get(i))) {
                RSSI.removeAll(RSSI);
                SSID.removeAll(SSID);
                BSSID.removeAll(BSSID);
                Intent macaco = new Intent(this, Macaco.class);
                startActivity(macaco);
                finish();
            }else{
                animalNaoEncontrado+=1;
                if(animalNaoEncontrado == ANIMAIS.size()) {
                    RSSI.removeAll(RSSI);
                    SSID.removeAll(SSID);
                    BSSID.removeAll(BSSID);
                    Toast.makeText(this, "Redes cadastradas não" +
                            " encontradas dentre as redes atuais. :(", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
