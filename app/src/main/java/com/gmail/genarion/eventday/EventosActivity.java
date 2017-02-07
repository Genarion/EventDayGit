package com.gmail.genarion.eventday;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventosActivity extends AppCompatActivity {

    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);


        if(findViewById(R.id.unique_fragment) != null){
            if (savedInstanceState == null) {
                listadoEventosFragment listadoFrag = new listadoEventosFragment();

                getSupportFragmentManager().beginTransaction().add(R.id.unique_fragment, listadoFrag).commit();
            }
        }

    }

    public void onFragmentInteraction(int position,int id){
        mostrarEventoFragment mostrarEventFrag = (mostrarEventoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMostrarEvento);

        if(mostrarEventFrag != null){
            mostrarEventFrag.updateView(id);
        }else{
            mostrarEventoFragment newmostrarEventFrag = new mostrarEventoFragment();
            Bundle args = new Bundle();
            args.putString("id", String.valueOf(id));

            newmostrarEventFrag.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.unique_fragment, newmostrarEventFrag);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }
}
