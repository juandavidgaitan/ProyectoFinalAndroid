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
import com.example.veterinaria.Modelo.ClsCliente;
import com.example.veterinaria.Modelo.ClsServico;
import com.example.veterinaria.Modelo.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarServicios extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    /*veterinaria*/
    EditText txtNit_Veterinaria;
    EditText txtVeterinaria;
    /*servicos*/
    EditText txtNombreServico;
    EditText txtDescripcion;
    Button btnBuscarVe;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_servicios);
        txtNit_Veterinaria = (EditText) findViewById(R.id.txtNit_veterinaria);
        txtVeterinaria = (EditText) findViewById(R.id.txtNombreVeterinaria);
        txtNombreServico = (EditText) findViewById(R.id.txtNombreServico);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        btnBuscarVe = (Button) findViewById(R.id.btnBuscarve);


        btnBuscarVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarVeteT("http://192.168.1.13/veterinaria/BuscarVeterinaria.php?nit_veterinaria=" + txtNit_Veterinaria.getText() + "");
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

    public void RegistrarServicio(View view) {
        String url = "http://192.168.1.13/veterinaria/wsJSONRegistroServicio.php?nombre=" + txtNombreServico.getText().toString() +
                "&descripcion=" + txtDescripcion.getText().toString() + "&veterinaria_fk=" + txtNit_Veterinaria.getText().toString();


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);


        limpiar();

        Toast.makeText(getApplicationContext(), "OPERACION EXITOSAMENTE", Toast.LENGTH_SHORT).show();


    }



    public void BuscarServicios(View view) {
        String url = "http://192.168.1.13/veterinaria/wsJSONBuscarServico.php?nombre=" + txtNombreServico.getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);


    }

    public void onResponse( JSONObject response) {
        Toast.makeText(getBaseContext(), "Mensaje" + response, Toast.LENGTH_SHORT).show();
        ClsServico servico = new ClsServico();
        JSONArray json = response.optJSONArray("servico");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);

            servico.setDescripcion(jsonObject.optString("descripcion"));
            servico.setVeterinaria_fk(jsonObject.optString("veterinaria_fk"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtDescripcion.setText(servico.getDescripcion());
        txtNit_Veterinaria.setText(servico.getVeterinaria_fk());






    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "OPERACION ERRONEA" + error.toString(), Toast.LENGTH_SHORT).show();
    }

    public void limpiar() {
        txtVeterinaria.setText("");
        txtNit_Veterinaria.setText("");
        txtDescripcion.setText("");
        txtNombreServico.setText("");
    }

    public void ActualizarServicio (View view){


        String url="http://192.168.1.13/veterinaria/wsJSONUpdateServicio.php?";


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
                String veterinaria_fk=txtNit_Veterinaria.getText().toString();
                String nombre=txtNombreServico.getText().toString();
                String descripcion=txtDescripcion.getText().toString();




                Map<String,String> parametros=new HashMap<>();
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

        String url = "http://192.168.1.13/veterinaria/wsJSONADeleteServicio.php?nombre="+txtNombreServico.getText().toString();

        stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.trim().equalsIgnoreCase("elimina")){
                    txtNombreServico.setText("");
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