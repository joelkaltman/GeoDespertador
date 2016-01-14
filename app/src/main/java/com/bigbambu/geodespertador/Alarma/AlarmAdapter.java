package com.bigbambu.geodespertador.Alarma;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigbambu.geodespertador.Constants.Constants;
import com.bigbambu.geodespertador.Layouts.PrincipalActivity;
import com.bigbambu.geodespertador.Layouts.SettingsAlarma;
import com.bigbambu.geodespertador.R;

import java.util.ArrayList;

/**
 * Created by Sebas on 06/01/2016.
 */
public class AlarmAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Alarma> list = new ArrayList<Alarma>();
    private Context context;
    PrincipalActivity actividad;
    TextView listItemText;
    TextView listItemTextRadio;
    ImageButton chk_button;
    LinearLayout layout_boton;

    public AlarmAdapter(ArrayList<Alarma> list, Context context, PrincipalActivity actividad) {
        this.list = list;
        this.context = context;
        this.actividad = actividad;
    }


    //region METODOS OVERRIDE NECESARIOS
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
    //endregion

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
        listItemTextRadio = (TextView)view.findViewById(R.id.list_item_string2);
        listItemTextRadio.setText("("+list.get(position).getDistancia()+"m)");
        layout_boton = (LinearLayout)view.findViewById(R.id.layout_texto);
        chk_button = (ImageButton) view.findViewById(R.id.chk_activada);
        final RelativeLayout linea = (RelativeLayout)view.findViewById(R.id.relative);
        if (list.get(position).getActiva() == Constants.ACTIVADA){
            chk_button.setBackgroundResource(R.drawable.reloj_prendido);
        }else {
            chk_button.setBackgroundResource(R.drawable.reloj_apagado);
        }

        //region CLICK EN EL LAYOUT
        layout_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar(list.get(position));
            }
        });
        //endregion

        chk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).switchear(PrincipalActivity.contexto);
                ImageButton botonactual = (ImageButton)v.findViewById(R.id.chk_activada);
                if (list.get(position).getActiva() == Constants.ACTIVADA) {
                    botonactual.setBackgroundResource(R.drawable.reloj_prendido);
                } else {
                    botonactual.setBackgroundResource(R.drawable.reloj_apagado);
                }
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
        i.setAction(Constants.MODIFICAR);
        PrincipalActivity.contexto.startActivity(i);
    }
}
