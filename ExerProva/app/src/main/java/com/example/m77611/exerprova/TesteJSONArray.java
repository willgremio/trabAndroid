package com.example.m77611.exerprova;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m77611.exerprova.DB.EnergiaContract;
import com.example.m77611.exerprova.DB.EnergiaHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TesteJSONArray extends Activity {
EnergiaHelper odb = new EnergiaHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_jsonarray);
        final SQLiteDatabase db = this.odb.getReadableDatabase();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        TextView tv = (TextView) findViewById(R.id.tvJSONARRAY);


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
        try {
            if (c.moveToFirst()) {
                do {
                    jsonObject.put("Código", c.getString(0));
                    jsonObject.put("Rua", c.getString(1));
                    jsonObject.put("Número ", c.getString(2));
                    jsonObject.put("Consumo", c.getString(3));

                    jsonArray.put(jsonObject);

                } while (c.moveToNext());
            }
        } catch (JSONException error){
            error.printStackTrace();
        }

        tv.setText(jsonArray.toString());

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "ArquivoJson.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(jsonArray.toString());
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        enviarEmail();
    }

    public void enviarEmail() {
        File myFile = new File(Environment.getExternalStorageDirectory(), "/Notes/ArquivoJson.txt");
        Uri uri = Uri.fromFile(myFile);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("plain/text");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"willian.serafini@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Exer JSON!");
        i.putExtra(Intent.EXTRA_TEXT   , "exportando do banco pro txt");
        i.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(i);
    }

}
