package br.unisc.gabrielcalderaro.vivaunisc;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;


public class ActivityGrafico extends ActionBarActivity {

    EditText num1, num2, num3, num4, num5;
    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_grafico);

        num1 = (EditText)findViewById(R.id.num1);
        num2 = (EditText)findViewById(R.id.num2);
        num3 = (EditText)findViewById(R.id.num3);
        num4 = (EditText)findViewById(R.id.num4);
        num5 = (EditText)findViewById(R.id.num5);
        btnShow = (Button)findViewById(R.id.show);

        btnShow.setOnClickListener(btnShowOnClickListener);

    }


    OnClickListener btnShowOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(
                            ActivityGrafico.this,
                            ShowWebChartActivity.class);

                    intent.putExtra("NUM1", getNum(num1));
                    intent.putExtra("NUM2", getNum(num2));
                    intent.putExtra("NUM3", getNum(num3));
                    intent.putExtra("NUM4", getNum(num4));
                    intent.putExtra("NUM5", getNum(num5));

                    startActivity(intent);
                }

            };

    private int getNum(EditText editText){

        int num = 0;

        String stringNum = editText.getText().toString();
        if(!stringNum.equals("")){
            num = Integer.valueOf(stringNum);
        }

        return (num);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_grafico, menu);
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
