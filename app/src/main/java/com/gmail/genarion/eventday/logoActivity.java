package com.gmail.genarion.eventday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class logoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String languageToLoad  = pref.getString("language", "default"); // your language
        System.out.println(pref.getString("language", "default"));
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        new Timer().schedule(new TimerTask(){
            @Override
            public void run(){
                startActivity(new Intent(getApplicationContext(),lista_aconteciActivity.class));
                finish();
            }
        },2000);
    }
}
