package com.bigbambu.geodespertador.Layouts;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigbambu.geodespertador.Alarma.AlarmDB;
import com.bigbambu.geodespertador.Alarma.Alarma;
import com.bigbambu.geodespertador.Constants.Constants;
import com.bigbambu.geodespertador.R;
import com.bigbambu.geodespertador.Ubicacion.Mapa;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class sonando extends AppCompatActivity {
    TextView txt_nombre;
    TextView txt_distancia;
    Button btn_detener;
    ImageButton btn_borrar;
    ImageButton btn_modificar;
    MediaPlayer mp_alarma;

    Alarma alarma_actual;

    Mapa map;

    //base de datos
    AlarmDB base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonando);
        setTitle("Destino próximo...");

        base = new AlarmDB(this);

        //mapa
        FragmentManager fragmentManager = getFragmentManager();
        map.map = ((MapFragment) fragmentManager.findFragmentById(R.id.map_sonando)).getMap();
        map = new Mapa(false);
        map.actualizarMarcadorUsuarioActual();

        String nombre = getIntent().getStringExtra("nombre");
        LatLng latlng = new LatLng(getIntent().getDoubleExtra("latitud", 0), getIntent().getDoubleExtra("longitud", 0));
        int distancia = getIntent().getIntExtra("distancia", 100);
        alarma_actual = new Alarma(nombre, latlng, distancia, 's');
        alarma_actual.switchear(this);
        map.actualizarMarcador(alarma_actual.getLatLong(), Constants.DESTINO);
        map.actualizarMarcador(Mapa.ubicacionUsuario, Constants.USUARIO);
        map.actualizarCirculo(alarma_actual.getLatLong(), alarma_actual.getDistancia());
        this.configurarBotones();

        //media player
        mp_alarma = MediaPlayer.create(this, R.raw.asd);
        mp_alarma.start();

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(2000);
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
        txt_nombre.setText(alarma_actual.getNombre());
        btn_detener = (Button) findViewById(R.id.btn_sonando_detener);
        btn_borrar = (ImageButton)findViewById(R.id.btn_sonando_borrar);
        btn_modificar = (ImageButton)findViewById(R.id.btn_sonando_modificar);
        txt_distancia = (TextView)findViewById(R.id.txt_sonando_distancia);
        txt_distancia.setText("Distancia: " + String.valueOf(this.obtenerDistanciaADestino()) + "m");
        //endregion


        //region BOTONES
        btn_detener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp_alarma.stop();
                finish();
            }
        });

        btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp_alarma.stop();
                base.borrarAlarma(alarma_actual.getNombre());
                Volver();
                finish();
            }
        });

        btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp_alarma.stop();
                Intent i = new Intent(getApplicationContext(), SettingsAlarma.class);
                i.putExtra("nombre", alarma_actual.getNombre());
                i.putExtra("lat", alarma_actual.getLatLong().latitude);
                i.putExtra("long", alarma_actual.getLatLong().longitude);
                i.putExtra("distancia", alarma_actual.getDistancia());
                i.setAction(Constants.MODIFICAR);
                startActivity(i);
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
        ubic_destino.setLatitude(alarma_actual.getLatLong().latitude);
        ubic_destino.setLongitude(alarma_actual.getLatLong().longitude);
        return (int)ubic_destino.distanceTo(ubic_usuario);
    }

    private void Volver(){
        Intent i = new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(i);
    }
}
