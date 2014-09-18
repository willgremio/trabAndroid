package br.unisc.gabrielcalderaro.vivaunisc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class ActivityCadastro extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_cadastro);
        ImageView img = (ImageView) findViewById(R.id.fotoUser);
        img.setImageResource(R.drawable.semfoto);
    }

    public void tiraFoto(View v){
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (data != null){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                Bitmap img = (Bitmap) bundle.get("data");

                ImageView imageView = (ImageView) findViewById(R.id.fotoUser);
                imageView.setImageBitmap(img);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_cadastro, menu);
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
