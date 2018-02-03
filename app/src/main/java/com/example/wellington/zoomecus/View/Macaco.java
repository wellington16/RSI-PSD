package com.example.wellington.zoomecus.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wellington.zoomecus.Control.ControlePrincipal;
import com.example.wellington.zoomecus.R;
import com.example.wellington.zoomecus.Service.Service;

public class Macaco extends AppCompatActivity {

    ControlePrincipal controlePrincipal = new ControlePrincipal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macaco);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplication(), Service.class);
        controlePrincipal.controlStopMain(getApplication(),intent);
        finish();
    }

}
