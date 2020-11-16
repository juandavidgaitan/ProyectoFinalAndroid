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
import com.example.veterinaria.Modelo.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarMascota extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

/*cliente*/
    EditText txtNombreDueno;
    EditText txtDueno;

    /*mascota*/
    EditText txtNombre;
    EditText txtEdad;
    EditText txtRaza;
    EditText txtId_mascota;
    Button btnRegistrarMascota;
    Button  btnBuscarMascota;
    StringRequest stringRequest;


    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


CtlGuardarMascota guardarMascota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);

        txtNombreDueno = (EditText) findViewById(R.id.txtNombreDueno);
        txtDueno = (EditText) findViewById(R.id.txtDueno);
        txtNombre = (EditText) findViewById(R.id.txtNit_veterinaria);
        txtEdad = (EditText) findViewById(R.id.txtVeterinaria);
        txtRaza = (EditText) findViewById(R.id.txtNombreMascota);
        txtId_mascota = (EditText) findViewById(R.id.txtId_mascota);

        btnRegistrarMascota=(Button) findViewById(R.id.btnRegistrarMascota);
        btnBuscarMascota=(Button) findViewById(R.id.btnBuscarMascotaTodo);

        btnRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarMascota("http://192.168.1.13/veterinaria/RegistrarMascota.php");
            }
        });

        btnBuscarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarMascotaTodo("http://192.168.1.13/veterinaria/BuscarMascota.php?id_mascota=" + txtId_mascota.getText()+"");
            }
        });


        request = Volley.newRequestQueue(getBaseContext());
    }
    public void BuscarCliente(View view) {
        String url = "http://192.168.1.13/veterinaria/wsJSONBuscarCliente.php?cedula=" + txtDueno.getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);


    }


    public void RegistrarMascota(String URL) {


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
                parametros.put("id_mascota",txtId_mascota.getText().toString());
                parametros.put("nombre",txtNombre.getText().toString());
                parametros.put("edad",txtEdad.getText().toString());
                parametros.put("raza",txtRaza.getText().toString());
                parametros.put("cliente_fk",txtDueno.getText().toString());
                return  parametros;

            }
        };
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getBaseContext()).addToRequestQueue(stringRequest);


    }

    private void BuscarMascotaTodo(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        txtNombre.setText(jsonObject.getString("nombre"));
                        txtEdad.setText(jsonObject.getString("edad"));
                        txtRaza.setText(jsonObject.getString("raza"));
                        txtDueno.setText(jsonObject.getString("cliente_fk"));



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

    public void ActualizarMascota (View view){
        String url="http://192.168.1.13/veterinaria/wsJSONUpdateMascota.php?";

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
                String id_mascota=txtId_mascota.getText().toString();
                String nombre=txtNombre.getText().toString();
                String raza=txtRaza.getText().toString();
                String edad=txtEdad.getText().toString();
                String cliente_fk=txtDueno.getText().toString();


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_mascota",id_mascota);
                parametros.put("nombre",nombre);
                parametros.put("raza",raza);
                parametros.put("edad",edad);
                parametros.put("cliente_fk",cliente_fk);


                return parametros;
            }
        };
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getBaseContext()).addToRequestQueue(stringRequest);
    }

    public void onResponse( JSONObject response) {
        Toast.makeText(getBaseContext(), "Mensaje" + response, Toast.LENGTH_SHORT).show();
        ClsCliente cliente = new ClsCliente();
        JSONArray json = response.optJSONArray("cliente");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
            cliente.setNombre(jsonObject.optString("nombre"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtNombreDueno.setText(cliente.getNombre());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "OPERACION ERRONEA EN REGISTRAR MASCOTA: " + error.toString(), Toast.LENGTH_SHORT).show();
    }


    public void Volver(View view) {

        Intent intent = new Intent(this, MenuCliente.class);
        startActivity(intent);

    }
    public void limpiar(){
        txtNombre.setText("");
        txtId_mascota.setText("");
        txtRaza.setText("");
        txtEdad.setText("");

        txtDueno.setText("");
        txtNombreDueno.setText("");

    }

    public void EliminarMascota(View view){

        String url = "http://192.168.1.13/veterinaria/wsJSONADeleteMascota.php?id_mascota="+txtId_mascota.getText().toString();

        stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.trim().equalsIgnoreCase("elimina")){
                    txtId_mascota.setText("");
                    txtNombre.setText("");
                    txtEdad.setText("");
                    txtRaza.setText("");
                    txtDueno.setText("");


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