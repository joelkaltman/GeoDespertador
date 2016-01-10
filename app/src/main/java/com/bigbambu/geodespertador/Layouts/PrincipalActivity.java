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
import com.bigbambu.geodespertador.Constants.Constants;
import com.bigbambu.geodespertador.R;
import com.bigbambu.geodespertador.Ubicacion.GPSTracker;


public class PrincipalActivity extends AppCompatActivity {

    public AlarmDB mi_base;
    public static Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        //iniciamos el servicio en segundo plano
        startService(new Intent(this, GPSTracker.class));

        PrincipalActivity.contexto = this;

        //Conecta a la db
        mi_base = new AlarmDB(this);

        //carga la lista de alarmas en el listView
        cargarListView();


        //region CONFIGURAR BOTON NUEVA ALARMA
        Button nueva_alarma;
        nueva_alarma=(Button)findViewById(R.id.btn_nueva);
        nueva_alarma.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), SettingsAlarma.class);
                i.setAction(Constants.NUEVO);
                startActivity(i);
            }
        });
        //endregion

    }

    //region METODOS OVERRIDE QUE NO SE QUE HACEN
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
    //endregion

    /**
     * Carga la vista en el layout y le asigna el adaptador
     */
    void cargarListView(){
        //Crea el adaptador de alarmas
        AlarmAdapter adapter = new AlarmAdapter(mi_base.obtenerTodasAlarmas(), this, this);

        //enlaza el list view del layout a la variable
        ListView mi_lista_alarmas = (ListView)findViewById(R.id.listView);

        //Le setea el adaptador a la lista
        mi_lista_alarmas.setAdapter(adapter);
    }

}
