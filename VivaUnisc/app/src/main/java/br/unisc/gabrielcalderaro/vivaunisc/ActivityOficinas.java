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
import android.widget.Toast;

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
        setEstudantesBanco();
        buscarTodasOficinas();
    }

    public void setEstudantesBanco() {
        String url = "http://vivaunisc.jossandro.com/estudantes";
        final SQLiteDatabase db = this.odb.getWritableDatabase();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray arrJSON = null;
                        try {
                            arrJSON = response.getJSONArray("estudantes");

                            for (int i = 0; i < arrJSON.length(); i++) {
                                JSONObject jsonKeyValue = arrJSON.getJSONObject(i);
                                ContentValues estudante = new ContentValues();
                                estudante.put(OficinaContract.Estudante.ID_ESTUDANTE, jsonKeyValue.getString("id_estudante"));
                                estudante.put(OficinaContract.Estudante.ID_OFICINA, jsonKeyValue.getString("id_oficina"));
                                estudante.put(OficinaContract.Estudante.NOME, jsonKeyValue.getString("nome"));
                                estudante.put(OficinaContract.Estudante.EMAIL, jsonKeyValue.getString("email"));
                                estudante.put(OficinaContract.Estudante.TELEFONE, jsonKeyValue.getString("telefone"));
                                estudante.put(OficinaContract.Estudante.CIDADE, jsonKeyValue.getString("cidade"));
                                long newEstudanteId = db.insert(OficinaContract.Estudante.TABLE_NAME, null, estudante);
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

    public void buscarTodasOficinas() {
        final SQLiteDatabase db = this.odb.getReadableDatabase();

        String[] projection = {
                OficinaContract.Oficina.ID_OFICINA,
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

                list.add(c.getString(0) + "-" + c.getString(1));

            } while (c.moveToNext());
        }

        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerOficinas);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener (new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View selectedItemView, int pos, long id) {
                String[] separated = list.get(pos).toString().split("-");
                buscaOficinaClicada(separated[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}

        });
    }

    public void buscaOficinaClicada (String id) {
        SQLiteDatabase db = this.odb.getReadableDatabase();

        String[] projection = {
                OficinaContract.Oficina.DATA_HORA,
                OficinaContract.Oficina.ID_OFICINA,
                OficinaContract.Oficina.IMAGEM,
                OficinaContract.Oficina.TITULO,
        };

        String selection = OficinaContract.Oficina.ID_OFICINA + " = ?";
        String[] selectionArgs = { id };
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


        String dataBr;
        ConverterDataHora cdh = new ConverterDataHora();
        dataBr = cdh.retornaData(c.getString(0));

        tit.setText(c.getString(3));
        data.setText(dataBr);
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


    public void telaGrafico(View v) {
        TextView id_oficina = (TextView) findViewById(R.id.id_oficina);
        TextView nome_oficina = (TextView) findViewById(R.id.titulo);
        Intent it = new Intent(this, ActivityGrafico.class);
        it.putExtra("id_oficina", id_oficina.getText().toString());
        it.putExtra("nome_oficina", nome_oficina.getText().toString());

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
        if (id == R.id.action_map){
            Intent it = new Intent(this, ActivityMap.class);
            startActivity(it);
        }

        if (id == R.id.action_grafico){
            Intent it = new Intent(this, ActivityGrafico.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }
}