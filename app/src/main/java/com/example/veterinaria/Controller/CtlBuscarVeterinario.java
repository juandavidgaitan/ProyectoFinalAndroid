package com.example.veterinaria.Controller;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.veterinaria.Modelo.ClsCliente;
import com.example.veterinaria.Modelo.ClsVeterinaria;
import com.example.veterinaria.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CtlBuscarVeterinario extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    EditText txtVeterianria;
    EditText txtDireccion;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtVeterianria = (EditText) findViewById(R.id.txtVeterinaria);
        txtDireccion = (EditText) findViewById(R.id.txtDireccionVe);

    }


    public void Buscar(final Context context, ClsVeterinaria ve) {
        String url = "http://192.168.1.13/veterinaria/wsJSONBuscarVeterinaria.php?nit_veterinaria=" + txtVeterianria.getText().toString();
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


        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtVeterianria.setText(cliente.getNombre());




    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "OPERACION ERRONEA 1: " + error.toString(), Toast.LENGTH_SHORT).show();
        txtVeterianria = (EditText) findViewById(R.id.txtVeterinaria);
    }
}
