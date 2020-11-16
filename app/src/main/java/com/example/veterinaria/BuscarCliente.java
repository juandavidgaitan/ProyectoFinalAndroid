package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.veterinaria.Modelo.ClsCliente;
import com.example.veterinaria.Modelo.ClsVeterinaria;
import com.example.veterinaria.Modelo.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BuscarCliente extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

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
        setContentView(R.layout.activity_buscar_cliente);

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
        Toast.makeText(getApplicationContext(), "OPERACION ERRONEA 3:" + error.toString(), Toast.LENGTH_SHORT).show();
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

    public void ActualizarCliente (View view){
        String url="http://192.168.0.5/veterinaria/wsJSONUpdateCliente.php?";

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
                String nombre=txtNombre.getText().toString();
                String apellido=txtApellido.getText().toString();
                String correo=txtCorreo.getText().toString();
                String telefono=txtTelefono.getText().toString();
                String usuario=txtUsuario.getText().toString();
                String contrasena=txtContrasena.getText().toString();

                Map<String,String> parametros=new HashMap<>();
                parametros.put("cedula",cedula);
                parametros.put("nombre",nombre);
                parametros.put("apellido",apellido);
                parametros.put("correo",correo);
                parametros.put("telefono",telefono);
                parametros.put("usuario",usuario);
                parametros.put("contrasena",contrasena);

                return parametros;
            }
        };
        request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getBaseContext()).addToRequestQueue(stringRequest);
    }

    public void Eliminarcliente (View view){

        String url = "http://192.168.0.5/veterinaria/wsJSONADeleteCliente.php?cedula="+txtCedula.getText().toString();

        stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.trim().equalsIgnoreCase("elimina")){
                    txtCedula.setText("");
                    txtNombre.setText("");
                    txtApellido.setText("");
                    txtContrasena.setText("");
                    txtUsuario.setText("");
                    txtCorreo.setText("");
                    txtTelefono.setText("");

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

