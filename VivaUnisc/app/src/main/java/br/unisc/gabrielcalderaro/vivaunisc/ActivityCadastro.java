package br.unisc.gabrielcalderaro.vivaunisc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ActivityCadastro extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private EditText edTxtNome, edTxtEmail, edTxtCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_cadastro);
        ImageView img = (ImageView) findViewById(R.id.fotoUser);
        img.setImageResource(R.drawable.semfoto);

        edTxtNome = (EditText) findViewById(R.id.editTextNomeCompleto);
        edTxtEmail = (EditText) findViewById(R.id.editTextEmail);
        edTxtCelular = (EditText) findViewById(R.id.editTextCelular);

        AutoCompleteTextView places = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        places.setAdapter(new AutoComplete(this,android.R.layout.simple_dropdown_item_1line ));
        places.setOnItemClickListener(this);
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


    public void salvarCadastro(View view){

        String url = "http://vivaunisc.jossandro.com/estudante";

        final JSONObject jsonBody = new JSONObject();


        String nome = edTxtNome.getText().toString();
        String email = edTxtEmail.getText().toString();
        String celular = edTxtCelular.getText().toString();

        boolean validacao = true;

        if(nome == null || nome.equals("")){
            validacao = false;
            edTxtNome.setError(getString(R.string.error_valNome));
        }

        if(email == null || email.equals("")){
            validacao = false;
            edTxtEmail.setError(getString(R.string.error_valEmail));
        }

        if(validacao){
            try{
                jsonBody.put("nome", nome);
                jsonBody.put("email", email);
                jsonBody.put("celular", celular);
                jsonBody.put("cidade", "");
                jsonBody.put("id_oficina",9999);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response){

                            String retorno = response.toString();
                            char aux = retorno.charAt(11);
                            Log.d("RQ", "Retornou do request! 11 " + aux);

                            if (aux == '0') {
                                Log.d("RQ", "Retornou do request! " + response.toString());
                                Toast.makeText(getApplicationContext(), "Não foi possível cadastrar o estudante nesta oficina", Toast.LENGTH_LONG).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("RQ","caiu no onErrorResponse");
                            Log.d("RQ",error.toString());
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(this);

            queue.add(jsObjRequest);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // TODO Auto-generated method stub
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
