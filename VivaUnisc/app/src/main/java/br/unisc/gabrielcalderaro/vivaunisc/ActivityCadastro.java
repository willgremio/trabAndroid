package br.unisc.gabrielcalderaro.vivaunisc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.ArrayList;

import DB.OficinaContract;
import DB.OficinaDBHelper;

public class ActivityCadastro extends ActionBarActivity implements AdapterView.OnItemClickListener {
    OficinaDBHelper odb = new OficinaDBHelper(this);

    private EditText edTxtNome, edTxtEmail, edTxtCelular;
    private TextView edTxtIdOficina;
    private AutoCompleteTextView edTxtCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_cadastro);

        TextView id = (TextView) findViewById(R.id.id_oficina);
        Intent int2 = getIntent();
        id.setText(int2.getStringExtra("id_oficina"));

        ImageView img = (ImageView) findViewById(R.id.fotoUser);
        img.setImageResource(R.drawable.semfoto);

        edTxtNome = (EditText) findViewById(R.id.editTextNomeCompleto);
        edTxtEmail = (EditText) findViewById(R.id.editTextEmail);
        edTxtCelular = (EditText) findViewById(R.id.editTextCelular);
        edTxtIdOficina = (TextView) findViewById(R.id.id_oficina);
        edTxtCidade = (AutoCompleteTextView) findViewById(R.id.autocomplete);

        AutoCompleteTextView places = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        places.setAdapter(new AutoComplete(this,android.R.layout.simple_dropdown_item_1line ));
    }

    public void tiraFoto(View v){
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it, 0);
    }


    public void carregaImage(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        ImageView imageView = (ImageView) findViewById(R.id.fotoUser);
        if (requestCode == 0) {
            if (data != null){
                Bundle bundle = data.getExtras();
                if (bundle != null){
                    Bitmap img = (Bitmap) bundle.get("data");

                    imageView.setImageBitmap(img);
                }
            }
        } else if ( requestCode == 1) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(data.getData());
            }
        }


    }

    public void salvarCadastro(View view){
        String nome = edTxtNome.getText().toString();
        String email = edTxtEmail.getText().toString();
        String celular = edTxtCelular.getText().toString();
        String id = edTxtIdOficina.getText().toString();
        String cidade = edTxtCidade.getText().toString();

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
            salvarEstudanteBanco(nome, email, celular, cidade, id);
            salvarEstudanteWeb(nome, email, celular, cidade, id);
        }
    }

    public void salvarEstudanteWeb(String nome, String email, String celular, String cidade, String id_oficina) {
        String url = "http://vivaunisc.jossandro.com/estudante";
        final JSONObject jsonBody = new JSONObject();

        try{
            jsonBody.put("nome", nome);
            jsonBody.put("email", email);
            jsonBody.put("telefone", celular);
            jsonBody.put("cidade", cidade);
            jsonBody.put("id_oficina", id_oficina);

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response){

                            String retorno = response.toString();
                            char aux = retorno.charAt(11);
                            //Log.d("RQ", "Retornou do request! 11 " + aux);

                            if (aux == '0') {
                                //Log.d("RQ", "Retornou do request! " + response.toString());
                                Toast.makeText(getApplicationContext(), "Não foi possível cadastrar o estudante nesta oficina!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                                Intent it = new Intent(getApplicationContext(), ActivityOficinas.class);
                                startActivity(it);
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void salvarEstudanteBanco(String nome, String email, String celular, String cidade, String id_oficina) {
        final SQLiteDatabase db = this.odb.getWritableDatabase();
        ContentValues estudante = new ContentValues();
        try {
            estudante.put(OficinaContract.Estudante.ID_OFICINA, id_oficina);
            estudante.put(OficinaContract.Estudante.NOME, nome);
            estudante.put(OficinaContract.Estudante.EMAIL, email);
            estudante.put(OficinaContract.Estudante.TELEFONE, celular);
            estudante.put(OficinaContract.Estudante.CIDADE, cidade);
            long newEstudanteId = db.insert(OficinaContract.Estudante.TABLE_NAME, null, estudante);

        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }


  /*
   public void onActivityResult(int reqCode, int resCode, Intent data){
        if (resCode = RESULT_OK){
            if (reqCode == 1 ){

            }
        }
    }
  */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_cadastro, menu);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // TODO Auto-generated method stub
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
