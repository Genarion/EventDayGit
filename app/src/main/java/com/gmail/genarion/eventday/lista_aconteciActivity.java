package com.gmail.genarion.eventday;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class lista_aconteciActivity extends AppCompatActivity {
    private final String ACTIVITY = "lista_aconteciActivity";
    private ArrayList<AcontecimientoItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aconteci);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                startActivity(new Intent(getApplicationContext(),AnadirAcontecimientoActivity.class));
            }
        });


        // Crear elementos
        items = new ArrayList<AcontecimientoItem>();
        items.add(new AcontecimientoItem("1", "Primer acontecimiento"));
        items.add(new AcontecimientoItem("2", "prime acontecimiento"));
        items.add(new AcontecimientoItem("3", "Segundo acontecimiento"));
        items.add(new AcontecimientoItem("4", "tercer acontecimiento"));
        items.add(new AcontecimientoItem("5", "cuarto acontecimiento"));
        items.add(new AcontecimientoItem("6", "quitno acontecimiento"));
        items.add(new AcontecimientoItem("7", "sexto acontecimiento"));
        items.add(new AcontecimientoItem("8", "septimo acontecimiento"));

        // Se inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Se crea el Adaptador con los datos
        AcontecimientoAdapter adaptador = new AcontecimientoAdapter(items);

        // Se asocia el elemento con una acción al pulsar el elemento
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                MyLog.d("ACTIVITY", "Click en RecyclerView");
                int position = recyclerView.getChildAdapterPosition(v);
                Toast toast = Toast.makeText(lista_aconteciActivity.this, String.valueOf(position) + " " + items.get(position).getId() + " " + items.get(position).getNombre(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Se asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Se muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.acerca_de:
                Intent intent = new Intent(this, acerca_de.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
    @Override
    public void onBackPressed() {
        this.finish();
        //System.exit(0);
    }

    @Override
    protected void onStart() {
        MyLog.d(ACTIVITY,"Estamos en onStart"); //creación del log del onStart
        super.onStart();
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
    protected void onStop() {
        MyLog.d(ACTIVITY,"Estamos en onStop"); //creación del log del onStop
        super.onStop();
    }

    @Override
    protected void onRestart() {
        MyLog.d(ACTIVITY,"Estamos en onRestart"); //creación del log del onRestart
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        MyLog.d(ACTIVITY,"Estamos en onDestroy"); //creación del log del onDestroy
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
