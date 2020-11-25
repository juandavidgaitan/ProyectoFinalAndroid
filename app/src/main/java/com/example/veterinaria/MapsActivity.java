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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    Bundle bundle;
    ClsCliente userActivo;
    CtlMarcador ctlMarcador;
    boolean marcadorActivo = false, recibioDatos = false;
    LatLng latLngActivo;
    private Marker markerPrueba;

    private final LatLng EAM = new LatLng(4.536307, -75.6723751);


    boolean cargooMapa=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ctlMarcador = new CtlMarcador(this);
        recibirDatos();

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EAM, 14));
        mMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.setOnMarkerClickListener(this);
        if (recibioDatos) {
            listarMarcadores();
            if (marcadorActivo) {
                latLngActivo = new LatLng(bundle.getDouble("latitud"), bundle.getDouble("longitud"));
                moverCamara(latLngActivo, 14);
            }
        }
        cargooMapa=true;
    }

    //cuando hago click en alguna parte del mapa
    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        Marker mark;
        mark = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        vistaMarcador(latLng);
    }

    private void vistaMarcador(LatLng latLng){
        Intent i = new Intent(this, MarcadorActivity.class);
        i.putExtra("latitud", latLng.latitude);
        i.putExtra("longitud", latLng.longitude);
        i.putExtra("estadoAccion", false);
        startActivity(i);
        finish();
    }

    //recibir datos desde otra vista
    private void recibirDatos() {
        bundle = getIntent().getExtras();
        recibioDatos = true;
    }

    //para agregar marcadores si tiene el usuario
   private void listarMarcadores() {
       List<Marcador> marcadores = ctlMarcador.listarPuntosUsuario();
       for (Marcador p : marcadores) {
           agregarMarcador(p);
       }
   }

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
        mMap.addMarker(new MarkerOptions().position(new LatLng(marcador.getLatitud(),
                marcador.getLongitud())).draggable(true).snippet(marcador.getDescripcion()).title(marcador.getNombre()).icon(BitmapDescriptorFactory.fromResource(R.drawable.veterinary)));
    }


    private void moverCamara(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public void vistaListaMarcadores(View v) {
        Intent i = new Intent(this, ListaMarcadoresVeteActivity.class);

        startActivity(i);
        finish();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
