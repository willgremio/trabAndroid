package com.example.user.exercrudsql;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.exercrudsql.DB.ImovelContract;
import com.example.user.exercrudsql.DB.ImovelHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class MyActivity extends ListActivity implements android.view.View.OnClickListener {
    TextView id_imo;
    ImovelHelper odb = new ImovelHelper(this);


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        final SQLiteDatabase db = this.odb.getReadableDatabase();

        String[] projection = {
                ImovelContract.Imovel._ID,
                ImovelContract.Imovel.TITULO,
                ImovelContract.Imovel.LOGRADOURO
        };

        Cursor c = db.query(
                ImovelContract.Imovel.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );


        setContentView(R.layout.activity_my);

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

        if (c.moveToFirst()) {
            do {
                HashMap<String,String> item = new HashMap<String,String>();
                item.put( "id", c.getString(0) );
                item.put( "titulo", c.getString(1) );
                item.put( "logradouro", c.getString(2) );
                list.add(item);

            } while (c.moveToNext());
        }

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                id_imo = (TextView) view.findViewById(R.id.imovel_Id);
                String valAnimalId = id_imo.getText().toString();
                Intent  objIndent = new Intent(getApplicationContext(),ImovelDetalhes.class);
                objIndent.putExtra("imovel_id", valAnimalId);
                startActivity(objIndent);
            }
        });

        ListAdapter adapter = new SimpleAdapter( MyActivity.this,list, R.layout.view_imovel_lista, new String[] { "id", "titulo","logradouro"}, new int[] {R.id.imovel_Id, R.id.tituloView, R.id.logradouroView});
        setListAdapter(adapter);

    }

    public void abreTelaCadastro(View view) {
        Intent it = new Intent(getApplicationContext(), NovoImovel.class);
        startActivity(it);
    }

    public void abreTelaCategoria(View view){
        Intent it = new Intent(getApplicationContext(), ActivityNovaCateforia.class);
        startActivity(it);
    }

    @Override
    public void onClick(View v) {

    }
}