package com.example.veterinaria;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.veterinaria.Controller.CtlLugar;
import com.example.veterinaria.Modelo.ClsLugar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class LugarActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener {
    private EditText nombreLugar;
    private EditText descripcion;

    private Marker markerPrueba;


    ClsLugar recibirLugar;
    CtlLugar gestionLugar;
    static final int WRITE_EXST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);
        nombreLugar =(EditText) findViewById(R.id.txtNombrePunto);
        descripcion = (EditText) findViewById(R.id.txtDescripcionDelPunto);


        gestionLugar =new CtlLugar(this);

        recibirLugar = gestionLugar.getLugar();

    }



    private void askForPermission(String permission, Integer requestCode){
        if(ContextCompat.checkSelfPermission(LugarActivity.this, permission)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(LugarActivity.this,permission)){
                ActivityCompat.requestPermissions(LugarActivity.this, new String[]{permission},requestCode);

            }else{
                ActivityCompat.requestPermissions(LugarActivity.this, new String[]{permission},requestCode);
            }
        }else{
            Registrar();
        }
    }

    public void ask(View view){
        switch (view.getId()){
            case R.id.btnRegistrar:
                askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXST);
                break;
        }
    }

    public void Registrar(){
        String Nombre = nombreLugar.getText().toString();
        String descripciones = descripcion.getText().toString();


        if(!Nombre.equals("")&&!descripciones.equals("")){
                if(gestionLugar.guardarMarca(Nombre,descripciones)){
                    Toast.makeText(getApplicationContext(),"Registro exitoso!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MapsActivity.class);

                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"El lugar ya existe.", Toast.LENGTH_SHORT).show();
                    onMarkerClick(markerPrueba);
                }
            }else{
            Toast.makeText(getApplicationContext(), "Por favor rellene todas las casillas." , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(markerPrueba)) {
            Toast.makeText(this, "Evento", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}