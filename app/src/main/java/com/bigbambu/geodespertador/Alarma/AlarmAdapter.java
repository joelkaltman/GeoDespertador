package com.bigbambu.geodespertador.Alarma;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bigbambu.geodespertador.Layouts.PrincipalActivity;
import com.bigbambu.geodespertador.R;
import com.bigbambu.geodespertador.Layouts.SettingsAlarma;

import java.util.ArrayList;

/**
 * Created by Sebas on 06/01/2016.
 */
public class AlarmAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Alarma> list = new ArrayList<Alarma>();
    private Context context;
    PrincipalActivity actividad;
    TextView listItemText;


    public AlarmAdapter(ArrayList<Alarma> list, Context context, PrincipalActivity actividad) {
        this.list = list;
        this.context = context;
        this.actividad = actividad;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.itemlistview, null);
        }

        //Handle TextView and display string from your list
        listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getNombre());

        listItemText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                modificar(list.get(position));
                return true;
            }
        });

        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar(list.get(position));
            }
        });

        return view;
    }

    public void modificar(Alarma alarma){
        Intent i = new Intent(PrincipalActivity.contexto, SettingsAlarma.class);
        i.putExtra("nombre", alarma.getNombre());
        i.putExtra("lat", alarma.getLatLong().latitude);
        i.putExtra("long", alarma.getLatLong().longitude);
        i.putExtra("distancia", alarma.getDistancia());
        i.setAction(SettingsAlarma.MODIFICAR);
        PrincipalActivity.contexto.startActivity(i);
    }
}
