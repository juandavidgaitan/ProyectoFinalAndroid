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
import com.example.veterinaria.Modelo.ClsServico;
import com.example.veterinaria.Modelo.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarServicios extends AppCompatActivity{

    /*veterinaria*/
    EditText txtNit_Veterinaria;
    EditText txtVeterinaria;
    /*servicos*/
    EditText txtNombreServico;
    EditText txtDescripcion;
    EditText txtId_servicio;
    Button btnBuscarVe;

    Button btnBuscarServicio;
    Button btnRegistrarServicio;


    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    CtlGuardarMascota ctl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_servicios);
        txtNit_Veterinaria = (EditText) findViewById(R.id.txtNit_veterinaria);
        txtVeterinaria = (EditText) findViewById(R.id.txtNombreVeterinaria);
        txtNombreServico = (EditText) findViewById(R.id.txtNombreServico);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        txtId_servicio = (EditText) findViewById(R.id.txtId_servicio);
        btnBuscarVe = (Button) findViewById(R.id.btnBuscarve);
        btnBuscarServicio = (Button) findViewById(R.id.btnBuscarServicio);
        btnRegistrarServicio = (Button) findViewById(R.id.btnRegistrarServicio);


        btnRegistrarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarServicio("http://192.168.0.5/veterinaria/RegistrarServicio.php");
            }
        });

        btnBuscarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarServicio("http://192.168.0.5/veterinaria/BuscarServicio.php?id_servicio=" + txtId_servicio.getText()+"");
            }
        });


        btnBuscarVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarVeteT("http://192.168.0.5/veterinaria/BuscarVeterinaria.php?nit_veterinaria=" + txtNit_Veterinaria.getText() + "");
            }
        });
    }

    public void Volver(View view) {

        Intent intent = new Intent(this, MenuVeterinaria.class);
        startActivity(intent);

    }

    private void BuscarVeteT(String URL) {

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
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
            }
        }
        );
        request = Volley.newRequestQueue(this);
        request.add(jsonArrayRequest);


    }

    public void RegistrarServicio(String URL) {


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
                parametros.put("id_servicio",txtId_servicio.getText().toString());
                parametros.put("nombre",txtNombreServico.getText().toString());
                parametros.put("descripcion",txtDescripcion.getText().toString());
                parametros.put("veterinaria_fk",txtNit_Veterinaria.getText().toString());

                return  parametros;

            }
        };
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getBaseContext()).addToRequestQueue(stringRequest);


    }

    private void BuscarServicio(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        txtNombreServico.setText(jsonObject.getString("nombre"));
                        txtDescripcion.setText(jsonObject.getString("descripcion"));
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
    public void limpiar() {
        txtVeterinaria.setText("");
        txtId_servicio.setText("");
        txtNit_Veterinaria.setText("");
        txtDescripcion.setText("");
        txtNombreServico.setText("");
    }

    public void ActualizarServicio (View view){


        String url="http://192.168.0.5/veterinaria/wsJSONUpdateServicio.php?";


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

                String id_servicio=txtId_servicio.getText().toString();
                String veterinaria_fk=txtNit_Veterinaria.getText().toString();
                String nombre=txtNombreServico.getText().toString();
                String descripcion=txtDescripcion.getText().toString();

                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_servicio",id_servicio);
                parametros.put("veterinaria_fk",veterinaria_fk);
                parametros.put("nombre",nombre);
                parametros.put("descripcion",descripcion);


                return parametros;
            }
        };
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getBaseContext()).addToRequestQueue(stringRequest);
    }

    public void EliminarServicio (View view){

        String url = "http://192.168.0.5/wsJSONADeleteServicio.php?id_servicio="+txtId_servicio.getText().toString();

        stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.trim().equalsIgnoreCase("elimina")){
                    txtNombreServico.setText("");
                    txtId_servicio.setText("");
                    txtDescripcion.setText("");
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