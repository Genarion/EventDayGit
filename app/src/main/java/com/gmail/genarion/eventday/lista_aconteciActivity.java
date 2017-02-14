package com.gmail.genarion.eventday;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class lista_aconteciActivity extends AppCompatActivity {
    private final String ACTIVITY = "lista_aconteciActivity";
    private ArrayList<AcontecimientoItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Obtengo las preferencias y el booleano guardarAcontecimiento para lanzar la actividad mostrarAconte o no
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(
                this);
        boolean ultimoAconteci=pref.getBoolean("guardarAcontecimiento", false);
        if(ultimoAconteci){
            SharedPreferences prefs = getSharedPreferences("Ajustes", Context.MODE_PRIVATE);
            //recogemos
            String id = prefs.getString("id", "");
            if(!id.equals("")){
                this.startActivity(new Intent(this, mostrar_acontecimiento.class));
            }
        }


        setContentView(R.layout.activity_lista_aconteci);
        //Up button
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
                startActivity(new Intent(getApplicationContext(),AnadirAcontecimientoActivity.class));
            }
        });


        // Crear elementos
        rellenaLista();

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
            case R.id.configuration:
                Intent intent2 = new Intent(this,SettingsActivity.class);
                this.startActivity(intent2);
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

    private void rellenaLista(){
        items = new ArrayList<AcontecimientoItem>();
        //Aqui creamos la bbdd
        AcontecimientosSQLiteHelper usdbh = new  AcontecimientosSQLiteHelper(this,"test1.db", null, 1);
        //Hacemos una comprobacion de si la bbdd que hemos creado existe.
        if(usdbh == null){
            //que no existe, pues enviamos un mylog.
            MyLog.i(ACTIVITY, "no hay Acontecimientos");
        }else{//que existe pues lo muestra.
            SQLiteDatabase db = usdbh.getReadableDatabase();
            String[] args = new String[] {};
            Cursor cursor = db.rawQuery(" SELECT id,nombre,inicio,fin FROM acontecimiento ORDER BY inicio DESC ", args);
            // Se inicializa el RecyclerView y se crea,
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst()) {
                items.removeAll(items);

                //Recorremos el cursor hasta que no haya más registros
                do{
                    //recogemos los datos y los asignamos a una variable.
                    String id =cursor.getString(cursor.getColumnIndex("id"));
                    String nombreAcontecimiento = cursor.getString(cursor.getColumnIndex("nombre"));
                    String inicioSinFormato = cursor.getString(cursor.getColumnIndex("inicio"));
                    // Formato para parsear
                    SimpleDateFormat dateParse = new SimpleDateFormat("yyyymmddhhmm");
                    // el que formatea
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    //creamos el date
                    Date date = null;
                    try {
                        date = dateParse.parse(inicioSinFormato);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String inicio=dateFormat.format(date);
                    String finSinFormato = cursor.getString(cursor.getColumnIndex("fin"));

                    try {
                        date = dateParse.parse(finSinFormato);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String fin=dateFormat.format(date);
                    items.add(new AcontecimientoItem(id,nombreAcontecimiento,inicio,fin));

                }while(cursor.moveToNext());

                // Se crea el Adaptador con los datos
                AcontecimientoAdapter adaptador = new AcontecimientoAdapter(items);

                // Se asocia el elemento con una acción al pulsar el elemento
                adaptador.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int position = recyclerView.getChildAdapterPosition(v);
                        // Acción al pulsar el elemento
                        //Para pasar los datos al fichero de Ajustes
                        SharedPreferences prefs =
                                getSharedPreferences("Ajustes", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("id", items.get(position).getId());
                        //System.out.println(items.get(position));
                        //System.out.println(items.get(position).getId());
                        editor.commit();
                        //abrimos la nueva actividad
                        startActivity(new Intent(lista_aconteciActivity.this, mostrar_acontecimiento.class));
                        MyLog.d(ACTIVITY, "Click en RecyclerView");
                    }
                });

                // Se asocia el Adaptador al RecyclerView
                recyclerView.setAdapter(adaptador);

                // Se muestra el RecyclerView en vertical
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }else{
                Snackbar.make(recyclerView, "No hay acontecimientos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            //fin else arriba
        }
    }
}
