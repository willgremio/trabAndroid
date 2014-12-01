package br.unisc.gabrielcalderaro.vivaunisc;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import DB.OficinaContract;
import DB.OficinaDBHelper;


public class ActivityGrafico extends ActionBarActivity {

    OficinaDBHelper odb = new OficinaDBHelper(this);
    final ArrayList<String> list = new ArrayList<String>();
    //TextView id = (TextView) findViewById(R.id.id_oficina);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_grafico);

        WebView webview = (WebView) findViewById(R.id.web);
        setEstudantesBanco();

        Intent int2 = getIntent();
        String id_oficina = (int2.getStringExtra("id_oficina"));

        Toast.makeText(getApplicationContext(), id_oficina, Toast.LENGTH_LONG).show();
        /*
        buscarTodosEstudantes(id_oficina); */

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.requestFocusFromTouch();
        webview.loadUrl("file:///android_asset/chart.html");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_grafico, menu);
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

    public void setEstudantesBanco() {
        String url = "http://vivaunisc.jossandro.com/estudante";
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


    public void buscarTodosEstudantes(String id_oficina) {
        final SQLiteDatabase db = this.odb.getReadableDatabase();

        String selection = OficinaContract.Estudante.ID_OFICINA + " = ?";
        String[] projection = {
                "count(DISTINCT " + OficinaContract.Estudante.ID_ESTUDANTE + ")",
                OficinaContract.Estudante.CIDADE
        };
        String[] selectionArgs = {id_oficina};
        String group = OficinaContract.Estudante.ID_OFICINA;

        Cursor c = db.query(
                OficinaContract.Estudante.TABLE_NAME,      // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                group,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        if (c.moveToFirst()) {
            do {

                list.add(c.getString(0));

            } while (c.moveToNext());
        }
    }
}
