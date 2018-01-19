package com.example.wellington.wfiscan.Servico;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.wellington.wfiscan.View.MainActivity;
import com.example.wellington.wfiscan.View.Caracara;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wellington on 18/01/18.
 */

public class Servico extends Service {
    public ArrayList<Worker> threads = new ArrayList<Worker>();

    private static final String TAG = "MyActivity";
    public static final long TEMPO = (200 * 60);

    public WifiManager wifi;
    BroadcastReceiver mWifiScanReceiver;

    private ArrayList<String> SSID = new ArrayList<String>(new HashSet<String>());
    private ArrayList<Integer> RSSI = new ArrayList<Integer>(new HashSet<Integer>());
    private ArrayList<String> BSSID = new ArrayList<String>(new HashSet<String>());


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("Script", "onCreate");
    }
    @Override
    public int onStartCommand(Intent intent, int flag, int startId){
        Log.i("Script", "onStartCommand");

        Worker worker = new Worker(startId);
        worker.start();
        threads.add(worker);

//        return (START_REDELIVER_INTENT);
        return (super.onStartCommand(intent,flag,startId));
    }

    class Worker extends Thread{

//        public int count;
        public int startId;
        public boolean ativo = true;

        public Worker(int startId) {
            this.startId = startId;
        }

        public void run(){
            while (ativo == true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scanAndShow();
                Log.i("Script", "Metodo Iniciado: ");
            }
            stopSelf(startId);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        pararServico();
    }

    private void pararServico() {
        for(int i = 0, tam = threads.size(); i< tam; i++){
            threads.get(i).ativo = false;
        }
    }



    public void scanAndShow(){

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mWifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {

//                TextView ssidView= findViewById(R.id.ssid);
//                TextView rssiView= findViewById(R.id.rssi);
//                TextView bssidView=  findViewById(R.id.bssid);
                SSID.clear();
                RSSI.clear();
                BSSID.clear();
                if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                    List<ScanResult> mScanResults = wifi.getScanResults();

                    String ssidString="";
                    String bssidString="";
                    String rssiString="";

                    for (int i = 0; i < mScanResults.size(); i++) {
                        SSID.add(mScanResults.get(i).SSID);
                        RSSI.add(mScanResults.get(i).level);
                        BSSID.add(mScanResults.get(i).BSSID);
                    }

//                    for (int i=0;i<SSID.size();i++){
//
//                        ssidString = ssidString+"\n"+SSID.get(i);
//                        bssidString = bssidString+"\n"+BSSID.get(i);
//                        rssiString = rssiString+"\n"+RSSI.get(i);
//                    }
//
//                    ssidView.setText(ssidString);
//                    rssiView.setText(rssiString);
//                    bssidView.setText(bssidString);

                    int maiorElementoRSSI = Collections.max(RSSI);
                    int indice = RSSI.indexOf(maiorElementoRSSI);
                    Log.i(TAG,"Indece: " + indice);
                    encontrarAnimal(indice);
                }
            }
        };
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
        Log.i(TAG,"Started Scanning");
//        executarTarefa();
    }

    protected void encontrarAnimal(int indexEncontrarAnimal){
        String animal = SSID.get(indexEncontrarAnimal);
        try {
            arrayAnimaisTelas(animal);
        }catch (Exception erro){
            Toast.makeText(this, "Não consegui encontrar nenhum animal. :(", Toast.LENGTH_SHORT).show();
            RSSI.removeAll(RSSI);
            SSID.removeAll(SSID);
            BSSID.removeAll(BSSID);
            Intent voltar = new Intent(this, MainActivity.class);
            startActivity(voltar);
        }
    }
    String animalEncontrado = "";
    //    int animalNaoEncontrado = 0;
    protected void arrayAnimaisTelas(String ssid){
        System.out.println(ssid);
        Log.i(TAG,"SSID: "+ssid);
        final ArrayList<String> ANIMAIS = new ArrayList<String>(){{
            add("Macaco");
            add("B.S.I.");
            add("ReidoPES");
            add("Eduroam");
            add("#FORATEMER");
        }};
        chamarTelas(ssid, ANIMAIS);
    }

    private void chamarTelas(String ssid, ArrayList<String> ANIMAIS) {

        for (int i =0; i < ANIMAIS.size();i++) {
            if (ssid.equals(ANIMAIS.get(i))) {
                System.out.println("Animal: "+animalEncontrado + " SSid; "+ssid);
                if (!(animalEncontrado.equals(ANIMAIS.get(i)))) {
                    animalEncontrado= ssid;
                    escolherTela(ssid);
                }
            }

        }
    }

    private void escolherTela(String ssid) {
        switch (ssid){
            case "B.S.I.":
                Intent caracara = new Intent(this, Caracara.class);
                startActivity(caracara);
                break;
            case "#FORATEMER":
                Intent Macaco = new Intent(this, com.example.wellington.wfiscan.View.Macaco.class);
                startActivity(Macaco);
                break;
            default: break;
        }
    }



}
