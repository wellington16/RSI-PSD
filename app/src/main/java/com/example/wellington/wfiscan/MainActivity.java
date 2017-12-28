package com.example.wellington.wfiscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printScan(View view){
        Intent printScan = new Intent(this, Resultados.class );
        startActivity(printScan);
        finish();
    }

    public void close(View view){
        this.finish();
    }
}
