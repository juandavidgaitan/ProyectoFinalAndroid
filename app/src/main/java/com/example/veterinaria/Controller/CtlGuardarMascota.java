package com.example.veterinaria.Controller;

import android.os.Bundle;

import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.veterinaria.R;


import org.json.JSONObject;

public class CtlGuardarMascota extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {


    EditText txtNombredueno;
    EditText txtDueno;
    EditText txtNombre;
    EditText txtEdad;
    EditText txtRaza;

    EditText txtId_mascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDueno = (EditText) findViewById(R.id.txtDueno);
        txtNombredueno = (EditText) findViewById(R.id.txtNombreDueno);
        txtNombre = (EditText) findViewById(R.id.txtNit_veterinaria);
        txtId_mascota = (EditText) findViewById(R.id.txtId_mascota);
        txtNombre = (EditText) findViewById(R.id.txtNit_veterinaria);
        txtEdad = (EditText) findViewById(R.id.txtVeterinaria);
        txtRaza = (EditText) findViewById(R.id.txtNombreMascota);



    }

    @Override

    public void onResponse(JSONObject response) {


            txtNombre.setText("");
            txtNombredueno.setText("");
            txtDueno.setText("");
            txtRaza.setText("");
            txtRaza.setText("");
            txtEdad.setText("");
            txtId_mascota.setText("");
        Toast.makeText(getApplicationContext(), "OPERACION EXITOSAMENTE", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "OPERACION ERRONEA 2 : " + error.toString(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}


