
package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.veterinaria.Controller.CtlGuardarMascota;
import com.example.veterinaria.Modelo.ClsCliente;
import com.example.veterinaria.Modelo.ClsVeterinario;
import com.example.veterinaria.Modelo.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarVeterinario extends AppCompatActivity   {

    /*veterinaria*/
    EditText txtNit_Veterinaria;
    EditText txtVeterinaria;
    /*veterinario*/
    EditText txtNombreVete;
    EditText txtCedula;
    EditText txtApellidos;
    EditText txtEdad;
    EditText txtCorreoVete;
    EditText txtSalario;
    Button btnBuscar;
    CtlGuardarMascota ctl;
    Button btnRegistarVeterinario;
    Button btnBuscarVeterinario;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_veterinario);
        txtNit_Veterinaria = (EditText) findViewById(R.id.txtNit_veterinaria);
        txtVeterinaria = (EditText) findViewById(R.id.txtNombreVeterinaria);
        txtNombreVete = (EditText) findViewById(R.id.txtNombrevete);
        txtCedula = (EditText) findViewById(R.id.txtCedula);
        txtApellidos = (EditText) findViewById(R.id.txtApellidos);
        txtEdad = (EditText) findViewById(R.id.txtedad);
        txtCorreoVete = (EditText) findViewById(R.id.txtCorreovete);
        txtSalario = (EditText) findViewById(R.id.txtSalario);
        btnBuscar=(Button) findViewById(R.id.btnBuscar);
        btnBuscarVeterinario=(Button) findViewById(R.id.btnBuscarVeterinario);
        btnRegistarVeterinario=(Button) findViewById(R.id.btnRegstrarVeterinario);


        btnRegistarVeterinario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarVeterinario("http://192.168.0.4/veterinaria/RegistrarVeterinario.php");
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarVete("http://192.168.0.4/veterinaria/BuscarVeterinaria.php?nit_veterinaria=" + txtNit_Veterinaria.getText()+"");
            }
        });
        btnBuscarVeterinario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarVeterinario("http://192.168.0.4/veterinaria/BuscarVeterinario.php?cedula=" + txtCedula.getText()+"");
            }
        });
    }
    public void Volver(View view) {

        Intent intent = new Intent(this, MenuVeterinaria.class);
        startActivity(intent);

    }

    private void BuscarVete(String URL) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        txtVeterinaria.setText(jsonObject.getString("nombre"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        request= Volley.newRequestQueue(this);
        request.add(jsonArrayRequest);

    }

    public void limpiar(){
        txtNombreVete.setText("");
        txtCedula.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtCorreoVete.setText("");
        txtSalario.setText("");
        txtNit_Veterinaria.setText("");
    }





    public void RegistrarVeterinario(String URL) {


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
                parametros.put("cedula",txtCedula.getText().toString());
                parametros.put("nombre",txtNombreVete.getText().toString());
                parametros.put("apellido",txtApellidos.getText().toString());
                parametros.put("edad",txtEdad.getText().toString());
                parametros.put("correo",txtCorreoVete.getText().toString());
                parametros.put("salario",txtSalario.getText().toString());
                parametros.put("veterinaria_fk",txtNit_Veterinaria.getText().toString());
                return  parametros;

            }
        };
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getBaseContext()).addToRequestQueue(stringRequest);


    }

    private void BuscarVeterinario(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        txtNombreVete.setText(jsonObject.getString("nombre"));
                        txtApellidos.setText(jsonObject.getString("apellido"));
                        txtEdad.setText(jsonObject.getString("edad"));
                        txtCorreoVete.setText(jsonObject.getString("correo"));
                        txtSalario.setText(jsonObject.getString("salario"));
                        txtNit_Veterinaria.setText(jsonObject.getString("veterinaria_fk"));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR DE CONEXION : ",Toast.LENGTH_SHORT).show();
            }
        }
        );
        request=Volley.newRequestQueue(this);
        request.add(jsonArrayRequest);

    }

    public void ActualizarVeterinario (View view){
        String url="http://192.168.0.4/veterinaria/wsJSONUpdateVeterinario.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.trim().equalsIgnoreCase("actualiza")){
                    //  txtApellido.setText("");
                    //  txtEdad.setText("");
                    // txtEdad.setText("");
                    Toast.makeText(getBaseContext(),"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();
                    limpiar();
                }else{
                    Toast.makeText(getBaseContext(),"Se ha modificado con exito ",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                    limpiar();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String cedula=txtCedula.getText().toString();
                String nombre=txtNombreVete.getText().toString();
                String apellido=txtApellidos.getText().toString();
                String edad=txtEdad.getText().toString();
                String correo=txtCorreoVete.getText().toString();
                String salario=txtSalario.getText().toString();
                String veterinaria_fk=txtNit_Veterinaria.getText().toString();

                Map<String,String> parametros=new HashMap<>();
                parametros.put("cedula",cedula);
                parametros.put("nombre",nombre);
                parametros.put("apellido",apellido);
                parametros.put("correo",correo);
                parametros.put("edad",edad);
                parametros.put("salario",salario);
                parametros.put("veterinaria_fk",veterinaria_fk);

                return parametros;
            }
        };
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getBaseContext()).addToRequestQueue(stringRequest);
    }

    public void EliminarVete (View view){

        String url = "http://192.168.0.4/veterinaria/wsJSONADeleteVeterinario.php?cedula="+txtCedula.getText().toString();

        stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.trim().equalsIgnoreCase("elimina")){
                    txtCedula.setText("");
                    txtNombreVete.setText("");
                    txtApellidos.setText("");
                    txtEdad.setText("");
                    txtCorreoVete.setText("");
                    txtSalario.setText("");
                    txtNit_Veterinaria.setText("");

                    Toast.makeText(getApplicationContext(),"Se ha Eliminado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    if (response.trim().equalsIgnoreCase("noExiste")){
                        Toast.makeText(getApplicationContext(),"No se encuentra la persona ",Toast.LENGTH_SHORT).show();
                        Log.i("RESPUESTA: ",""+response);
                    }else{
                        Toast.makeText(getApplicationContext(),"No se ha Eliminado ",Toast.LENGTH_SHORT).show();
                        Log.i("RESPUESTA: ",""+response);
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext()
                        ,"No se ha podido conectar",Toast.LENGTH_SHORT).show();

            }
        });
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }



}