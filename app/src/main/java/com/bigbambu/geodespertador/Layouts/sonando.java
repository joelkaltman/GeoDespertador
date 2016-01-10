package com.bigbambu.geodespertador.Layouts;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigbambu.geodespertador.R;
import com.bigbambu.geodespertador.Ubicacion.Mapa;

public class sonando extends AppCompatActivity {
    TextView txt_nombre;
    TextView txt_distancia;
    Button btn_detener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonando);
        this.configurarBotones();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sonando, menu);
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


    private void configurarBotones() {

        //region ENLAZAR
        txt_nombre = (TextView) findViewById(R.id.txt_sonando_nombre);
        txt_nombre.setText(getIntent().getStringExtra("nombre"));
        btn_detener = (Button) findViewById(R.id.btn_sonando_detener);
        txt_distancia = (TextView)findViewById(R.id.txt_sonando_distancia);
        txt_distancia.setText("Distancia: " + String.valueOf(this.obtenerDistanciaADestino()) + "m");
        //endregion


        //region BOTON GUARDAR
        btn_detener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //endregion
    }

    public int obtenerDistanciaADestino(){
        Location ubic_usuario = new Location("");
        ubic_usuario.setLatitude(Mapa.ubicacionUsuario.latitude);
        ubic_usuario.setLongitude(Mapa.ubicacionUsuario.longitude);
        Location ubic_destino = new Location("");
        ubic_destino.setLatitude(getIntent().getDoubleExtra("latitud", 0));
        ubic_destino.setLongitude(getIntent().getDoubleExtra("longitud", 0));
        return (int)ubic_destino.distanceTo(ubic_usuario);
    }
}
