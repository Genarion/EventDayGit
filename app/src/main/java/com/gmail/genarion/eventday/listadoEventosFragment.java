package com.gmail.genarion.eventday;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link listadoEventosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link listadoEventosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class listadoEventosFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String id_Acon;
    private ListView listView;
    private ArrayList<EventoItem> items;
    private EventoAdapter eventosAdapter;
    private OnFragmentInteractionListener mListener;

    public listadoEventosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment listadoEventosFragment.
     */
    // TODO: Rename and change types and number of parameter
    public static listadoEventosFragment newInstance(String param1, String param2) {

        listadoEventosFragment fragment = new listadoEventosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        //String provincias[] = {"Cordoba","Baena","Suiza","Nueza zelanda","New York","Miami","Caceres"};
        //setListAdapter(new ArrayAdapter<String>(getActivity(), layout,provincias));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listado_eventos, container, false);
        listView = (ListView) rootView.findViewById(android.R.id.list);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getFragmentManager().findFragmentById(R.id.mostrar_fragment) != null){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()+ " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //int num = getArguments().getInt(ARG_SECTION_NUMBER);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        SharedPreferences prefs = getActivity().getSharedPreferences("Ajustes", Context.MODE_PRIVATE);
        //recogemos
        id_Acon = prefs.getString("id", "Error al coger preferencias");
        AcontecimientosSQLiteHelper usdbh =
                new AcontecimientosSQLiteHelper(getActivity(), "test1.db", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();


        String[] argsID = new String[]{id_Acon};
        Cursor cursor = db.rawQuery(" SELECT id,nombre FROM evento WHERE id_acontecimiento=? ", argsID);

        //Nos aseguramos de que existe al menos un registro
        if (cursor.moveToFirst()) {
            items = new ArrayList<EventoItem>();
            do {
                //recogemos los datos
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String nombreAcontecimiento = cursor.getString(cursor.getColumnIndex("nombre"));
                items.add(new EventoItem(id, nombreAcontecimiento));
            } while (cursor.moveToNext());
            eventosAdapter = new EventoAdapter(getActivity(),layout, items);
            listView.setAdapter(eventosAdapter);
        }


    }

    @Override
    public void onListItemClick(ListView l,View v , int position,long id){
        if(mListener != null){
            mListener.onFragmentInteraction(position,items.get(position).getId());
        }
        getListView().setItemChecked(position,true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position,String id);
    }
}
