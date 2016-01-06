package com.bigbambu.geodespertador;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

        CrearAlarmas();

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
        Alarma alarm = new Alarma("Casa", "-34.6229419", "-58.4491101", "100");
        mi_base.insertarAlarma(alarm);
        alarm = new Alarma("Facu", "20", "46", "50");
        mi_base.insertarAlarma(alarm);
        alarm = new Alarma("Sinagoga", "25", "53", "234");
        mi_base.insertarAlarma(alarm);
        alarm = new Alarma("LCDTM", "30", "32", "646");
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
        AlarmAdapter adapter = new AlarmAdapter(list, this, this);

        //handle listview and assign adapter
        mi_lista_alarmas.setAdapter(adapter);

    }
}
