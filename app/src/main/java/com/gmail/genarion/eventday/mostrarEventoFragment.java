package com.gmail.genarion.eventday;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class mostrarEventoFragment extends Fragment {


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

        Bundle args = getArguments();
        if(args != null){
            updateView(args.getInt("posicion"));
        }
    }

    public void updateView(int id){
        TextView article = (TextView) getActivity().findViewById(R.id.fragmentTextViewContent);
        article.setText(""+id);
    }
}
