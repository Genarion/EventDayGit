package com.gmail.genarion.eventday;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class EventosActivity extends AppCompatActivity implements listadoEventosFragment.OnFragmentInteractionListener{

    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(findViewById(R.id.unique_fragment) != null){
            if (savedInstanceState == null) {
                listadoEventosFragment listadoFrag = new listadoEventosFragment();

                getSupportFragmentManager().beginTransaction().add(R.id.unique_fragment, listadoFrag).commit();
            }
        }

    }

    public void onFragmentInteraction(int position,String id){
        mostrarEventoFragment mostrarEventFrag = (mostrarEventoFragment) getSupportFragmentManager().findFragmentById(R.id.mostrar_fragment);

        if(mostrarEventFrag != null){
            mostrarEventFrag.updateView(id);
        }else{
            mostrarEventoFragment newmostrarEventFrag = new mostrarEventoFragment();
            Bundle args = new Bundle();
            args.putString("id", id);

            newmostrarEventFrag.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.unique_fragment, newmostrarEventFrag);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }
}
