package com.example.user.exercrudsql;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.exercrudsql.DB.ImovelContract;
import com.example.user.exercrudsql.DB.ImovelHelper;

import java.util.ArrayList;


public class NovoImovel extends Activity {
    ImovelHelper odb = new ImovelHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_imovel);
        setTiposNegocios();
        buscarCategorias();
    }

    public void setTiposNegocios() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Venda");
        list.add("Aluguel");
        Spinner spinner2 = (Spinner) findViewById(R.id.SpinnerTipo_neg);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }


    public void buscarCategorias() {
        SQLiteDatabase db = this.odb.getReadableDatabase();
        String[] projection = {
                ImovelContract.Categoria.NOME
        };

        Cursor c = db.query(
                ImovelContract.Categoria.TABLE_NAME,      // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        final ArrayList<String> list = new ArrayList<String>();

        if (c.moveToFirst()) {
            do {

                list.add(c.getString(0));

            } while (c.moveToNext());
        }

        Spinner spinner2 = (Spinner) findViewById(R.id.SpinnerCategoria);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

    }

    public void voltar(View view) {
        finish();
    }

    public void cadastraImovel(View view) {
        final SQLiteDatabase db = this.odb.getWritableDatabase();
        ContentValues imovel = new ContentValues();
        EditText editTitulo = (EditText) findViewById(R.id.editTitulo);
        EditText editLogradouro = (EditText) findViewById(R.id.editLogradouro);
        EditText numero = (EditText) findViewById(R.id.numero);
        EditText editBairro = (EditText) findViewById(R.id.editBairro);
        EditText editCidade = (EditText) findViewById(R.id.editCidade);
        EditText editValor = (EditText) findViewById(R.id.editValor);
        Spinner SpinnerTipo_neg = (Spinner) findViewById(R.id.SpinnerTipo_neg);
        Spinner SpinnerCategoria = (Spinner) findViewById(R.id.SpinnerCategoria);

        String titulo = editTitulo.getText().toString();
        String logradouro = editLogradouro.getText().toString();
        int num = Integer.parseInt(numero.getText().toString());
        String bairro = editBairro.getText().toString();
        String cidade = editCidade.getText().toString();
        String valor = editValor.getText().toString();
        String tipo = SpinnerTipo_neg.toString();
        int categoria = SpinnerCategoria.getSelectedItemPosition() + 1;
        try {
            imovel.put(ImovelContract.Imovel.TITULO, titulo);
            imovel.put(ImovelContract.Imovel.LOGRADOURO, logradouro);
            imovel.put(ImovelContract.Imovel.NUMERO, num);
            imovel.put(ImovelContract.Imovel.BAIRRO, bairro);
            imovel.put(ImovelContract.Imovel.CIDADE, cidade);
            imovel.put(ImovelContract.Imovel.VALOR, valor);
            imovel.put(ImovelContract.Imovel.CATEGORIA_ID, categoria);

            long newEnergiaId = db.insert(ImovelContract.Imovel.TABLE_NAME, null, imovel);
            db.close();
            Toast.makeText(this, "Imóvel cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            Intent it = new Intent(getApplicationContext(), MyActivity.class);
            startActivity(it);

        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }


}
