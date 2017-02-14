package com.gmail.genarion.eventday;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private float latitudAcontecimiento=0;
    private float longitudAcontecimiento=0;
    private String nombreAcontecimiento;
    private float latitudEvento=0;
    private float longitudEvento=0;
    private String nombreEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        SharedPreferences prefs =
                getSharedPreferences("Ajustes", Context.MODE_PRIVATE);
        //recogemos
        String id = prefs.getString("id", "");
        AcontecimientosSQLiteHelper usdbh =
                new AcontecimientosSQLiteHelper(this, "test1.db", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();


        String[] argsID = new String[] {id};
        Cursor cursorEven = db.rawQuery(" SELECT nombre,latitud,longitud FROM evento WHERE id_acontecimiento=? ", argsID);
        Cursor cursor = db.rawQuery(" SELECT nombre,latitud,longitud FROM acontecimiento WHERE id=? ", argsID);

        //Nos aseguramos de que existe al menos un registro
        if (cursor.moveToFirst()) {
            do{
//recogemos los datos
                nombreAcontecimiento = cursor.getString(cursor.getColumnIndex("nombre"));
                longitudAcontecimiento = Float.parseFloat(cursor.getString(cursor.getColumnIndex("longitud")));
                latitudAcontecimiento = Float.parseFloat(cursor.getString(cursor.getColumnIndex("latitud")));


            }while(cursor.moveToNext());
        }
        // Añado el marcador del acontecimiento
        LatLng AcontecimientoActual = new LatLng(latitudAcontecimiento, longitudAcontecimiento);
        mMap.addMarker(new MarkerOptions().position(AcontecimientoActual).title(nombreAcontecimiento)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_play_light));
        //Muevo la camara a la localizacion de acontecimiento
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AcontecimientoActual,16));
        //Si tiene eventos
        if (cursorEven.moveToFirst()) {
            LatLng EventoActual;
            do{
                //recogemos los datos
                nombreEvento = cursorEven.getString(cursorEven.getColumnIndex("nombre"));
                if(!cursorEven.getString(cursorEven.getColumnIndex("longitud")).isEmpty()&& !cursorEven.getString(cursorEven.getColumnIndex("latitud")).isEmpty()) {
                    longitudEvento = Float.parseFloat(cursorEven.getString(cursorEven.getColumnIndex("longitud")));
                    latitudEvento = Float.parseFloat(cursorEven.getString(cursorEven.getColumnIndex("latitud")));
                    EventoActual = new LatLng(latitudEvento, longitudEvento);//BitmapDescriptorFactory.fromResource(R.drawable.ic_tv_light)
                    mMap.addMarker(new MarkerOptions().position(EventoActual).title(nombreEvento)).setIcon(BitmapDescriptorFactory.defaultMarker());
                }

            }while(cursorEven.moveToNext());
        }
    }
}
