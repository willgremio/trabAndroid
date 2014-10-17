package br.unisc.gabrielcalderaro.vivaunisc;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import br.unisc.gabrielcalderaro.vivaunisc.R;

public class ActivityInformacaoAdcional extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_informacao_adcional);

        Intent int2 = getIntent();
        final String id = int2.getStringExtra("id_oficina");

        String url = "http://vivaunisc.jossandro.com/oficina/" + id;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            String titulo = response.getString("titulo");
                            String curso = response.getString("curso");
                            String data_hora = response.getString("data_hora");
                            String duracao = response.getString("duracao");
                            String descricao = response.getString("descricao");
                            String responsavel = response.getString("responsável");
                            String local = response.getString("local");
                            String vagas = response.getString("vagas");
                            String inscritos = response.getString("inscritos");
                            String imagem = response.getString("imagem");

                            TextView tit = (TextView) findViewById(R.id.titulo);
                            TextView curs = (TextView) findViewById(R.id.resposta1);
                            TextView data = (TextView) findViewById(R.id.resposta2);
                            TextView dur = (TextView) findViewById(R.id.resposta3);
                            TextView desc = (TextView) findViewById(R.id.resposta4);
                            TextView respons = (TextView) findViewById(R.id.resposta5);
                            TextView loc = (TextView) findViewById(R.id.resposta6);
                            TextView vag = (TextView) findViewById(R.id.resposta7);
                            TextView insc = (TextView) findViewById(R.id.resposta8);

                            tit.setText(titulo);
                            data.setText(data_hora);
                            curs.setText(curso);
                            dur.setText(duracao);
                            desc.setText(descricao);
                            respons.setText(responsavel);
                            loc.setText(local);
                            vag.setText(vagas);
                            insc.setText(inscritos);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_informacao_adcional, menu);
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