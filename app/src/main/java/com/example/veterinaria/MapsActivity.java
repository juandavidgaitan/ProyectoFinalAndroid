package com.example.veterinaria;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, SensorEventListener {
    private GoogleMap mMap;
   private int idUsuario;
    double latitud=0,longitud=0;

    SensorManager sensorManager;
    Sensor s;
    Sensor acelerometro;

    private static final float sensibilidad_limite_sacudida =1.0f;
    private static final int tiempo_limite_sacudida =250;
    private long tiempoSacudida=0;

    private Marker markerPrueba;
    private final LatLng EAM = new LatLng(4.541763, -75.663464); // posicion de
    // la EAM

    private EditText txtDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle b = getIntent().getExtras();
        //this.idUsuario= b.getInt("idUsuario");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        txtDireccion = (EditText) findViewById(R.id.txtBuscar);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /* tipo de mapa (normal,satélite, hibrido o relieve)*/
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        /* Ubica el mapa en la EAM, con zoom de 15 ( 2 (continente) hasta 21 (calle))*/
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EAM, 18));

        mMap.getUiSettings().setZoomControlsEnabled(false);// se desactiva los
        // botones de zoom
        mMap.getUiSettings().setCompassEnabled(true);// visualizar la brujula

        mMap.addMarker(new MarkerOptions().position(EAM)// posicion del marcador
                .title("EAM")// titulo del marcador
                .snippet("Institucion universitaria EAM")
                // descripcion
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))// icono
                // marcador
                .anchor(0.5f, 0.5f));// tamaño


        mMap.setOnMapClickListener(this);// se asigna el lister asignado al
        // metodo onMapClick del mapa

        LatLng EAM = new LatLng(4.541763, -75.663464);
        markerPrueba = googleMap.addMarker(new MarkerOptions().position(EAM).title("EAM"));
        googleMap.setOnMarkerClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(EAM));



    }

    public void miPosicionActual(View view) {

        if (mMap.getMyLocation() != null) {

            double latitudActual = mMap.getMyLocation().getLatitude(); // determina
            // la
            // latitud
            // actual
            double longitudActual = mMap.getMyLocation().getLongitude();// determina
            // la
            // longitud
            // actual

            mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(new LatLng(
                            latitudActual, longitudActual), 15));// actualiza la
            // posicion del mapa
            // animadamente
        }
    }

    public void addMarker(View view) {

        double latitudMapa = mMap.getCameraPosition().target.latitude;// latitud
        /* del punto central que se este visualizando*/
        double altitudMapa = mMap.getCameraPosition().target.longitude; // altitud
        /* del punto central que se este visualizando*/
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitudMapa,
                altitudMapa)));
        Intent intent = new Intent(this, LugarActivity.class);
        startActivity(intent);
    }

    public void buscarDireccion(View view){
        String direccion = txtDireccion.getText().toString();
        List<Address> addressList = null;
        if(direccion!=null && !direccion.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try {
                //String direccion, Int numero maximo de resultados
                //Devuelve los puntos de las concordancias en el mapa segun el texto ingresado
                addressList = geocoder.getFromLocationName(direccion,1);
            } catch (IOException e) {
                e.printStackTrace();

            }
            Address address = addressList.get(0);
            LatLng posicion = new LatLng(address.getLatitude(),
                    address.getLongitude());



            mMap.addMarker(
                    new MarkerOptions().position(posicion).
                            title(direccion));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        Marker mark;
        mark = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .title("Ubicacion Seleccionada").snippet("Ya puedes guardar este punto"));
        latitud = mark.getPosition().latitude;
        longitud = mark.getPosition().longitude;


        // Recibe por parametro la posicion exacta donde se pulso y añade un
        // marcador
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).icon(
                BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)).title("XXXXXX")// titulo del marcador
                .snippet("IDSDADASDASDASDASD"));
    }

    private void guardarMarcador(View view){
        if(latitud == 0 || longitud ==0){
            Toast.makeText(this,"Elija la ubicacion del mapa", Toast.LENGTH_SHORT).show();

        }else{
            Intent intent = new Intent(this,LugarActivity.class);
            intent.putExtra("longitud",String.valueOf(longitud));
            intent.putExtra("latitud",String.valueOf(latitud));
            intent.putExtra("idUsuario",String.valueOf(idUsuario));
        }
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(markerPrueba)) {

            Intent intent = new Intent(this, LugarActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
