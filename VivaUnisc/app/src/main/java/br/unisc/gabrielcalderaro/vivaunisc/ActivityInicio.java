package br.unisc.gabrielcalderaro.vivaunisc;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import DB.OficinaContract;
import DB.OficinaDBHelper;

public class ActivityInicio extends ActionBarActivity {
    OficinaDBHelper odb = new OficinaDBHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOficinasBanco();
        setContentView(R.layout.activity_activity_inicio);
    }

    public void listarOficinas(View v) {
        Intent it = new Intent(this, ActivityOficinas.class);
        startActivity(it);
    }

    public void mostrarMapa(View v) {
        Intent it = new Intent(this, ActivityMap.class);
        startActivity(it);
    }

    public void setOficinasBanco() {
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

}