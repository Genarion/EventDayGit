package com.gmail.genarion.eventday;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class mostrar_acontecimiento extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;
    private Context context;
    private String id;
    private Button botonMapa;
    private final String ACTIVITY = "mostrar_Acontecimiento";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_mostrar_acontecimiento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        botonMapa = (Button) findViewById(R.id.mapaGoogle);

        botonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MapsActivity.class));
            }
        });
        SharedPreferences prefs =
                getSharedPreferences("Ajustes", Context.MODE_PRIVATE);
        id = prefs.getString("id_acontecimiento", "0");
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fabMostrar_Acontecimiento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              AcontecimientosSQLiteHelper    usdbh =
                        new AcontecimientosSQLiteHelper (context, "test.db", null, 1);
                //instancia la db.
                SQLiteDatabase db = usdbh.getReadableDatabase();

                String[] argsID = new String[] {id};
                Cursor cursor = db.rawQuery(" SELECT * FROM evento WHERE id=? ", argsID);
                //Nos aseguramos de que existe al menos un registro
                if (cursor.moveToFirst()) {
                    startActivity(new Intent(getApplicationContext(), EventosActivity.class));
                }else{
                    Snackbar.make(view, "No hay eventos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        //leer de la base de datos.
        AcontecimientosSQLiteHelper usdbh =
                //new AcontecimientosSQLiteHelper(this, Environment.getExternalStorageDirectory()+"/DBAcontecimientos.db", null, 1);
                new AcontecimientosSQLiteHelper(this, "test.db", null, 1);
        //instancia la db.
        SQLiteDatabase db = usdbh.getReadableDatabase();

        String[] argsID = new String[] {id};
        Cursor cursor = db.rawQuery(" SELECT * FROM acontecimiento WHERE id=? ", argsID);

        //Para comprobar que haya algun dato
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            LinearLayout layoutPrincipal = (LinearLayout) findViewById(R.id.VerAcontecimiento);
            layoutPrincipal.setOrientation(LinearLayout.VERTICAL);



            do{
                String nombreAcontecimiento = cursor.getString(cursor.getColumnIndex("nombre"));
                String organizador = cursor.getString(cursor.getColumnIndex("organizador"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                String portada = cursor.getString(cursor.getColumnIndex("portada"));
                String inicio = cursor.getString(cursor.getColumnIndex("inicio"));
                String fin = cursor.getString(cursor.getColumnIndex("fin"));
                String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
                String localidad = cursor.getString(cursor.getColumnIndex("localidad"));
                String codPostal = cursor.getString(cursor.getColumnIndex("cod_postal"));
                String provincia = cursor.getString(cursor.getColumnIndex("provincia"));
                String longitud = cursor.getString(cursor.getColumnIndex("longitud"));;
                String latitud = cursor.getString(cursor.getColumnIndex("latitud"));
                String telefono = cursor.getString(cursor.getColumnIndex("telefono"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String web = cursor.getString(cursor.getColumnIndex("web"));
                String facebook = cursor.getString(cursor.getColumnIndex("facebook"));
                String twitter = cursor.getString(cursor.getColumnIndex("twitter"));
                String instagram = cursor.getString(cursor.getColumnIndex("instagram"));

                if (!nombreAcontecimiento.isEmpty()) crearLayoutConImagen(nombreAcontecimiento, R.drawable.ic_nombre_acontecimiento, layoutPrincipal);
                if(!organizador.isEmpty()) crearLayoutConImagen(organizador, R.drawable.ic_organizador, layoutPrincipal);
                if(!descripcion.isEmpty()) crearLayoutConImagen(descripcion, R.drawable.ic_descripcion, layoutPrincipal);
                if(!tipo.isEmpty()) crearLayoutConImagen(tipo, R.drawable.ic_tipo, layoutPrincipal);
                if(!portada.isEmpty()) crearLayoutConImagen(portada, R.drawable.ic_portada, layoutPrincipal);
                if(!inicio.isEmpty()) crearLayoutConImagen(inicio, R.drawable.ic_inicio, layoutPrincipal);
                if(!fin.isEmpty()) crearLayoutConImagen(fin, R.drawable.ic_fin, layoutPrincipal);
                if(!direccion.isEmpty()) crearLayoutConImagen(direccion, R.drawable.ic_direccion, layoutPrincipal);
                if(!localidad.isEmpty()) crearLayoutConImagen(localidad, R.drawable.ic_localiadd, layoutPrincipal);
                if(!codPostal.isEmpty()) crearLayoutConImagen(codPostal, R.drawable.ic_codigo_postal, layoutPrincipal);
                if(!provincia.isEmpty()) crearLayoutConImagen(provincia, R.drawable.ic_provincia, layoutPrincipal);
                if(longitud.isEmpty()){
                    botonMapa.setVisibility(View.INVISIBLE);
                }
                if(latitud.isEmpty()){
                    botonMapa.setVisibility(View.INVISIBLE);
                }
                if(!telefono.isEmpty()) crearLayoutConImagen(telefono, R.drawable.ic_telefono, layoutPrincipal);
                if(!email.isEmpty()) crearLayoutConImagen(email, R.drawable.ic_email, layoutPrincipal);
                if(!web.isEmpty()) crearLayoutConImagen(web , R.drawable.ic_web, layoutPrincipal);
                if(!facebook.isEmpty()) crearLayoutConImagen(facebook, R.drawable.ic_facebook, layoutPrincipal);
                if(!twitter.isEmpty()) crearLayoutConImagen(twitter, R.drawable.ic_twitter, layoutPrincipal);
                if(!instagram.isEmpty()) crearLayoutConImagen(instagram, R.drawable.ic_instagram,layoutPrincipal);
            }while(cursor.moveToNext());
        }
    }
    public void crearLayoutConImagen(String nombre, int rutaImage, LinearLayout layout){
        //creamos el segundo Layout.
        LinearLayout milayout = new LinearLayout(new ContextThemeWrapper(this, R.style.AppTheme));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //le pasamos los parametros y la orientación.
        milayout.setLayoutParams(params);
        milayout.setOrientation(LinearLayout.HORIZONTAL);
        //add textView
        tv = new TextView(new ContextThemeWrapper(this, R.style.AppTheme));
        tv.setText(nombre);
        iv = new ImageView(new ContextThemeWrapper(this, R.style.AppTheme));
        iv.setImageResource(rutaImage);
        //Le añadimos los parametros a los view.
        iv.setLayoutParams(params);
        tv.setLayoutParams(params);
        //añadimos al layout que hemos creado tanto el texto como la imagen.
        milayout.addView(iv);
        milayout.addView(tv);
        //le añadimos el layout que hemos creado al principal.
        layout.addView(milayout);
    }
    /** Logs */
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

    /**fin Logs*/

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }


}
