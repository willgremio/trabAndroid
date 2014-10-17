package br.unisc.gabrielcalderaro.vivaunisc;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DB.OficinaContract;
import DB.OficinaDBHelper;


public class ActivityOficinas extends ActionBarActivity {
    OficinaDBHelper odb = new OficinaDBHelper(this);
    final ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_oficinas);
        setListOficinas();

        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerOficinas);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View selectedItemView, int pos, long id) {
                String string = list.get(pos).toString();
                String id_oficina = string.split("\\-")[0];
                buscaOficina();
            }

        });

    }

    public void setListOficinas() {
        String url = "http://vivaunisc.jossandro.com/oficinas";
        final SQLiteDatabase db = this.odb.getWritableDatabase();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray arrJSON = null;
                        try {
                            arrJSON = response.getJSONArray("oficinas");

                            for (int i = 0; i < arrJSON.length(); i++) {
                                JSONObject jsonKeyValue = arrJSON.getJSONObject(i);
                                ContentValues oficina = new ContentValues();
                                oficina.put(OficinaContract.Oficina.ID_OFICINA, jsonKeyValue.getString("id_oficina"));
                                oficina.put(OficinaContract.Oficina.CURSO, jsonKeyValue.getString("curso"));
                                oficina.put(OficinaContract.Oficina.TITULO, jsonKeyValue.getString("titulo"));
                                oficina.put(OficinaContract.Oficina.IMAGEM, jsonKeyValue.getString("imagem"));
                                oficina.put(OficinaContract.Oficina.DATA_HORA, jsonKeyValue.getString("data_hora"));
                                oficina.put(OficinaContract.Oficina.DURACAO, jsonKeyValue.getString("duracao"));
                                oficina.put(OficinaContract.Oficina.DESCRICAO, jsonKeyValue.getString("descricao"));
                                oficina.put(OficinaContract.Oficina.RESPONSAVEL, jsonKeyValue.getString("responsável"));
                                oficina.put(OficinaContract.Oficina.LOCAL, jsonKeyValue.getString("local"));
                                oficina.put(OficinaContract.Oficina.VAGAS, jsonKeyValue.getString("vagas"));
                                oficina.put(OficinaContract.Oficina.INSCRITOS, jsonKeyValue.getString("inscritos"));
                                long newOficinaId = db.insert(OficinaContract.Oficina.TABLE_NAME, null, oficina);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(jsObjRequest);
    }

    public void buscaOficina() {

        SQLiteDatabase db = this.odb.getReadableDatabase();

        String[] projection = {
                OficinaContract.Oficina.ID_OFICINA,
                OficinaContract.Oficina.TITULO,
        };

        String sortOrder =
                OficinaContract.Oficina.ID_OFICINA + " DESC";

        Cursor c = db.query(
                OficinaContract.Oficina.TABLE_NAME,      // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        c.moveToFirst();
        String oficina = new String();
        for(String coluna : projection){

            list.add(oficina);
        }

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
        Intent it = new Intent(this,ActivityInformacaoAdcional.class);
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