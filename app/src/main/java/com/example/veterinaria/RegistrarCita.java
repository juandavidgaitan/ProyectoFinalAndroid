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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.veterinaria.Controller.CtlBuscarVeterinario;
import com.example.veterinaria.Modelo.ClsMascota;
import com.example.veterinaria.Modelo.ClsVeterinaria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarCita  extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    /*veterinaria*/
    EditText txtNit_Veterinaria;
    EditText txtVeterinaria;

    /*mascota*/
    EditText txtMascotaid;
    EditText txtNombre;
    /*Cita*/
    EditText txtDescripcion;
    EditText txtHora;
    Button btnBuscarVeterinaria;
    Button btnBuscarMascota;

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
        btnBuscarVeterinaria=(Button) findViewById(R.id.btnBuscarVeterinaria);
        btnBuscarMascota=(Button) findViewById(R.id.BuscarMascota);


        request = Volley.newRequestQueue(getBaseContext());

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

    public void RegistrarCita(View view) {
        String url = "http://192.168.1.13/veterinaria/wsJSONRegistroCita.php?mascota_fk=" + txtMascotaid.getText().toString() +
                "&veterinaria_fk=" + txtNit_Veterinaria.getText().toString() + "&hora=" + txtHora.getText().toString() +
                "&descripcion=" + txtDescripcion.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "OPERACION ERRONEA" + error.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {

        txtMascotaid.setText("");
        txtDescripcion.setText("");
        txtNit_Veterinaria.setText("");
        txtHora.setText("");
        txtNombre.setText("");
        txtVeterinaria.setText("");

        Toast.makeText(getApplicationContext(), "OPERACION EXITOSAMENTE", Toast.LENGTH_SHORT).show();

    }
}
