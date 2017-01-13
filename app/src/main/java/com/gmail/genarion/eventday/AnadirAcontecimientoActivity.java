package com.gmail.genarion.eventday;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AnadirAcontecimientoActivity extends AppCompatActivity {
    Button button_ok;
    EditText editText;
    String id_editText;
    public static Context context;
    private static final String ACTIVITY = "AnadirAcontecimientoActivity";
    final private int REQUEST_CODE_INTERNET = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_acontecimiento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button_ok = (Button) findViewById(R.id.Button_enviar);
        editText = (EditText) findViewById(R.id.editText_enviar);
        context = this;
        final ProgressBar barraProgreso=(ProgressBar) findViewById(R.id.barraProgresoAnadir);

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextNombre = (EditText) findViewById(R.id.editText_enviar);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextNombre.getWindowToken(), 0);
                if (!isOnline()) {
                    Snackbar.make(view, "No hay conexión!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {

                    String editTextNombreText = editTextNombre.getText().toString();
                    int longitudTexto = editTextNombreText.length();
                    if (longitudTexto == 0) {
                        Snackbar.make(view, "Error!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);

                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            new AnadirAcontecimientoAsyncTask(editTextNombre.getText().toString(), context, barraProgreso,button_ok).execute();
                        } else {
                            // Explicar permiso
                            if (ActivityCompat.shouldShowRequestPermissionRationale(AnadirAcontecimientoActivity.this, Manifest.permission.INTERNET)) {
                                Toast.makeText(context, "El permiso es necesario para utilizar el internet.", Toast.LENGTH_SHORT).show();
                            }

// Solicitar el permiso
                            ActivityCompat.requestPermissions(AnadirAcontecimientoActivity.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_INTERNET);
                        }
                    }
                }


            }
        });
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(context, "No hay conexión internet!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        MyLog.d(ACTIVITY,"Estamos en onResume"); //creación del log del onResume
        super.onResume();
    }

    @Override
    protected void onPause() {
        MyLog.d(ACTIVITY,"Estamos en onPause"); //creación del log del onPause
        super.onPause();
    }

    @Override
    protected void onRestart() {
        MyLog.d(ACTIVITY,"Estamos en onRestart"); //creación del log del onRestart
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        MyLog.d(ACTIVITY,"Estamos en onDestroy"); //creación del log del onDestroy
        super.onDestroy();
    }
}



