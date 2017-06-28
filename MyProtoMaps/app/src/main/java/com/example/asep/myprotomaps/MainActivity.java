package com.example.asep.myprotomaps;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener, OnMapReadyCallback{

    Spinner spinner;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        spinner = (Spinner) findViewById(R.id.spnType);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView myText = (TextView) view;
        Toast.makeText(this, "You selected "+myText.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Spinner spinner = (Spinner) findViewById(R.id.spnType);
        String type = spinner.getSelectedItem().toString();
        switch(type){
            case "Normal":
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case "Satellite":
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case "Hybrid":
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case "Terrain":
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
    }
}
