package com.example.m77611.exerprova;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MyActivity extends ListActivity {
    private static final String[] nomes = new String[] { "Nova Leitura", "Listar ultimas leituras", "Exportar dados para JSON", "Sair" };

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, NovaLeitura.class));
                break;
            case 1:
                startActivity(new Intent(this, ListarLeituras.class));
                break;
            case 2:
                startActivity(new Intent(this, ExportarJSON.class));
                break;
            default:
                finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
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