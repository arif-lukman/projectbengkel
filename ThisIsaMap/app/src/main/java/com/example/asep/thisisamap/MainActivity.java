package com.example.asep.thisisamap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, OnItemSelectedListener, OnMapClickListener{

    //vars
    private GoogleMap mMap;
    private Spinner mSpinner;
    private TextView latTxt;
    private TextView lngTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //handling spinner
        mSpinner = (Spinner) findViewById(R.id.spnType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.mapTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        //handling fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //ambil googlemap
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        //update tipe mapnya
        updateMapType();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //update tipe mapnya
        updateMapType();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateMapType(){
        if(mMap == null){
            return;
        }
        ///ngubah tipe map
        //ambil value spinner
        Spinner spinner = (Spinner) findViewById(R.id.spnType);
        String val = String.valueOf(spinner.getSelectedItem());
        //tampilin di toast
        Toast.makeText(MainActivity.this, "You chose " + val + " map type.", Toast.LENGTH_SHORT).show();
        //ganti tipe map sesuai value spinner
        switch(val){
            //hybrid - mode satelite tapi ada teksnya
            case "Hybrid":
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            //normal, kek gmaps biasanya
            case "Normal":
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            //kek bumi aslinya
            case "Satellite":
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            //mirip2 ama normal, cuman beda. gatau dimana bedanya.
            case "Terrain":
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
    }

    public void updateLatLng(LatLng latlng){
        //ambil objek textview
        latTxt = (TextView) findViewById(R.id.latTxt);
        lngTxt = (TextView) findViewById(R.id.lngTxt);
        //tampilin latitude ama longitudenya
        latTxt.setText(String.valueOf(latlng.latitude));
        lngTxt.setText(String.valueOf(latlng.longitude));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //debug
        //Log.d("Position", "Lat = " +latLng.latitude+ " Long = " +latLng.longitude);
        //Toast.makeText(MainActivity.this, "Lat = " +latLng.latitude+ " Long = " +latLng.longitude, Toast.LENGTH_SHORT).show();
        //hapus marker sebelumnya
        mMap.clear();
        //bikin marker
        mMap.addMarker(new MarkerOptions()
                //posisi
                .position(latLng)
                //teks
                .title("You are here")
                //icon
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        //update display lat & lng
        updateLatLng(latLng);
    }
}
