package br.unisc.gabrielcalderaro.vivaunisc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import android.widget.AdapterView.OnItemSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import DB.OficinaContract;
import DB.OficinaDBHelper;


public class ActivityOficinas extends ActionBarActivity {
    OficinaDBHelper odb = new OficinaDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_oficinas);
        buscarTodasOficinas();

    }

    public void buscarTodasOficinas() {
        final SQLiteDatabase db = this.odb.getReadableDatabase();

        String[] projection = {
                OficinaContract.Oficina.TITULO
        };

        String group = OficinaContract.Oficina.ID_OFICINA;

        Cursor c = db.query(
                OficinaContract.Oficina.TABLE_NAME,      // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                group,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        final ArrayList<String> list = new ArrayList<String>();

        if (c.moveToFirst()) {
            do {

                list.add(c.getString(0));

            } while (c.moveToNext());
        }

        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerOficinas);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener (new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View selectedItemView, int pos, long id) {
                buscaOficinaClicada(list.get(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}

        });
    }

    public void buscaOficinaClicada (String string) {
        SQLiteDatabase db = this.odb.getReadableDatabase();

        String[] projection = {
                OficinaContract.Oficina.DATA_HORA,
                OficinaContract.Oficina.ID_OFICINA,
                OficinaContract.Oficina.IMAGEM
        };

        String selection = OficinaContract.Oficina.TITULO + " = ?";
        String[] selectionArgs = { string };
        String group = OficinaContract.Oficina.ID_OFICINA;
        Cursor c = db.query(
                OficinaContract.Oficina.TABLE_NAME,      // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                group,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        c.moveToFirst();
        TextView tit = (TextView) findViewById(R.id.titulo);
        TextView data = (TextView) findViewById(R.id.data_hora);
        TextView id_oficina = (TextView) findViewById(R.id.id_oficina);

        tit.setText(string);
        data.setText(c.getString(0));
        id_oficina.setText(c.getString(1));
        getImage(c.getString(2));

    }

    public void getImage(String img){
        ImageLoader mImageLoader;
        NetworkImageView mNetworkImageView;
        final String IMAGE_URL = "http://vivaunisc.jossandro.com/" + img;

        mNetworkImageView = (NetworkImageView) findViewById(R.id.myNetworkImageView);
        mImageLoader = MySingleton.getInstance(this).getImageLoader();
        mNetworkImageView.setImageUrl(IMAGE_URL, mImageLoader);
    }

    public void telaCadastro(View v){
       TextView id_oficina = (TextView) findViewById(R.id.id_oficina);
       Intent it = new Intent(this,ActivityCadastro.class);
       it.putExtra("id_oficina", id_oficina.getText().toString());
       startActivity(it);
   }

    public void telaInformacoes(View v) {
        TextView id_oficina = (TextView) findViewById(R.id.id_oficina);
        Intent it = new Intent(this,ActivityInformacaoAdcional.class);
        it.putExtra("id_oficina", id_oficina.getText().toString());
        startActivity(it);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_oficinas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}