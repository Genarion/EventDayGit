package com.gmail.genarion.eventday;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.icu.lang.UCharacter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.gmail.genarion.eventday.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Thread.sleep;



public class AnadirAcontecimientoAsyncTask extends AsyncTask<String, String, String> {
    private static final String ACTIVITY = "ListadoAcontecimientosActivity";
    HttpURLConnection urlConnection;
    String id;
    Context vistaContext;
    ProgressBar barraProgreso;
    boolean existeAcontecimiento = true;
    Button boton;

    public AnadirAcontecimientoAsyncTask(String id, Context vista, ProgressBar barraProgreso,Button boton) {
        super();
        this.id = id;
        this.vistaContext = vista;
        this.barraProgreso = barraProgreso;
        this.boton = boton;
    }

    @Override
    protected void onPreExecute() {

        barraProgreso.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... args) {
        StringBuilder result = new StringBuilder();


        try {
            URL url = new URL("http://amferrer.hol.es/api/v1/acontecimiento/" + id);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }


            JSONObject jsonAcontecimientoConEventos = new JSONObject(result.toString());
            //Report

            if (jsonAcontecimientoConEventos.has("acontecimiento")) {
                //Abrimos la base de datos 'DBUsuarios' en modo escritura //añadir Environment.getExternalStorageDirectory()+"/DBAcontecimientos.db"
                //AcontecimientosSQLiteHelper usdbh = new AcontecimientosSQLiteHelper(this.vistaContext, Environment.getExternalStorageDirectory() + "/DBAcontecimientos.db", null, 1);
                AcontecimientosSQLiteHelper usdbh = new AcontecimientosSQLiteHelper(this.vistaContext,"test.db" , null, 1);

                SQLiteDatabase db = usdbh.getWritableDatabase();
                if (db != null) {
                    JSONObject jsonAcontecimiento = new JSONObject(jsonAcontecimientoConEventos.getString("acontecimiento"));
                    String nombreAcontecimiento = (jsonAcontecimiento.has("nombre") ? jsonAcontecimiento.getString("nombre") : "");
                    String organizadorAcontecimiento = (jsonAcontecimiento.has("organizador") ? jsonAcontecimiento.getString("organizador") : "");
                    String descripcion = (jsonAcontecimiento.has("descripcion") ? jsonAcontecimiento.getString("descripcion") : "");
                    String tipo = (jsonAcontecimiento.has("tipo") ? jsonAcontecimiento.getString("tipo") : "");
                    String portada = (jsonAcontecimiento.has("portada") ? jsonAcontecimiento.getString("portada") : "");
                    String inicio = (jsonAcontecimiento.has("inicio") ? jsonAcontecimiento.getString("inicio") : "");
                    String fin = (jsonAcontecimiento.has("fin") ? jsonAcontecimiento.getString("fin") : "");
                    String direccion = (jsonAcontecimiento.has("direccion") ? jsonAcontecimiento.getString("direccion") : "");
                    String localidad = (jsonAcontecimiento.has("localidad") ? jsonAcontecimiento.getString("localidad") : "");
                    String codPostal = (jsonAcontecimiento.has("codPostal") ? jsonAcontecimiento.getString("codPostal") : "");
                    String provincia = (jsonAcontecimiento.has("provincia") ? jsonAcontecimiento.getString("provincia") : "");
                    String longitud = (jsonAcontecimiento.has("longitud") ? jsonAcontecimiento.getString("longitud") : "");
                    String latitud = (jsonAcontecimiento.has("latitud") ? jsonAcontecimiento.getString("latitud") : "");
                    String telefono = (jsonAcontecimiento.has("telefono") ? jsonAcontecimiento.getString("telefono") : "");
                    String email = (jsonAcontecimiento.has("email") ? jsonAcontecimiento.getString("email") : "");
                    String web = (jsonAcontecimiento.has("web") ? jsonAcontecimiento.getString("web") : "");
                    String facebook = (jsonAcontecimiento.has("facebook") ? jsonAcontecimiento.getString("facebook") : "");
                    String twitter = (jsonAcontecimiento.has("twitter") ? jsonAcontecimiento.getString("twitter") : "");
                    String instagram = (jsonAcontecimiento.has("instagram") ? jsonAcontecimiento.getString("instagram") : "");

                    //borramos la base de datos.
                    db.execSQL("DELETE FROM `acontecimiento` WHERE id='"+id+"';");
                    //Insertamos los datos en la tabla Usuarios
                    db.execSQL("INSERT INTO `acontecimiento` (`id`, `nombre`, `organizador`, `descripcion`, " +
                            "`tipo`, `portada`, `inicio`, `fin`, `direccion`, `localidad`, `cod_postal`, `provincia`," +
                            " `longitud`, `latitud`, `telefono`, `email`, `web`, `facebook`, `twitter`, `instagram`) " +
                            "VALUES ('"+id+"', '"+nombreAcontecimiento+"','"+organizadorAcontecimiento+"', '"+descripcion+"', '"+tipo+"', " +
                            "'"+portada+"', '"+inicio+"', '"+fin+"', '"+direccion+"', '"+localidad+"', '"+codPostal+"', '"+provincia+"', " +
                            "'"+longitud+"', '"+latitud+"', '"+telefono+"', '"+email+"', '"+web+"', '"+facebook+"', '"+twitter+"'," +
                            "'"+instagram+"');");

                    MyLog.i("NuevoAcontecimiento-Acon", (jsonAcontecimiento.has("nombre")) ? jsonAcontecimiento.getString("nombre") : "No nombre");

                    if (jsonAcontecimientoConEventos.has("eventos")) {
                        //Recuperar array
                        JSONArray jsonEventoArray = new JSONArray(jsonAcontecimientoConEventos.getString("eventos"));
                        for (int i = 0; i < jsonEventoArray.length(); i++) {
                            JSONObject jsonEvento = jsonEventoArray.getJSONObject(i);
                            String idEvento = (jsonEvento.has("id") ? jsonEvento.getString("id") : "");
                            String nombreEvento = (jsonEvento.has("nombre") ? jsonEvento.getString("nombre") : "");
                            String descripcionEvento = (jsonEvento.has("descripcion") ? jsonEvento.getString("descripcion") : "");
                            String inicioEvento = (jsonEvento.has("inicio") ? jsonEvento.getString("inicio") : "");
                            String finEvento = (jsonEvento.has("fin") ? jsonEvento.getString("fin") : "");
                            String direccionEvento = (jsonEvento.has("direccion") ? jsonEvento.getString("direccion") : "");
                            String localidadEvento = (jsonEvento.has("localidad") ? jsonEvento.getString("localidad") : "");
                            String codPostalEvento = (jsonEvento.has("codPostal") ? jsonEvento.getString("codPostal") : "");
                            String provinciaEvento = (jsonAcontecimiento.has("provincia") ? jsonAcontecimiento.getString("provincia") : "");
                            String longitudEvento = (jsonEvento.has("longitud") ? jsonEvento.getString("latitud") : "");
                            String latitudEvento = (jsonAcontecimiento.has("latitud") ? jsonAcontecimiento.getString("latitud") : "");

                            db.delete("evento", "id" + "=" + idEvento, null);
                            db.execSQL("INSERT INTO `evento` (`id`, `id_acontecimiento`, `nombre`, `descripcion`, `inicio`, `fin`," +
                                    " `direccion`, `localidad`, `cod_postal`, `provincia`, `longitud`, `latitud`) VALUES" +
                                    "('" + idEvento + "', '" + id + "', '" + nombreEvento + "', '" + descripcionEvento + "', '" + inicioEvento + "', '" +
                                    finEvento + "', '" + direccionEvento + "', '" + localidadEvento + "', '" + codPostalEvento + "', '" + provinciaEvento + "', '" +
                                    longitudEvento + "', '" + latitudEvento + "')");
                            MyLog.i("NuevoAcontecimiento-Event", jsonEvento.getString("nombre"));

                        }
                    } else {
                        MyLog.i("NuevoAcontecimiento-Event", "acontecimiento sin eventos");
                    }
                }

            //cerramos la base de datos
            db.close();
        }else {
                MyLog.i("NuevoAcontecimiento-Acon", "error");
                existeAcontecimiento = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        if(existeAcontecimiento){
            SharedPreferences prefs = vistaContext.getSharedPreferences("Ajustes",Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("id_acontecimiento", id);
            editor.commit();
            barraProgreso.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(vistaContext, mostrar_acontecimiento.class);
            vistaContext.startActivity(intent);
            ((Activity)vistaContext).finish();
        }else{
            Snackbar.make(boton, "id no encontradado.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            barraProgreso.setVisibility(View.INVISIBLE);
        }

    }
}
