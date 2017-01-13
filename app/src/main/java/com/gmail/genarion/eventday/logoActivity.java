package com.gmail.genarion.eventday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class logoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Timer().schedule(new TimerTask(){
            @Override
            public void run(){
                startActivity(new Intent(getApplicationContext(),lista_aconteciActivity.class));
                finish();
            }
        },2000);
    }
}
