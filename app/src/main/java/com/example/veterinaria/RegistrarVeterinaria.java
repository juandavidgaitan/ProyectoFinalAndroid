package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.veterinaria.Modelo.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class RegistrarVeterinaria extends AppCompatActivity {
    EditText txtNit;
    EditText txtNombre;
    EditText txtDireccion;
    EditText txtTelefono;
    EditText txtUsuario;
    EditText txtContrasena;
    Button btnRegistrar;

    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_veterinaria);

        txtNit = (EditText) findViewById(R.id.txtNit);
        txtNombre = (EditText) findViewById(R.id.txtNit_veterinaria);
        txtDireccion = (EditText) findViewById(R.id.txtNombreVeterinaria);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        btnRegistrar = (Button) findViewById(R.id.btnRegistra);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarVeterinaria("http://172.20.10.3/veterinaria/RegistrarVeterinaria.php");
            }
        });

        request = Volley.newRequestQueue(getBaseContext());

    }

    public void RegistrarVeterinaria(String URL) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                limpiar();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nit_veterinaria",txtNit.getText().toString());
                parametros.put("nombre",txtNombre.getText().toString());
                parametros.put("direccion",txtDireccion.getText().toString());
                parametros.put("telefono",txtTelefono.getText().toString());
                parametros.put("usuario",txtUsuario.getText().toString());
                parametros.put("contrasena",txtContrasena.getText().toString());
                return  parametros;

            }
        };
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getBaseContext()).addToRequestQueue(stringRequest);

    }

    public void limpiar(){
        txtNombre.setText("");
        txtNit.setText("");
        txtDireccion.setText("");
        txtUsuario.setText("");
        txtTelefono.setText("");
        txtContrasena.setText("");
    }
    public void Volver(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}