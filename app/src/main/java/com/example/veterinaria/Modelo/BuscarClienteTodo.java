package com.example.veterinaria.Modelo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.veterinaria.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuscarClienteTodo extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

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

    public void BuscarClientes(View view) {
        String url = "http://192.168.1.13/veterinaria/wsJSONBuscarClienteTodo.php?cedula=" + txtCedula.getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);


    }
    public void onResponse( JSONObject response) {
        Toast.makeText(getBaseContext(), "Mensaje" + response, Toast.LENGTH_SHORT).show();
        ClsCliente cliente = new ClsCliente();
        JSONArray json = response.optJSONArray("cliente");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);

            cliente.setNombre(jsonObject.optString("nombre"));
            cliente.setApellido(jsonObject.optString("apellido"));
            cliente.setTelefono(jsonObject.optString("telefono"));
            cliente.setCorreo(jsonObject.optString("correo"));
            cliente.setUsuario(jsonObject.optString("usuario"));
            cliente.setContrasena(jsonObject.optString("contrasena"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        txtTelefono.setText(cliente.getTelefono());
        txtCorreo.setText(cliente.getCorreo());
        txtUsuario.setText(cliente.getUsuario());
        txtContrasena.setText(cliente.getContrasena());






    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "OPERACION ERRONEA" + error.toString(), Toast.LENGTH_SHORT).show();
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
}