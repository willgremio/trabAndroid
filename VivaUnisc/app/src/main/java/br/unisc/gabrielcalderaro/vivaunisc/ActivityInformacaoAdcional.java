package br.unisc.gabrielcalderaro.vivaunisc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import DB.OficinaContract;
import DB.OficinaDBHelper;

public class ActivityInformacaoAdcional extends ActionBarActivity {
    OficinaDBHelper odb = new OficinaDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteDatabase db = this.odb.getReadableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_informacao_adcional);

        Intent int2 = getIntent();
        final String id = int2.getStringExtra("id_oficina");
        try {
                String[] projection = {
                        OficinaContract.Oficina.TITULO,
                        OficinaContract.Oficina.CURSO,
                        OficinaContract.Oficina.DATA_HORA,
                        OficinaContract.Oficina.DURACAO,
                        OficinaContract.Oficina.DESCRICAO,
                        OficinaContract.Oficina.RESPONSAVEL,
                        OficinaContract.Oficina.LOCAL,
                        OficinaContract.Oficina.VAGAS,
                        OficinaContract.Oficina.INSCRITOS,
                        OficinaContract.Oficina.IMAGEM
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
                String titulo = c.getString(0);
                String curso = c.getString(1);
                String data_hora = c.getString(2);
                String duracao = c.getString(3);
                String descricao = c.getString(4);
                String responsavel = c.getString(5);
                String local = c.getString(6);
                String vagas = c.getString(7);
                String inscritos = c.getString(8);
                String imagem = c.getString(9);

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

                } catch (Exception e) {
                    e.printStackTrace();
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