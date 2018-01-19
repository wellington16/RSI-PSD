package com.example.wellington.wfiscan.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wellington.wfiscan.R;
import com.example.wellington.wfiscan.Servico.Servico;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
//    public static final long TEMPO = (200 * 60);

    Button iniciar;
    Button sair;

    public WifiManager wifi;
    BroadcastReceiver mWifiScanReceiver;
    public boolean wasConnected;

//    private ArrayList<String> SSID = new ArrayList<String>(new HashSet<String>());
//    private ArrayList<Integer> RSSI = new ArrayList<Integer>(new HashSet<Integer>());
//    private ArrayList<String> BSSID = new ArrayList<String>(new HashSet<String>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wasConnected = wifi.isWifiEnabled();
        if (!wasConnected) wifi.setWifiEnabled(true);
//        scanAndShow();

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
            stopService(intent);
        }
    };


//    public void scanAndShow(){
//
//        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//
//        mWifiScanReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context c, Intent intent) {
//
//                TextView ssidView= findViewById(R.id.ssid);
//                TextView rssiView= findViewById(R.id.rssi);
//                TextView bssidView=  findViewById(R.id.bssid);
//                SSID.clear();
//                RSSI.clear();
//                BSSID.clear();
//                if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
//                    List<ScanResult> mScanResults = wifi.getScanResults();
//
//                    String ssidString="";
//                    String bssidString="";
//                    String rssiString="";
//
//                    for (int i = 0; i < mScanResults.size(); i++) {
//                        SSID.add(mScanResults.get(i).SSID);
//                        RSSI.add(mScanResults.get(i).level);
//                        BSSID.add(mScanResults.get(i).BSSID);
//                    }
//
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
//
//                    int maiorElementoRSSI = Collections.max(RSSI);
//                    int indice = RSSI.indexOf(maiorElementoRSSI);
//                    Log.i(TAG,"Indece: " + indice);
//                    encontrarAnimal(indice);
//                }
//            }
//        };
//        registerReceiver(mWifiScanReceiver,
//                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
//        wifi.startScan();
//        Log.i(TAG,"Started Scanning");
//        executarTarefa();
//    }
//
//    public void refreshButton(View view) {
//        unregisterReceiver(mWifiScanReceiver);
////        Intent caracara = new Intent(this, Caracara.class);
////        startActivity(caracara);
////        finish();
//       scanAndShow();
//
//    }
//    protected void encontrarAnimal(int indexEncontrarAnimal){
//        String animal = SSID.get(indexEncontrarAnimal);
//        try {
//            arrayAnimaisTelas(animal);
//        }catch (Exception erro){
//            Toast.makeText(this, "Não consegui encontrar nenhum animal. :(", Toast.LENGTH_SHORT).show();
//            RSSI.removeAll(RSSI);
//            SSID.removeAll(SSID);
//            BSSID.removeAll(BSSID);
//            Intent voltar = new Intent(this, MainActivity.class);
//            startActivity(voltar);
//            finish();
//        }
//    }
//    String animalEncontrado = "";
////    int animalNaoEncontrado = 0;
//    protected void arrayAnimaisTelas(String ssid){
//        System.out.println(ssid);
//        Log.i(TAG,"SSID: "+ssid);
//        final ArrayList<String> ANIMAIS = new ArrayList<String>(){{
//            add("Macaco");
//            add("B.S.I.");
//            add("ReidoPES");
//            add("Eduroam");
//            add("#FORATEMER");
//        }};
//        chamarTelas(ssid, ANIMAIS);
//    }
//
//    private void chamarTelas(String ssid, ArrayList<String> ANIMAIS) {
//
//        for (int i =0; i < ANIMAIS.size();i++) {
//            if (ssid.equals(ANIMAIS.get(i))) {
//                System.out.println("Animal: "+animalEncontrado + " SSid; "+ssid);
//                if (!(animalEncontrado.equals(ANIMAIS.get(i)))) {
//                    animalEncontrado= ssid;
//                    escolherTela(ssid);
//                }
//            }
////            else
////                {
////                animalNaoEncontrado+=1;
////                if(animalNaoEncontrado == ANIMAIS.size()) {
////                    Toast.makeText(this, "Redes cadastradas não" +
////                            " encontradas dentre as redes atuais. :(", Toast.LENGTH_SHORT).show();
////                }
////            }
//        }
//    }
//
//    private void escolherTela(String ssid) {
//        switch (ssid){
//            case "B.S.I.":
//                Intent caracara = new Intent(this, Caracara.class);
//                startActivity(caracara);
//                finish();
//                break;
//            case "#FORATEMER":
//                Intent Macaco = new Intent(this, Macaco.class);
//                startActivity(Macaco);
//                finish();
//                break;
//
//            default: break;
//        }
//    }

//    public void executarTarefa(){
//
//        System.out.println("Inicio");
//        Timer timer = null;
//        if(timer == null){
//            timer = new Timer();
//            TimerTask tarefa = new TimerTask() {
//                @Override
//                public void run() {
//                    try{
//                        System.out.println("Teste agendador");
//                        scanAndShow();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            };
//            timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

//    @Override
//    public void onResume() {
//        if (getIntent().getBooleanExtra("EXIT", false)) {
//            finish();
//        }
//        super.onResume();
//    }

}
