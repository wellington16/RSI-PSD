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
import java.util.List;

public class Resultados extends AppCompatActivity{

    private static final String TAG = "PrintActivity";
    public WifiManager wifi;
    BroadcastReceiver mWifiScanReceiver;
    public boolean wasConnected;
    private int indexEncontrarAnimal;

    final ArrayList<String> SSID = new ArrayList<String>();
    final ArrayList<Integer> RSSI = new ArrayList<Integer>();
    final ArrayList<Integer> RSSIOriginal = new ArrayList<Integer>();
    final ArrayList<String> BSSID = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_scan);

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
                    RSSIOriginal.addAll(RSSI);
                    Collections.sort(RSSI);
                    String animalDoTopo = RSSI.get(RSSI.size()-1).toString();
                    indexEncontrarAnimal = potenciaEncontrada(animalDoTopo);
                    encontrarAnimal(indexEncontrarAnimal);
                }
            }
        };
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
        Log.i(TAG,"Started Scanning");
    }

    protected int potenciaEncontrada(String animalDoTopo){
        int indexAnimal = 0;
        for (int i =0; i < RSSI.size();i++){
            if(RSSIOriginal.get(i).toString().equals(animalDoTopo)){
                indexAnimal += i;
                return indexAnimal;
            }
        }
        return 0;
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
        }};
        int animalNaoEncontrado = 0;
        for (int i =0; i < ANIMAIS.size();i++) {
            if (ssid.equals(ANIMAIS.get(i))) {
                Intent macaco = new Intent(this, Macaco.class);
                startActivity(macaco);
                finish();
            }else{
                animalNaoEncontrado+=1;
                if(animalNaoEncontrado == ANIMAIS.size()) {
                    Toast.makeText(this, "Redes cadastradas não encontradas dentre as redes atuais. :(", Toast.LENGTH_SHORT).show();
                    Intent voltar = new Intent(this, MainActivity.class);
                    startActivity(voltar);
                    finish();
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
