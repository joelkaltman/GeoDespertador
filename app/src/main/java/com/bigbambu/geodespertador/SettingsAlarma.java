package com.bigbambu.geodespertador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class SettingsAlarma extends AppCompatActivity {
    Button btn_guardar;
    Button btn_volver;
    EditText txt_nombre;
    SeekBar skb_distancia;
    AlarmDB mi_base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_alarma);

        mi_base = AlarmDB.con;

        txt_nombre = (EditText)findViewById(R.id.txt_nombre);
        btn_guardar = (Button)findViewById(R.id.btn_guardar);
        btn_volver = (Button)findViewById(R.id.btn_volver);
        skb_distancia = (SeekBar)findViewById(R.id.skb_distancia);

        btn_guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String nombre = txt_nombre.getText().toString();
                int distancia = skb_distancia.getProgress();
                //mi_base.insertarAlarma(mi_alarma);

            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(i);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_alarma, menu);
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
}
