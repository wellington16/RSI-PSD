package com.example.wellington.zoomecus.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.wellington.zoomecus.Control.ControlePrincipal;
import com.example.wellington.zoomecus.Model.ConfiguracaoRede;
import com.example.wellington.zoomecus.View.Caracara;
import com.example.wellington.zoomecus.View.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by wellington on 18/01/18.
 */

public class Service extends android.app.Service {
    public ArrayList<Worker> threads = new ArrayList<>();
    private final String TAG = "MyActivity";
    private boolean contador = false;
    private WifiManager wifi;
    private BroadcastReceiver mWifiScanReceiver;
    private String animalEncontrado = "";
    private Boolean contExit = true;

    private ControlePrincipal controlePrincipal = new ControlePrincipal();

    private ArrayList<ConfiguracaoRede> SSID = new ArrayList<>();

    final ArrayList<String> ANIMAIS = new ArrayList<String>()
    {{
        add("12:13:14:15:09:10");
        add("ec:10:12:14:09:00");
    }};

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId)
    {

        Log.i("Script", "onStartCommand");
        Worker worker = new Worker(startId);
        worker.start();
        threads.add(worker);
//        return (START_REDELIVER_INTENT);
        System.out.println("Start: "+startId);
        System.out.println("flag: "+flag);
        return (super.onStartCommand(intent,flag,startId));
    }
    int encerra = 500;
    private class Worker extends Thread
    {
        private int startId;
        private boolean ativo = true;
        private Worker(int startId) {
            this.startId = startId;
        }
        public void run()
        {
            while (ativo)
            {
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    Log.i("Script","ERRO grave no Worker:" + e);
                }
                if(!contador)
                {
                    scanAndShow();
                    Log.i("Script", "Metodo Iniciado: ");

                }
            }
            stopSelf(startId);
            SSID.clear();
            Log.i("Script", "stopself " + startId);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        pararServico();
        Log.i("Script", "ondestroy ");
        contador = true;
    }

    private void pararServico()
    {
        for(int i = 0, tam = threads.size(); i< tam; i++)
        {
            threads.get(i).ativo = false;
        }
    }

    private void scanAndShow()
    {

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mWifiScanReceiver = new BroadcastReceiver()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onReceive(Context c, Intent intent)
            {
                if (Objects.equals(intent.getAction(), WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
                {
                    List<ScanResult> mScanResults = wifi.getScanResults();

                    for (int i = 0; i < mScanResults.size(); i++)
                    {
                        ConfiguracaoRede  configuracaoRede = new ConfiguracaoRede();
                        configuracaoRede.setLevel(mScanResults.get(i).level);
                        configuracaoRede.setBssid(mScanResults.get(i).BSSID);
                        configuracaoRede.setRssid(mScanResults.get(i).SSID);
                        SSID.add(configuracaoRede);

                        Log.i(TAG,"Nome da rede: " + mScanResults.get(i).SSID);
                        Log.i(TAG,"MAC: " + mScanResults.get(i).BSSID);
                    }

                    ConfiguracaoRede  maiorElemento = Collections.max(SSID);
                    chamarTelas(maiorElemento, ANIMAIS);
                }
            }
        };
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
        Log.i(TAG,"Started Scanning");
    }


    private void chamarTelas(ConfiguracaoRede maiorElemento, ArrayList<String> ANIMAIS)
    {
        System.out.println(maiorElemento.getRssid());
        Log.i(TAG,"MAC: "+ maiorElemento.getBssid());
        boolean exist = false;
        String elementoEncontrado;
        MainActivity mainActivity = new MainActivity();
        Service service = new Service();
        for (int i =0; i < ANIMAIS.size();i++)
        {
            if (maiorElemento.getBssid().equals(ANIMAIS.get(i)))
            {
                elementoEncontrado = maiorElemento.getBssid();
                exist = true;
                System.out.println("Animal: "+animalEncontrado + " BSSid: "+maiorElemento.getBssid());
                if (!(animalEncontrado.equals(ANIMAIS.get(i))))
                {
                    if(mainActivity.getBaseContext() == (service.getBaseContext()))
                    animalEncontrado= elementoEncontrado;
                    escolherTela(elementoEncontrado);
                    break;
                }
            }
        }
        if (!exist)
        {
            if (contExit) {
                Toast.makeText(getApplicationContext(), "Animal " +
                        "não encontrado", Toast.LENGTH_SHORT).show();
                contExit= false;
            }
        }
    }

    private void escolherTela(String bssid)
    {
        switch (bssid)
        {
            case "12:13:14:15:09:10":
                //TrojanHorse
                Intent caracara1 = new Intent(this, Caracara.class);
                vibrar();
                startActivity(caracara1);
                break;
            case "ec:10:12:14:09:00":
                //Macaco
                Intent Macaco = new Intent(this, com.example.wellington.zoomecus.View.Macaco.class);
                vibrar();
                startActivity(Macaco);
                break;
            default: break;
        }
    }
    private void vibrar()
    {
        Vibrator vibra = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 1000;//'1000' é o tempo em milissegundos, é basicamente o tempo de duração da vibração. portanto, quanto maior este numero, mais tempo de vibração você irá ter
        vibra.vibrate(milliseconds);
    }
}
