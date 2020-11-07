
package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarVeterinario extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {

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

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

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

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarVete("http://192.168.1.13/veterinaria/BuscarVeterinaria.php?nit_veterinaria=" + txtNit_Veterinaria.getText()+"");
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
    public void RegistrarVeterinarios(View view) {
        String url = "http://192.168.1.13/veterinaria/wsJSONRegistroVeterinario.php?cedula=" + txtCedula.getText().toString() +
                "&apellido=" + txtApellidos.getText().toString() + "&nombre=" + txtNombreVete.getText().toString() +
                "&edad=" + txtEdad.getText().toString() + "&correo=" + txtCorreoVete.getText().toString() + "&salario=" + txtSalario.getText().toString() + "&veterinaria_fk=" + txtNit_Veterinaria.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);




    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "OPERACION ERRONEA" + error.toString(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResponse(JSONObject response) {

        txtVeterinaria.setText("");
        txtNit_Veterinaria.setText("");
        txtCorreoVete.setText("");
        txtCedula.setText("");
        txtNombreVete.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtSalario.setText("");

        Toast.makeText(getApplicationContext(), "OPERACION EXITOSAMENTE", Toast.LENGTH_SHORT).show();


    }

}