package br.unisc.gabrielcalderaro.vivaunisc;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import DB.OficinaContract;
import DB.OficinaDBHelper;


public class ActivityGrafico extends ActionBarActivity {

    OficinaDBHelper odb = new OficinaDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_grafico);
        TextView oficina = (TextView) findViewById(R.id.oficina);

        Intent int2 = getIntent();
        String id_oficina = (int2.getStringExtra("id_oficina"));
        String nome_oficina = (int2.getStringExtra("nome_oficina"));

        oficina.setText(nome_oficina);

        buscarTodosEstudantes(id_oficina);
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

    public void buscarTodosEstudantes(String id_oficina) {
        final SQLiteDatabase db = this.odb.getReadableDatabase();

        String selection = OficinaContract.Estudante.ID_OFICINA + " = ?";
        String[] projection = {
                "count(DISTINCT " + OficinaContract.Estudante.ID_ESTUDANTE + ")",
                OficinaContract.Estudante.CIDADE
        };
        String[] selectionArgs = {id_oficina};
        String group = OficinaContract.Estudante.CIDADE;

        Cursor c = db.query(
                OficinaContract.Estudante.TABLE_NAME,      // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                group,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        String summary = "<html>\n" +
                "<head>\n" +
                "    <!--Load the AJAX API-->\n" +
                "    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "    \n" +
                "      // Load the Visualization API and the piechart package.\n" +
                "      google.load('visualization', '1.0', {'packages':['corechart']});\n" +
                "      \n" +
                "      // Set a callback to run when the Google Visualization API is loaded.\n" +
                "      google.setOnLoadCallback(drawChart);\n" +
                "\n" +
                "\n" +
                "      // Callback that creates and populates a data table, \n" +
                "      // instantiates the pie chart, passes in the data and\n" +
                "      // draws it.\n" +
                "      function drawChart() {\n" +
                "\n" +
                "      // Create the data table.\n" +
                "      var data = new google.visualization.DataTable();\n" +
                "      data.addColumn('string', 'CIDADE');\n" +
                "      data.addColumn('number', 'VALUE');\n" +
                "      data.addRows([\n";

        int i = 1;
                if (c.moveToFirst()) {
                    do {
                        String nomeCidade = c.getString(1);
                        if(c.getString(1).trim().equals("") || c.getString(1).trim() == "") {
                            nomeCidade = "Cidade não Informada" + i;
                        }

                        Log.d("RQ", "cidade:: " + nomeCidade);
                        Log.d("RQ", "num:: " + c.getString(0));
                        summary += "['"+nomeCidade+"', "+c.getString(0)+"], \n";
                        i++;

                    } while (c.moveToNext());
                }

        summary += "      ]);\n" +
                "\n" +
                "      // Set chart options\n" +
                "      var options = {\n" +
                "                     'width':800,\n" +
                "                     'height':600};\n" +
                "\n" +
                "      // Instantiate and draw our chart, passing in some options.\n" +
                "      var chart = new google.visualization.PieChart(document.getElementById('chart_div'));\n" +
                "      chart.draw(data, options);\n" +
                "    }\n" +
                "    </script>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<!--Div that will hold the pie chart-->\n" +
                "<div id=\"chart_div\" style=\"width:800; height:600\"></div>\n" +
                "</body>\n" +
                "</html>\n";


        WebView webview = (WebView) findViewById(R.id.web);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.requestFocusFromTouch();
        webview.loadDataWithBaseURL(null, summary, "text/html", "utf-8", null );
    }
}
