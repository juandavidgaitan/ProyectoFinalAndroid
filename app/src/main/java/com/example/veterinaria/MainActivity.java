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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.veterinaria.Modelo.ClsVeterinaria;

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

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingresa("http://192.168.1.13/veterinaria/wsJSONValidarCliente.php");
                IngresaVe("http://192.168.1.13/veterinaria/wsJSONValidarVeterinaria.php");

            }
        });
        }




    private void Ingresa(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(),MenuCliente.class);
                    startActivity(intent);
                }else if(!response.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), MenuVeterinaria.class);
                    startActivity(intent);
                } /*else

                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorretas ",Toast.LENGTH_SHORT).show();*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_SHORT).show();



            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<>();
                parametros.put("usuario",txtUsuario.getText().toString());
                parametros.put("contrasena",txtContrasena.getText().toString());

                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);







    }

    private void IngresaVe(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 if(!response.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), MenuVeterinaria.class);
                    startActivity(intent);
                } /*else

                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorretas ",Toast.LENGTH_SHORT).show();*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_SHORT).show();



            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<>();
                parametros.put("usuario",txtUsuario.getText().toString());
                parametros.put("contrasena",txtContrasena.getText().toString());

                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);







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