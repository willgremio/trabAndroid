package br.unisc.gabrielcalderaro.vivaunisc;

import android.app.Activity;
import android.content.Intent;
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


public class ActivityOficinas extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_oficinas);
        setListOficinas();

    }

    public void setListOficinas() {
        String url = "http://vivaunisc.jossandro.com/oficinas";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){

                        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerOficinas);
                        JSONArray arrJSON = null;
                        String titulo;
                        final ArrayList<String> list = new ArrayList<String>();

                        try {
                            arrJSON = response.getJSONArray("oficinas");

                            for(int i =0; i < arrJSON.length(); i++) {
                                JSONObject jsonKeyValue = arrJSON.getJSONObject(i);
                                titulo = jsonKeyValue.getString("id_oficina") + "-" + jsonKeyValue.getString("titulo");
                                list.add(titulo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter);
                        spinner2.setOnItemSelectedListener (new CustomOnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> arg0, View selectedItemView, int pos, long id) {
                                String string = list.get(pos).toString();
                                String id_oficina = string.split("\\-")[0];
                                buscarOficina(id_oficina);
                            }

                        });
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(jsObjRequest);
    }

    public void buscarOficina(final String id) {
        String url = "http://vivaunisc.jossandro.com/oficina/" + id;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            String titulo = response.getString("titulo");
                            String data_hora = response.getString("data_hora");
                            String imagem = response.getString("imagem");

                            TextView tit = (TextView) findViewById(R.id.titulo);
                            TextView data = (TextView) findViewById(R.id.data_hora);
                            TextView id_oficina = (TextView) findViewById(R.id.id_oficina);

                            id_oficina.setText(id);
                            tit.setText(titulo);
                            data.setText(data_hora);
                            getImage(imagem);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(jsObjRequest);
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