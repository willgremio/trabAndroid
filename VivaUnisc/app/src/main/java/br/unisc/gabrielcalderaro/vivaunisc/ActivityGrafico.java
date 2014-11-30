package br.unisc.gabrielcalderaro.vivaunisc;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import DB.OficinaContract;
import DB.OficinaDBHelper;


public class ActivityGrafico extends ActionBarActivity {

    OficinaDBHelper odb = new OficinaDBHelper(this);
    final ArrayList<String> list = new ArrayList<String>();
    TextView id = (TextView) findViewById(R.id.id_oficina);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_grafico);

        Intent int2 = getIntent();
        id.setText(int2.getStringExtra("id_oficina"));

        WebView webview = (WebView) findViewById(R.id.webView1);
        String content = "<html>"
                + "  <head>"
                + "    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
                + "    <script type=\"text/javascript\">"

                + "      google.load('visualization', '1.0', {'packages':['corechart']});"
                + "      google.setOnLoadCallback(drawChart);"

                + "      function drawChart() {"
                + "        var data = new google.visualization.DataTable();"
                + "          data.addColumn('string', 'Cidade')"
                + "          data.addColumn('number', 'Estudante');"
                + "          data.addRows([["+list.get(0).toString()+","+list.get(1).toString+"]]);"
                + "          var options = {'title':'Estudantes por região',"
                + "                         'width':400,"
                + "                         'height':300};"
                + "        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));"
                + "        chart.draw(data, options);"
                + "        }"
                + "        };"
                + "        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));"
                + "        chart.draw(data, options);"
                + "      }"
                + "    </script>"
                + "  </head>"

                + "  <body>"
                + "    <div id=\"chart_div\"></div>"
                + "  </body>" + "</html>";

        setEstudantesBanco();
        buscarTodosEstudantes();

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.requestFocusFromTouch();
        webview.loadDataWithBaseURL( "file:///android_asset/", content, "text/html", "utf-8", null );
    }


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


    public void buscarTodosEstudantes() {
        final SQLiteDatabase db = this.odb.getReadableDatabase();
        String id_oficina = String.valueOf(id);

        String selection = OficinaContract.Estudante.ID_OFICINA + " = ?";
        String[] projection = {"count("+OficinaContract.Estudante._ID+")",OficinaContract.Estudante.CIDADE};
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
