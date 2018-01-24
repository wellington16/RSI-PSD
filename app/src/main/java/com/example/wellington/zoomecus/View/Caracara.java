package com.example.wellington.zoomecus.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wellington.zoomecus.R;
import com.example.wellington.zoomecus.Servico.Servico;

public class Caracara extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracara);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        Intent intent = new Intent(getApplication(), Servico.class);
        stopService(intent);
        super.onDestroy();
    }
}
