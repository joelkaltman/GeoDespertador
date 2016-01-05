package com.bigbambu.geodespertador;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class PrincipalActivity extends AppCompatActivity {
    ListView mi_lista_alarmas;
    Button nueva_alarma;
    public static AlarmDB mi_base;
    public static Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        mi_lista_alarmas = (ListView)findViewById(R.id.listView);

        contexto = this;
        mi_base = new AlarmDB(this);

        //CrearAlarmas();

        List<Alarma> alarmas_lista = mi_base.obtenerTodasAlarmas();
        cargarListView(alarmas_lista);


        nueva_alarma=(Button)findViewById(R.id.btn_nueva);
        nueva_alarma.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), SettingsAlarma.class);
                i.setAction("NUEVO");
                startActivity(i);
            }
        });

    }

    //MEtodo para crear alarmas, luego va a desaparecer
    private void CrearAlarmas() {
        Alarma alarm = new Alarma("Casa", "100", "200", "50");
        mi_base.insertarAlarma(alarm);
        alarm = new Alarma("Facu", "50", "46", "20");
        mi_base.insertarAlarma(alarm);
        alarm = new Alarma("Sinagoga", "234", "53", "25");
        mi_base.insertarAlarma(alarm);
        alarm = new Alarma("LCDTM", "646", "32", "30");
        mi_base.insertarAlarma(alarm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void cargarListView(List<Alarma> lista_mostrar){
        /*View hola;
        String[] items = new String[lista_mostrar.size()];
        for(int i=0; i<lista_mostrar.size(); i++){
            items[i] = lista_mostrar.get(i).nombre + " - radio: " + lista_mostrar.get(i).radio + "m";
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.itemlistview, items);
        mi_lista_alarmas.setAdapter(adaptador);
        */


        //generate list
        /*ArrayList<String> list = new ArrayList<String>();
        for(int i=0; i<lista_mostrar.size(); i++){
            list.add(lista_mostrar.get(i).nombre);
        }*/
        ArrayList<Alarma> list = new ArrayList<Alarma>();
        for(int i=0; i<lista_mostrar.size(); i++){
            list.add(lista_mostrar.get(i));
        }

        //instantiate custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, this, this);

        //handle listview and assign adapter
        mi_lista_alarmas.setAdapter(adapter);

    }







    public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<Alarma> list = new ArrayList<Alarma>();
        private Context context;
        PrincipalActivity actividad;
        TextView listItemText;


        public MyCustomAdapter(ArrayList<Alarma> list, Context context, PrincipalActivity actividad) {
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
            listItemText.setText(list.get(position).nombre + "-" + list.get(position).id);

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
            i.putExtra("id",String.valueOf(alarma.id));
            i.putExtra("nombre", alarma.nombre);
            i.putExtra("lat", alarma.latitud);
            i.putExtra("long", alarma.longitud);
            i.putExtra("distancia", alarma.distancia);
            i.setAction("MODIFICAR");
            startActivity(i);
        }

    }
}
