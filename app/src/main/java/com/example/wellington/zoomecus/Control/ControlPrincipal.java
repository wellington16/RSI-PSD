package com.example.wellington.zoomecus.Control;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.wellington.zoomecus.Servico.Servico;

/**
 * Created by wellington on 24/01/18.
 */

public class ControlPrincipal extends AppCompatActivity {
    
        public void controlStartMain (Context context, Intent intent)
        {
            context.startService(intent);
        }

        public boolean controlStopMain (Context context, Intent intent)
        {
            boolean result = context.stopService(intent);
            Log.i("Control ", ""+result);
            return result;
        }

        public void startActivitys(Context context, Intent intent)
        {
            context.startActivity(intent);
        }
}