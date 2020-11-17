package com.example.veterinaria;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.veterinaria.Controller.CtlMarcador;
import com.example.veterinaria.Modelo.ClsCliente;
import com.example.veterinaria.Modelo.Marcador;

public class MarcadorActivity extends AppCompatActivity  {

    String[] listaColores= {"azul","rojo","amarillo", "verde", "naranja", "rosado", "violeta"};

    EditText txtNombre, txtDescripcion;
    Button btnAccion;
    Spinner spnColores;
    Bundle bundle;
    CtlMarcador ctlMarcador;
    //si el estado esta falso el marcado se guarda, si esta en true el marcador se modifica
    boolean estadoAccion = false;
    Marcador marcador =null;

    //usuario que esta utilizando la aplicacion en el momento
    ClsCliente userActivo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcador);
        ctlMarcador = new CtlMarcador(this);
        txtNombre = findViewById(R.id.txtNombreMarcador);
        txtDescripcion = findViewById(R.id.txtDescrpcionMarcador);
        btnAccion = findViewById(R.id.btnAccionMarcador);
        spnColores = findViewById(R.id.spnColores);
        llenarSpinner();
        bundle = getIntent().getExtras();
        estadoAccion = bundle.getBoolean("estadoAccion");
   //     userActivo = (ClsCliente) bundle.getSerializable("userActivo");

        if(estadoAccion){
            btnAccion.setText("editar");
            marcador = (Marcador) bundle.getSerializable("modificar");
            //cargar datos para modificar
            txtNombre.setEnabled(false);
            txtNombre.setText(marcador.getNombre());
            txtDescripcion.setText(marcador.getDescripcion());
            for(int i= 0;i < listaColores.length; i++){
                if (listaColores[i].equals(marcador.getColor())){
                    spnColores.setSelection(i);
                    break;
                }
            }
        }else {
            btnAccion.setText("guardar");
            marcador = new Marcador();
            marcador.setLatitud(bundle.getDouble("latitud"));
            marcador.setLongitud(bundle.getDouble("longitud"));

        }

        btnAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString();
                String descripcion = txtDescripcion.getText().toString();
                String color = spnColores.getSelectedItem().toString();
                if(nombre.isEmpty() || descripcion.isEmpty()){
                    imprimir("Se deben llenar bien los datos");
                }else{
                    marcador.setNombre(nombre);
                    marcador.setDescripcion(descripcion);
                    marcador.setColor(color);
                   /* marcador.setUsuario(userActivo.getUsuario());*/
                    if (estadoAccion){
                        //modificar
                        imprimir(marcador.toString());
                        try {
                           /* ctlMarcador.actualizar(marcador);*/
                        } catch (Exception e) {
                            imprimir(e.getMessage());
                        }
                        Intent intent = new Intent(getApplicationContext(), ListaMarcadoresActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        //guardar
                        try {
                            if(ctlMarcador.registrar(marcador)){
                                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                intent.putExtra("latitud", marcador.getLatitud());
                                intent.putExtra("longitud", marcador.getLongitud());

                                startActivity(intent);
                                finish();
                            }else {
                                imprimir("ya se encuentra un marcador con ese nombre debe cambiar");
                                txtNombre.setText("");
                            }
                        } catch (Exception e) {
                            imprimir(e.getMessage());
                        }
                    }
                }
            }
        });

    }


    private void llenarSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaColores);
        spnColores.setAdapter(adapter);
    }

    public void imprimir(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(estadoAccion){
            Intent intent = new Intent(getApplicationContext(), ListaMarcadoresActivity.class);
            //intent.putExtra("userActivo", (Parcelable) userActivo);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
          //  intent.putExtra("userActivo", (Parcelable) userActivo);
          intent.putExtra("marcadorActivo", false);
            startActivity(intent);
            finish();
        }
    }


}