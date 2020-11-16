package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.veterinaria.Controller.CtlUser;
import com.example.veterinaria.Modelo.ClsCliente;
import com.example.veterinaria.Modelo.ClsVeterinaria;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtUsuario;
    EditText txtContrasena;
    Button btnIngresar;


    ProgressBar carga;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ClsVeterinaria clsVeterinaria;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        ClsCliente userActivo = null;


        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginVeterinaria ("http://192.168.0.4/veterinaria/LoginVeterinaria.php?usuario=" + txtUsuario.getText() + ""+"&contrasena="+ txtContrasena.getText());
                LoginCliente ("http://192.168.0.4/veterinaria/LoginCliente.php?usuario=" + txtUsuario.getText() + ""+"&contrasena="+ txtContrasena.getText()+"");


            }
        });
    }





    private void validarCliente(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), MenuCliente.class);
                    startActivity(intent);
                } /*else
                    Toast.makeText(MainActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", txtUsuario.getText().toString());
                parametros.put("contrasena", txtContrasena.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void LoginVeterinaria(String URL) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {



                    Intent intent = new Intent(getApplicationContext(), MenuVeterinaria.class);
                    startActivity(intent);

                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               /* Toast.makeText(getApplicationContext(), "Usuario o contrasena incorrectas", Toast.LENGTH_SHORT).show();*/
            }
        }
        );
        request = Volley.newRequestQueue(this);
        request.add(jsonArrayRequest);


    }
    private void LoginCliente(String URL) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {



                    Intent intent = new Intent(getApplicationContext(), MenuCliente.class);

                    startActivity(intent);

                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               /* Toast.makeText(getApplicationContext(), "Usuario o contrasena incorrectas", Toast.LENGTH_SHORT).show();*/
            }
        }
        );
        request = Volley.newRequestQueue(this);
        request.add(jsonArrayRequest);


    }


    public void RegistarVeterinaria(View view) {

        Intent intent = new Intent(this, RegistrarVeterinaria.class);
        startActivity(intent);

    }

    public void RegistarCliente(View view) {

        Intent intent = new Intent(this, RegistrarCliente.class);
        startActivity(intent);

    }
}