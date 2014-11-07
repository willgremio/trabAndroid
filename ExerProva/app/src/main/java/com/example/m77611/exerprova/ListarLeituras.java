package com.example.m77611.exerprova;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import com.example.m77611.exerprova.DB.EnergiaContract;
import com.example.m77611.exerprova.DB.EnergiaHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class ListarLeituras extends ListActivity {
    EnergiaHelper odb = new EnergiaHelper(this);


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        final SQLiteDatabase db = this.odb.getReadableDatabase();

        String[] projection = {
                EnergiaContract.Energia.COD_CLI,
                EnergiaContract.Energia.RUA,
                EnergiaContract.Energia.NUMERO,
                EnergiaContract.Energia.KWH
        };

        Cursor c = db.query(
                EnergiaContract.Energia.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );


        setContentView(R.layout.activity_listar_leituras);

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

        if (c.moveToFirst()) {
            do {
                HashMap<String,String> item = new HashMap<String,String>();
                item.put( "cliente", "Cod: " + c.getString(0) + " Rua " + c.getString(1) + ", " + c.getString(2));
                item.put( "consumo", "Consumo " + c.getString(3) );
                list.add(item);

            } while (c.moveToNext());
        }

        String[] from = new String[] { "cliente", "consumo" };

        int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
        int layoytNativo = android.R.layout.two_line_list_item;

        setListAdapter(new SimpleAdapter(this,list,layoytNativo, from,to));
    }
}