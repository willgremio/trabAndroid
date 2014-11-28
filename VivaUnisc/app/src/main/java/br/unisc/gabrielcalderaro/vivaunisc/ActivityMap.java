package br.unisc.gabrielcalderaro.vivaunisc;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


public class ActivityMap extends ActionBarActivity {
    GoogleMap map;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);

        map = mapFragment.getMap();
    }

    public void action_none(View v){
        map.setMapType(GoogleMap.MAP_TYPE_NONE);
    }

    public void action_normal(View v){
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    public void action_satelite(View v){
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
    public void action_hybrid(View v){
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    public void action_terrain(View v){
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_map, menu);
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
