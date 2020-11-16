package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.veterinaria.Controller.CtlBuscarVeterinario;
import com.example.veterinaria.Modelo.ClsMascota;
import com.example.veterinaria.Modelo.ClsVeterinaria;
import com.example.veterinaria.Modelo.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarCita  extends AppCompatActivity  {
    /*veterinaria*/
    EditText txtNit_Veterinaria;
    EditText txtVeterinaria;

    /*mascota*/
    EditText txtMascotaid;
    EditText txtNombre;
    /*Cita*/
    EditText txtDescripcion;
    EditText txtHora;
    EditText txtId_cita;
    Button btnBuscarVeterinaria;
    Button btnBuscarMascota;

    Button btnRegistrarCita;
    Button btnBuscarcita;

    CtlBuscarVeterinario ctlBuscarVeterinaria = new CtlBuscarVeterinario();
    ClsVeterinaria ClsVeterinaria;
    ClsMascota ClsMascota;
    StringRequest stringRequest;



    ProgressBar carga;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cita);

        txtNit_Veterinaria = (EditText) findViewById(R.id.txtNitVeterinaria);
        txtVeterinaria = (EditText) findViewById(R.id.txtVeterinaria);
        txtMascotaid = (EditText) findViewById(R.id.txtNit_veterinaria);
        txtNombre = (EditText) findViewById(R.id.txtNombreMascota);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        txtHora = (EditText) findViewById(R.id.txtHora);
        txtId_cita = (EditText) findViewById(R.id.txtId_cita);
        btnBuscarVeterinaria=(Button) findViewById(R.id.btnBuscarVeterinaria);
        btnBuscarMascota=(Button) findViewById(R.id.BuscarMascota);

        btnRegistrarCita=(Button) findViewById(R.id.btnRegistrarCita);
        btnBuscarcita=(Button) findViewById(R.id.btnBuscarCita);


        request = Volley.newRequestQueue(getBaseContext());

        btnRegistrarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarCita("http://192.168.1.13/veterinaria/RegistrarCita.php");
            }
        });
        btnBuscarcita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarCita("http://192.168.1.13/veterinaria/BuscarCita.php?id_cita=" + txtId_cita.getText()+"");
            }
        });

        btnBuscarVeterinaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarVeterinaria("http://192.168.1.13/veterinaria/BuscarVeterinaria.php?nit_veterinaria=" + txtNit_Veterinaria.getText()+"");
            }
        });

        btnBuscarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarMascota("http://192.168.1.13/veterinaria/BuscarMascota.php?id_mascota=" + txtMascotaid.getText()+"");
            }
        });
    }



    private void BuscarVeterinaria(String URL) {

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
                Toast.makeText(getApplicationContext(),"ERROR DE CONEXION EN REGISTRAR CITA:",Toast.LENGTH_SHORT).show();
            }
        }
        );
        request=Volley.newRequestQueue(this);
        request.add(jsonArrayRequest);


    }

    private void BuscarMascota(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        txtNombre.setText(jsonObject.getString("nombre"));

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

        public void Volver(View view) {

        Intent intent = new Intent(this, MenuCliente.class);
        startActivity(intent);

    }

    public void RegistrarCita(String URL) {


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
                parametros.put("id_cita",txtId_cita.getText().toString());
                parametros.put("mascota_fk",txtMascotaid.getText().toString());
                parametros.put("veterinaria_fk",txtNit_Veterinaria.getText().toString());
                parametros.put("hora",txtHora.getText().toString());
                parametros.put("descripcion",txtDescripcion.getText().toString());


                return  parametros;

            }
        };
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getBaseContext()).addToRequestQueue(stringRequest);


    }

    private void BuscarCita(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        txtId_cita.setText(jsonObject.getString("id_cita"));
                        txtMascotaid.setText(jsonObject.getString("mascota_fk"));
                        txtNit_Veterinaria.setText(jsonObject.getString("veterinaria_fk"));
                        txtHora.setText(jsonObject.getString("hora"));
                        txtDescripcion.setText(jsonObject.getString("descripcion"));




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
        txtMascotaid.setText("");
        txtNit_Veterinaria.setText("");
        txtDescripcion.setText("");
        txtHora.setText("");
        txtNombre.setText("");
        txtVeterinaria.setText("");
        txtId_cita.setText("");
    }

    public void ActualizarCita (View view){


        String url="http://192.168.1.13/veterinaria/wsJSONUpdateCita.php?";


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

                String id_cita=txtId_cita.getText().toString();
                String mascota_fk=txtMascotaid.getText().toString();
                String veterinaria_fk=txtNit_Veterinaria.getText().toString();
                String hora=txtHora.getText().toString();
                String descripcion=txtDescripcion.getText().toString();

                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_cita",id_cita);
                parametros.put("mascota_fk",mascota_fk);
                parametros.put("veterinaria_fk",veterinaria_fk);
                parametros.put("hora",hora);
                parametros.put("descripcion",descripcion);


                return parametros;
            }
        };
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getBaseContext()).addToRequestQueue(stringRequest);
    }

    public void EliminarCita (View view){

        String url = "http://192.168.1.13/veterinaria/wsJSONADeleteCita.php?id_cita="+txtId_cita.getText().toString();

        stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.trim().equalsIgnoreCase("elimina")){
                    txtMascotaid.setText("");
                    txtId_cita.setText("");
                    txtDescripcion.setText("");
                    txtNit_Veterinaria.setText("");
                    txtHora.setText("");


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
