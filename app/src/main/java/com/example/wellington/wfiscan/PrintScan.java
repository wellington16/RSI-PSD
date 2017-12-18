package com.example.wellington.wfiscan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrintScan extends AppCompatActivity{

    private static final String TAG = "PrintActivity";
    public WifiManager wifi;
    BroadcastReceiver mWifiScanReceiver;
    public boolean wasConnected;

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

        final ArrayList<String> SSID = new ArrayList<String>();
        final ArrayList<Integer> RSSI = new ArrayList<Integer>();
        final ArrayList<String> BSSID = new ArrayList<String>();

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
                    tratamento(ssidString, rssiString);
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

    protected void tratamento(String redes, String rssids){
        String[] listRedes = redes.split("\n",-1);
        String[] rssidsSeparados= rssids.split("\n");
        ArrayList<Integer> listRssids = new ArrayList<Integer>();

//        for(String number : rssidsSeparados) {
//            numberList.add(Integer.parseInt(number));
//        }
        for(int rssidx = 1; rssidx < rssidsSeparados.length;rssidx ++){
            listRssids.add(Integer.parseInt(rssidsSeparados[rssidx]));
//            System.out.println(listRssids.get(rssidx));
        }
        Collections.reverse(listRssids);
        System.out.println(listRssids);
        int contadoSeEncontrou = 0;
        for (int i = 1; i < listRedes.length; i++) {
//            Integer.parseInt(listRedes[i]);

            if (listRedes[i].equals("Macaco")) {
                chamaTelaMacaco(listRedes[i]);
                break;
            }
            contadoSeEncontrou += 1;
            if (contadoSeEncontrou == listRedes.length-1) {
                Toast.makeText(this, "Não consegui encontrar nenhum animal. :(", Toast.LENGTH_SHORT).show();
                Intent voltar = new Intent(this, MainActivity.class);
                startActivity(voltar);
                finish();
            }

        }
    }

    protected void chamaTelaMacaco(String ssdid){
        String redeTeste = "Macaco";
        if (ssdid.equals(redeTeste)){
            Intent macaco =  new Intent(this,TelaMacacoActivity.class);
            startActivity(macaco);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
