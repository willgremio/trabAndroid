package br.unisc.gabrielcalderaro.vivaunisc;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        String string = parent.getItemAtPosition(pos).toString();
        String id_oficina = string.split("\\-")[0];
        Toast.makeText(parent.getContext(), "id : " + id_oficina, Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
