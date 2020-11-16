package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.veterinaria.Modelo.ClsVeterinaria;

import org.json.JSONObject;

public class RegistrarCliente extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    EditText txtCedula;
    EditText txtNombre;
    EditText txtApellido;
    EditText txtCorreo;
    EditText txtTelefono;
    EditText txtUsuario;
    EditText txtContrasena;
    Button btnRegistrar;



    ProgressBar carga;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ClsVeterinaria clsVeterinaria;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);

        txtCedula = (EditText) findViewById(R.id.txtCedula);
        txtNombre = (EditText) findViewById(R.id.txtNombres);
        txtApellido = (EditText) findViewById(R.id.txtApellidos);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        btnRegistrar = (Button) findViewById(R.id.btnRegistra);



        request = Volley.newRequestQueue(getBaseContext());
    }

    public void RegistrarCliente(View view) {
        String url = "http://192.168.1.13/veterinaria/wsJSONRegistroCliente.php?cedula=" + txtCedula.getText().toString() +
                "&nombre=" + txtNombre.getText().toString() + "&apellido=" + txtApellido.getText().toString() +
                "&correo=" + txtCorreo.getText().toString() + "&telefono=" + txtTelefono.getText().toString() + "&usuario=" + txtUsuario.getText().toString() + "&contrasena=" + txtContrasena.getText().toString();


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "OPERACION ERRONEA EN REGISTRAR CLIENTE:" + error.toString(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResponse(JSONObject response) {

        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
        Toast.makeText(getApplicationContext(), "OPERACION EXITOSAMENTE:", Toast.LENGTH_SHORT).show();
        limpiar();

    }


    public void limpiar(){
        txtNombre.setText("");
        txtCedula.setText("");
        txtCorreo.setText("");
        txtUsuario.setText("");
        txtTelefono.setText("");
        txtContrasena.setText("");
        txtApellido.setText("");
    }

    public void Volver(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}