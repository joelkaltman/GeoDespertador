package com.bigbambu.geodespertador.Layouts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigbambu.geodespertador.R;

public class sonando extends AppCompatActivity {
    TextView miTxt;
    Button btn_detener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonando);
        miTxt = (TextView)findViewById(R.id.txt_sonando_nombre);
        btn_detener = (Button)findViewById(R.id.btn_sonando_detener);
        miTxt.setText(getIntent().getStringExtra("nombre"));
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
        miTxt = (TextView) findViewById(R.id.txt_sonando_nombre);
        miTxt.setText(getIntent().getStringExtra("nombre"));
        btn_detener = (Button) findViewById(R.id.btn_sonando_detener);
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
}
