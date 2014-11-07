package com.example.m77611.exerprova;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.m77611.exerprova.DB.EnergiaContract;
import com.example.m77611.exerprova.DB.EnergiaHelper;


public class NovaLeitura extends Activity {
    EnergiaHelper odb = new EnergiaHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_leitura);
    }

    public void cadastraLeitura(View view) {
        final SQLiteDatabase db = this.odb.getWritableDatabase();
        ContentValues energia = new ContentValues();
        EditText campo_cliente = (EditText) findViewById(R.id.cliente);
        EditText rua_endereco = (EditText) findViewById(R.id.rua_endereco);
        EditText campo_numero = (EditText) findViewById(R.id.numero);
        EditText campo_kwh = (EditText) findViewById(R.id.kwh);
        int id_cliente = Integer.parseInt(campo_cliente.getText().toString());
        String end = rua_endereco.getText().toString();
        int num = Integer.parseInt(campo_numero.getText().toString());
        int kwh = Integer.parseInt(campo_kwh.getText().toString());
        try {
            energia.put(EnergiaContract.Energia.COD_CLI, id_cliente);
            energia.put(EnergiaContract.Energia.RUA, end);
            energia.put(EnergiaContract.Energia.NUMERO, num);
            energia.put(EnergiaContract.Energia.KWH, kwh);
            long newEnergiaId = db.insert(EnergiaContract.Energia.TABLE_NAME, null, energia);
            Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
            Intent it = new Intent(getApplicationContext(), MyActivity.class);
            startActivity(it);

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void voltar(View view) {
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nova_leitura, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
