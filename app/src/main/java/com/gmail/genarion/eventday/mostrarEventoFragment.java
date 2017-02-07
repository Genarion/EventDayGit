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
                new AcontecimientosSQLiteHelper(getActivity(), "Eventgo.db", null, 1);
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
                String fin = cursor.getString(cursor.getColumnIndex("fin"));
                String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
                String localidad = cursor.getString(cursor.getColumnIndex("localidad"));
                String codPostal = cursor.getString(cursor.getColumnIndex("cod_postal"));
                String provincia = cursor.getString(cursor.getColumnIndex("provincia"));
                String longitud = cursor.getString(cursor.getColumnIndex("longitud"));;
                String latitud = cursor.getString(cursor.getColumnIndex("latitud"));
                //borramos la lista para que la vuelva a pintar
                layoutPrincipal.removeAllViewsInLayout();
                if (!nombreEvento.isEmpty()) crearElementoVista(nombreEvento, R.drawable.ic_info_black_24dp, layoutPrincipal);
                if(!descripcion.isEmpty()) crearElementoVista(descripcion, R.drawable.ic_info_black_24dp, layoutPrincipal);
                if(!inicio.isEmpty()) crearElementoVista(inicio, R.drawable.ic_info_black_24dp, layoutPrincipal);
                if(!fin.isEmpty()) crearElementoVista(fin, R.drawable.ic_info_black_24dp, layoutPrincipal);
                if(!direccion.isEmpty()) crearElementoVista(direccion, R.drawable.ic_info_black_24dp, layoutPrincipal);
                if(!localidad.isEmpty()) crearElementoVista(localidad, R.drawable.ic_info_black_24dp, layoutPrincipal);
                if(!codPostal.isEmpty()) crearElementoVista(codPostal, R.drawable.ic_info_black_24dp, layoutPrincipal);
                if(!provincia.isEmpty()) crearElementoVista(provincia, R.drawable.ic_info_black_24dp, layoutPrincipal);
                if(!longitud.isEmpty()) crearElementoVista(longitud, R.drawable.ic_info_black_24dp, layoutPrincipal);
                if(!latitud.isEmpty()) crearElementoVista(latitud, R.drawable.ic_info_black_24dp, layoutPrincipal);
            }while(cursor.moveToNext());
        }
    }

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
