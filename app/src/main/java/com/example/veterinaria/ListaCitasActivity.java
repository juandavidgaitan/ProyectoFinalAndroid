package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.veterinaria.Modelo.ClsCita;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaCitasActivity extends AppCompatActivity {

    RadioButton r1,r2;
    EditText txtId_cita,txtHora,txtDescripcion;
    ListView list;
    RequestQueue requestQueue;

    public static ClsCita clsCita;
    ArrayList<ClsCita> lista;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_citas);
        lista = new ArrayList<>();
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        txtId_cita = findViewById(R.id.txtId_cita);
        txtHora = findViewById(R.id.txtHora);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        list=findViewById(R.id.list);




    }



    public void listar(View view){

        String url ="http://192.168.1.13/veterinaria/wsJSONConsultarListaCita.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            ClsCita cita = new ClsCita();
                            JSONObject jsonObject = response.getJSONObject(i);
                            cita.setId_cita(jsonObject.getString("id_cita"));
                            cita.setHora(jsonObject.getString("hora"));
                            cita.setDescripcion(jsonObject.getString("descripcion"));

                            lista.add(cita);
                            Toast.makeText(ListaCitasActivity.this, "" + response.toString(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(ListaCitasActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    verlista();
                } else {
                    Toast.makeText(ListaCitasActivity.this, "No se encontro nada", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListaCitasActivity.this, "este es el error " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
        requestQueue = Volley.newRequestQueue(this);

    }

    public void verlista(){
        list.setAdapter(null);
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, lista.toArray());
        list.setAdapter(adapter);
    }




    }



