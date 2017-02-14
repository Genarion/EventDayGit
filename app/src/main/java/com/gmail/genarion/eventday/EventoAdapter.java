package com.gmail.genarion.eventday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mario on 06/02/2017.
 */

public class EventoAdapter  extends ArrayAdapter<EventoItem> {
    private final ArrayList<EventoItem> items;
    private final Context context;
    //Adaptador para eventos
    public EventoAdapter(Context context, int textViewResourceId,ArrayList<EventoItem> items) {
        super(context,textViewResourceId,items);
        this.items = items;
        this.context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.rowlistadoevento, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.textView_nombre_evento);
        textView.setText(items.get(position).getNombre());
        return view;
    }
}