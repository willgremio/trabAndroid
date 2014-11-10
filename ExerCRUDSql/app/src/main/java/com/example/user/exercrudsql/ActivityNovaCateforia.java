package com.example.user.exercrudsql;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.exercrudsql.DB.ImovelContract;
import com.example.user.exercrudsql.DB.ImovelHelper;


public class ActivityNovaCateforia extends Activity {
    ImovelHelper odb = new ImovelHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_nova_cateforia);
    }

    public void cadastrarNovaCategoria(View view){
        final SQLiteDatabase db = this.odb.getWritableDatabase();
        ContentValues categoria = new ContentValues();

        EditText etNome = (EditText) findViewById(R.id.editNome);

        String nome = etNome.getText().toString();

        try{
            categoria.put(ImovelContract.Categoria.NOME, nome);

            long newCategoriaId = db.insert(ImovelContract.Categoria.TABLE_NAME, null, categoria);
            db.close();

            Toast.makeText(this, "Imóvel cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            Intent it = new Intent(getApplicationContext(), MyActivity.class);
            startActivity(it);

        } catch (Exception erro){
            erro.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_nova_cateforia, menu);
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
