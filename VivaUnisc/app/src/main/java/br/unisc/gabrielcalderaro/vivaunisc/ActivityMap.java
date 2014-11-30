package br.unisc.gabrielcalderaro.vivaunisc;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import DB.OficinaContract;
import DB.OficinaDBHelper;


public class ActivityMap extends ActionBarActivity {
    OficinaDBHelper odb = new OficinaDBHelper(this);
    GoogleMap map;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //esvaziarTableEstudantes();

        setContentView(R.layout.activity_activity_map);
        setEstudantesBanco();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        map = mapFragment.getMap();

        buscaEstudantesBanco();

    }

    public void esvaziarTableEstudantes() {
        try {
            SQLiteDatabase db = this.odb.getReadableDatabase();
            db.delete(OficinaContract.Estudante.TABLE_NAME, null, null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //db.execSQL("delete from " + OficinaContract.Estudante.TABLE_NAME);
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
                                estudante.put(OficinaContract.Estudante._ID, jsonKeyValue.getString("id_estudante"));
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

    public void buscaEstudantesBanco() {
        final SQLiteDatabase db = this.odb.getReadableDatabase();

        String[] projection = {
                "count(" + OficinaContract.Estudante._ID + ")",
                OficinaContract.Estudante.CIDADE
        };

        String group = OficinaContract.Estudante.CIDADE;

        Cursor c = db.query(
                OficinaContract.Estudante.TABLE_NAME,      // The table to query
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
                getLatLongFromAddress(c.getString(1), Integer.parseInt(c.getString(0)));
            } while (c.moveToNext());
        }

        Spinner spinner2 = (Spinner) findViewById(R.id.spinnercidades);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

    }

    public void getLatLongFromAddress(final String cidade, final int num) {
        if (!cidade.equals("") || cidade != "") {
            String query = cidade.replace(" ", "%20");
            String url = "http://maps.google.com/maps/api/geocode/json?address=" +
                    query + "&sensor=false";
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray results = response.getJSONArray("results");
                                JSONObject result = results.getJSONObject(0);
                                JSONObject geo = result.getJSONObject("geometry");
                                JSONObject local = geo.getJSONObject("location");

                                String lat = local.getString("lat");
                                String lng = local.getString("lng");

                                marcaPontoCidades(Double.parseDouble(lat), Double.parseDouble(lng), cidade, num);

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

    public void marcaPontoCidades(double latitude, double longitude, String cidade, int num) {
        LatLng ll = new LatLng(latitude, longitude);
        adicionarMarcador(ll, cidade, num + " inscritos");
    }

    private void adicionarMarcador(LatLng latlong, String titulo, String snip){
        MarkerOptions options = new MarkerOptions();
        options.position(latlong).title(titulo).snippet(snip);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
        Marker marker = map.addMarker(options);
    }

    public void action_none(View v){
        map.setMapType(GoogleMap.MAP_TYPE_NONE);
    }

    public void action_normal(View v){
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    public void action_satelite(View v){
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
    public void action_hybrid(View v){
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    public void action_terrain(View v){
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_map, menu);
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
