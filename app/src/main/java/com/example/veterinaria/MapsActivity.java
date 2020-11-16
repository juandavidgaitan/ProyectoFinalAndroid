package com.example.veterinaria;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;


import com.example.veterinaria.Controller.CtlMarcador;
import com.example.veterinaria.Modelo.ClsCliente;
import com.example.veterinaria.Modelo.Marcador;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    Bundle bundle;
    ClsCliente userActivo;
    CtlMarcador ctlMarcador;
    boolean marcadorActivo = false, recibioDatos = false;
    LatLng latLngActivo;
 /*
    //accelerometro
    private static final float LIMITE_SENSIBILIDAD_SACUDIDA = 2.1f;
    private static final int LIMITE_TIEMPO_SACUDIDA = 250;
    private SensorManager sensorManager;
    private Sensor sensorAcelerometro, sensorProximidad;
    private long tiempoSacudida = 0;
*/
    //posicion
    double latitudActual;
    double longitudActual;
    float zoomActual=0;
    boolean cargooMapa=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ctlMarcador = new CtlMarcador(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(false);

        if (recibioDatos) {
          /*  listarMarcadores();*/
            if (marcadorActivo) {
                latLngActivo = new LatLng(bundle.getDouble("latitud"), bundle.getDouble("longitud"));
                moverCamara(latLngActivo, 5);
            }
        }
        cargooMapa=true;
    }

    //cuando hago click en alguna parte del mapa
    @Override
    public void onMapClick(LatLng latLng) {
        vistaMarcador(latLng);
    }

    private void vistaMarcador(LatLng latLng){
        Intent i = new Intent(this, MarcadorActivity.class);

        i.putExtra("latitud", latLng.latitude);
        i.putExtra("longitud", latLng.longitude);
        i.putExtra("estadoAccion", false);
        i.putExtra("userActivo", (Parcelable) userActivo);
        startActivity(i);
        finish();
    }

    //recibir datos desde otra vista
    private void recibirDatos() {
        bundle = getIntent().getExtras();
        userActivo = (ClsCliente) bundle.getSerializable("userActivo");
        marcadorActivo = bundle.getBoolean("marcadorActivo");
        recibioDatos = true;
    }

    //para agregar marcadores si tiene el usuario
   /* private void listarMarcadores(){
        List<Marcador> marcadores = ctlMarcador.listarPuntosUsuario(userActivo.getUsuario());
        for (Marcador p : marcadores){
            agregarMarcador(p);
        }
    }*/

    private void agregarMarcador(Marcador marcador){
        LatLng latLng = new LatLng(marcador.getLatitud(), marcador.getLongitud());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(marcador.getNombre());
        markerOptions.snippet(marcador.getDescripcion());
        switch (marcador.getColor()) {
            case "azul":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                break;
            case "rojo":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                break;
            case "amarillo":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                break;
            case "verde":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                break;
            case "naranja":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                break;
            case "rosado":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                break;
            case "violeta":
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                break;
            default:
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                break;
        }
        mMap.addMarker(markerOptions);
    }

    private void moverCamara(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public void vistaListaMarcadores(View v) {
        Intent i = new Intent(this, ListaMarcadoresActivity.class);
        i.putExtra("userActivo", (Parcelable) userActivo);
        startActivity(i);
        finish();
    }

}
