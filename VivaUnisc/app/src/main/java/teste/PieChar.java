package teste;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import DB.OficinaContract;
import DB.OficinaDBHelper;
import br.unisc.gabrielcalderaro.vivaunisc.R;

public class PieChar extends Activity {
    // Atributos gráfico
    private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.YELLOW };

    // private static double[] VALUES = new double[] { 10, 11, 12, 13};

    private CategorySeries mSeries = new CategorySeries("");

    private DefaultRenderer mRenderer = new DefaultRenderer();

    private GraphicalView mChartView;

    //Atributos DB
    OficinaDBHelper odb = new OficinaDBHelper(this);

    final ArrayList<String> listNum = new ArrayList<String>();
    final ArrayList<String> listCidade = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteDatabase db = this.odb.getReadableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_char);

        setEstudantesBanco();

//Recebendo o Id de oficina
        Intent int2 = getIntent();
        final String id = int2.getStringExtra("id_oficina");

        String selection = OficinaContract.Estudante.ID_OFICINA + " = ?";

        String[] projection = {
                "count(DISTINCT " + OficinaContract.Estudante.ID_ESTUDANTE + ")",
                OficinaContract.Estudante.CIDADE
        };


        String[] selectionArgs = {id};
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

System.out.println("ENTREEEEEEEEEEEEI :");
//add numero de inscritos por cidade a uma lista
//add nomes de cidades da oficina a uma lista
        if (c.moveToFirst()) {
            do {
                System.out.println("AQUUUUUUUUUUUUUUUUUUUUI :" + c.getString(0));

                listNum.add(c.getString(0));
                listCidade.add(c.getString(1));

            } while (c.moveToNext());
        }

        System.out.println("SAAAAAAAAAAAAAAAAAAAAAI");

//Gráfico
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(40);
        mRenderer.setLegendTextSize(40);
        mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);

        int i = 0;
        double VALUES;

        for (String listaGrafico : listNum){

            VALUES = Double.parseDouble(listNum.get(i));

            mSeries.add(listaGrafico + " teste: " + VALUES, VALUES);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            mRenderer.addSeriesRenderer(renderer);
            i++;

        }

        if (mChartView != null) {
            mChartView.repaint();
        }



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
                                estudante.put(OficinaContract.Estudante.ID_ESTUDANTE, jsonKeyValue.getString("id_estudante"));
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

                listNum.add(c.getString(0));

            } while (c.moveToNext());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
            mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);

            mChartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();

                    if (seriesSelection == null) {
                        Toast.makeText(PieChar.this, "No chart element was clicked", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PieChar.this, "Chart element data point index " + (seriesSelection.getPointIndex() + 1) + " was clicked" + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            mChartView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                    if (seriesSelection == null) {
                        Toast.makeText(PieChar.this, "No chart element was long pressed", Toast.LENGTH_SHORT);
                        return false;
                    } else {
                        Toast.makeText(PieChar.this, "Chart element data point index " + seriesSelection.getPointIndex() + " was long pressed", Toast.LENGTH_SHORT);
                        return true;
                    }
                }
            });
            layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        }
        else {
            mChartView.repaint();
        }
    }
}

/*
//Preparando o SQL retornar cidades
        try{
            String[] projection = {
                    OficinaContract.Estudante.CIDADE,
            };

            String selection = OficinaContract.Estudante.ID_OFICINA + " = ?";
            String[] selectionArgs = { id };
            String group = OficinaContract.Estudante.CIDADE;

            Cursor cursor = db.query(
                    OficinaContract.Estudante.TABLE_NAME,      // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    group,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );


            final ArrayList<String> listCidade = new ArrayList<String>();

            if (cursor.moveToFirst()) {
                do {
                    listCidade.add(cursor.getString(0));

                    System.out.println("AQUUUUUUUUUUUUUUUUUUUUUUUUUUUUUI: ");

                    String cidade = cursor.getString(0);
                    System.out.println("Aqui a cidade: " + cidade);

                } while (cursor.moveToNext());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
*/