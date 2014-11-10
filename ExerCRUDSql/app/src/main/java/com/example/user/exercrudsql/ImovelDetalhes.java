package com.example.user.exercrudsql;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.exercrudsql.DB.ImovelContract;
import com.example.user.exercrudsql.DB.ImovelHelper;


public class ImovelDetalhes extends Activity {
    ImovelHelper odb = new ImovelHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imovel_detalhes);

        Intent intent = getIntent();
        String id = intent.getStringExtra("imovel_id");
        final SQLiteDatabase db = this.odb.getReadableDatabase();

        String[] projection = {
                ImovelContract.Imovel.TITULO,
                ImovelContract.Imovel.LOGRADOURO,
                ImovelContract.Imovel.NUMERO,
                ImovelContract.Imovel.BAIRRO,
                ImovelContract.Imovel.CIDADE,
                ImovelContract.Imovel.VALOR,
                ImovelContract.Imovel._ID,
                ImovelContract.Imovel.TIPO_NEGOCIO
        };

        String selection = ImovelContract.Imovel._ID + " = ?";
        String[] selectionArgs = { id };

        Cursor c = db.query(
                ImovelContract.Imovel.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        c.moveToFirst();
        String titulo = c.getString(0);
        String logradouro = c.getString(1);
        String numero = c.getString(2);
        String bairro = c.getString(3);
        String cidade = c.getString(4);
        String valor = c.getString(5);
        String id_imovel = c.getString(6);
        String tipo = c.getString(7);

        EditText tit = (EditText) findViewById(R.id.editTituloDetalhes);
        EditText logra = (EditText) findViewById(R.id.editLogradouroDetalhes);
        EditText num = (EditText) findViewById(R.id.numeroDetalhes);
        EditText bai = (EditText) findViewById(R.id.editBairroDetalhes);
        EditText cid = (EditText) findViewById(R.id.editCidadeDetalhes);
        EditText val = (EditText) findViewById(R.id.editValorDetalhes);
        TextView id_imo = (TextView) findViewById(R.id.id_linha);
        //Spinner tipo_neg = (Spinner) findViewById(R.id.tipo_negDetalhes);


        tit.setText(titulo);
        logra.setText(logradouro);
        num.setText(numero);
        bai.setText(bairro);
        cid.setText(cidade);
        val.setText(valor);
        id_imo.setText(id_imovel);
    }

    public void voltar(View view) {
        finish();
    }

    public void deletarImovel (View view) {
        TextView id_imo = (TextView) findViewById(R.id.id_linha);
        int ImovelId = Integer.parseInt(id_imo.getText().toString());
        SQLiteDatabase db = this.odb.getWritableDatabase();

        db.delete(ImovelContract.Imovel.TABLE_NAME, ImovelContract.Imovel._ID + "=" + ImovelId, null);
        db.close(); // Closing database connection
        Toast.makeText(getApplicationContext(), "Imóvel deletado com sucesso!", Toast.LENGTH_LONG).show();
        Intent it = new Intent(getApplicationContext(), MyActivity.class);
        startActivity(it);
    }

    public void salvar (View view) {
        EditText tit = (EditText) findViewById(R.id.editTituloDetalhes);
        EditText logra = (EditText) findViewById(R.id.editLogradouroDetalhes);
        EditText num = (EditText) findViewById(R.id.numeroDetalhes);
        EditText bai = (EditText) findViewById(R.id.editBairroDetalhes);
        EditText cid = (EditText) findViewById(R.id.editCidadeDetalhes);
        EditText val = (EditText) findViewById(R.id.editValorDetalhes);
        TextView id_imo = (TextView) findViewById(R.id.id_linha);

        String titulo = tit.getText().toString();
        String logradouro = logra.getText().toString();
        int numero = Integer.parseInt(num.getText().toString());
        String bairro = bai.getText().toString();
        String cidade = cid.getText().toString();
        String valor = val.getText().toString();
        int ImovelId = Integer.parseInt(id_imo.getText().toString());

        final SQLiteDatabase db = this.odb.getWritableDatabase();
        ContentValues imovel = new ContentValues();
        try {
            imovel.put(ImovelContract.Imovel.TITULO, titulo);
            imovel.put(ImovelContract.Imovel.LOGRADOURO, logradouro);
            imovel.put(ImovelContract.Imovel.NUMERO, numero);
            imovel.put(ImovelContract.Imovel.BAIRRO, bairro);
            imovel.put(ImovelContract.Imovel.CIDADE, cidade);
            imovel.put(ImovelContract.Imovel.VALOR, valor);

            db.update(ImovelContract.Imovel.TABLE_NAME, imovel, ImovelContract.Imovel._ID + "=" + ImovelId, null);
            db.close(); // Closing database connection
            Toast.makeText(getApplicationContext(), "Imóvel editado com sucesso!", Toast.LENGTH_LONG).show();
            Intent it = new Intent(getApplicationContext(), MyActivity.class);
            startActivity(it);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.imovel_detalhes, menu);
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
