package com.gmail.genarion.eventday;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class mostrarEventoFragment extends Fragment {

    private static final String ACTIVITY = "StartCreate";
    private TextView tv;
    private ImageView iv;

    public mostrarEventoFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_evento, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        Bundle args=getArguments();
        if(args!=null){
            updateView(args.getString("id"));
        }
    }

    public void updateView(String id){
        //leer de la base de datos.
        AcontecimientosSQLiteHelper usdbh =
                new AcontecimientosSQLiteHelper(getActivity(), "test1.db", null, 1);
        //instancia la db.
        SQLiteDatabase db = usdbh.getReadableDatabase();

        String[] argsID = new String[] {id};
        Cursor cursor = db.rawQuery(" SELECT * FROM evento WHERE id=? ", argsID);

        //Nos aseguramos de que existe al menos un registro
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            LinearLayout layoutPrincipal = (LinearLayout) getActivity().findViewById(R.id.layout_mostrar_eventos);
            layoutPrincipal.setOrientation(LinearLayout.VERTICAL);
            do{
                String nombreEvento = cursor.getString(cursor.getColumnIndex("nombre"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                String inicio = cursor.getString(cursor.getColumnIndex("inicio"));
                // Formato para parsear
                SimpleDateFormat dateParse = new SimpleDateFormat("yyyymmddhhmm");
                // el que formatea
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = dateParse.parse(inicio);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                inicio=dateFormat.format(date);
                String fin = cursor.getString(cursor.getColumnIndex("fin"));
                try {
                    date = dateParse.parse(fin);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                fin=dateFormat.format(date);
                String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
                String localidad = cursor.getString(cursor.getColumnIndex("localidad"));
                String codPostal = cursor.getString(cursor.getColumnIndex("cod_postal"));
                String provincia = cursor.getString(cursor.getColumnIndex("provincia"));
                String longitud = cursor.getString(cursor.getColumnIndex("longitud"));;
                String latitud = cursor.getString(cursor.getColumnIndex("latitud"));
                //borramos la lista para que la vuelva a pintar
                layoutPrincipal.removeAllViewsInLayout();
                if (!nombreEvento.isEmpty()) crearElementoVista(nombreEvento, R.drawable.ic_nombre_acontecimiento, layoutPrincipal);
                if(!descripcion.isEmpty()) crearElementoVista(descripcion, R.drawable.ic_descripcion, layoutPrincipal);
                if(!inicio.isEmpty()) crearElementoVista(inicio, R.drawable.ic_inicio, layoutPrincipal);
                if(!fin.isEmpty()) crearElementoVista(fin, R.drawable.ic_fin, layoutPrincipal);
                if(!direccion.isEmpty()) crearElementoVista(direccion, R.drawable.ic_direccion, layoutPrincipal);
                if(!localidad.isEmpty()) crearElementoVista(localidad, R.drawable.ic_localiadd, layoutPrincipal);
                if(!codPostal.isEmpty()) crearElementoVista(codPostal, R.drawable.ic_codigo_postal, layoutPrincipal);
                if(!provincia.isEmpty()) crearElementoVista(provincia, R.drawable.ic_provincia, layoutPrincipal);
                if(!longitud.isEmpty()) crearElementoVista(longitud, R.drawable.ic_longitud, layoutPrincipal);
                if(!latitud.isEmpty()) crearElementoVista(latitud, R.drawable.ic_latitud, layoutPrincipal);
            }while(cursor.moveToNext());
        }
    }
    //Metodo para crear los elementos en la vista con layout horizontal
    public void crearElementoVista(String nombre, int rutaImage, LinearLayout layout){
        //creamos el segundo Layout.
        LinearLayout milayout = new LinearLayout(new ContextThemeWrapper(getActivity(), R.style.AppTheme));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //le pasamos los parametros y la orientación.
        milayout.setLayoutParams(params);
        milayout.setOrientation(LinearLayout.HORIZONTAL);
        //add textView
        tv = new TextView(new ContextThemeWrapper(getActivity(), R.style.AppTheme));
        tv.setText(nombre);
        iv = new ImageView(new ContextThemeWrapper(getActivity(), R.style.AppTheme));
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
}
