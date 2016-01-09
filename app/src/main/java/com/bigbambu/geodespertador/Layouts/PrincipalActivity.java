package com.bigbambu.geodespertador.Layouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bigbambu.geodespertador.Alarma.AlarmAdapter;
import com.bigbambu.geodespertador.Alarma.AlarmDB;
import com.bigbambu.geodespertador.Alarma.Alarma;
import com.bigbambu.geodespertador.Ubicacion.GPSTracker;
import com.bigbambu.geodespertador.R;

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

        iniciarServicioVerificacion();

        contexto = this;
        mi_base = new AlarmDB(this);

        List<Alarma> alarmas_lista = mi_base.obtenerTodasAlarmas();
        cargarListView(alarmas_lista);


        nueva_alarma=(Button)findViewById(R.id.btn_nueva);
        nueva_alarma.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), SettingsAlarma.class);
                i.setAction(SettingsAlarma.NUEVO);
                startActivity(i);
            }
        });

    }

    private void iniciarServicioVerificacion(){
        startService(new Intent(this, GPSTracker.class));
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
