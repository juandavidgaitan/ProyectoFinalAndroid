package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.veterinaria.Controller.CtlCita;
import com.example.veterinaria.Modelo.ClsCita;
import com.example.veterinaria.Modelo.Marcador;
import com.example.veterinaria.Modelo.VolleySingleton;
import com.example.veterinaria.adaptadores.AdaptadorRecyclerMarcador;
import com.example.veterinaria.adaptadores.Adapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaCitasActivity extends AppCompatActivity {


    TextView txtId_citaCita, txtHoraCita, txtDescripcionCita;
    RequestQueue requestQueue;
    RecyclerView recyclerView;

    public static ClsCita clsCita;
    ArrayList<ClsCita> listaCitas;
    private Button borrar;
    StringRequest stringRequest;
    RequestQueue request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_citas);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerCitas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        txtId_citaCita = (TextView) findViewById(R.id.txtId_cita);
        txtHoraCita = (TextView) findViewById(R.id.txtHora);
        txtDescripcionCita = (TextView) findViewById(R.id.txtDescripcion);
        borrar = findViewById(R.id.btnBorrar);
        listar();
        listaCitas = new ArrayList<>();


    }


    public void listar() {

        String url = "http://192.168.1.13/veterinaria/listarOmar.php";
        System.out.println("entro");

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("entro2");
                if (response != null) {
                    List<String> listaCitasString = new ArrayList<>();
                    ArrayList<ClsCita> listaCitas = new ArrayList<>();
                    try {
                        JSONObject jsonObject = null;
                        System.out.println("entro al objeto");
                        for (int i = 0; i < response.length(); i++) {


                            if (response.getJSONObject(i) != null) {
                                jsonObject = response.getJSONObject(i);
                                try {
                                    System.out.println("entro4");
                                    ClsCita cita = new ClsCita();

                                    cita.setId_cita(jsonObject.getString("id_cita"));
                                    cita.setHora(jsonObject.getString("hora"));
                                    cita.setDescripcion(jsonObject.getString("descripcion"));

                                    listaCitas.add(cita);
                                    listaCitasString.add("id_cita" + jsonObject.getString("id_cita") + " hora " +
                                            jsonObject.getString("hora") + "descripcion" +
                                            jsonObject.getString("descripcion"));


                                    /* Toast.makeText(ListaCitasActivity.this, response.toString(), Toast.LENGTH_SHORT).show();*/

                                } catch (JSONException e) {
                                    Toast.makeText(ListaCitasActivity.this, "el error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(ListaCitasActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }


                        }
                        System.out.println("entro5");

                        verlista(listaCitas);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }

                } else {
                    Toast.makeText(ListaCitasActivity.this, "No se encontro nada", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("entr6");
                Toast.makeText(ListaCitasActivity.this, "el error " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println("entr7");
        requestQueue.add(jsonArrayRequest);
        System.out.println("entr8");
        requestQueue = Volley.newRequestQueue(this);

    }


    public void verlista(final ArrayList<ClsCita> listaCitas) {
        System.out.println("entr9");
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, listaCitas.toArray());
        System.out.println("entr10");
        Adapter adaptador = new Adapter(getApplicationContext(), listaCitas) {


        };
        recyclerView.setAdapter(new Adapter(this.getApplicationContext(), listaCitas));
        System.out.println("entr11");
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("entr12");
                    Intent i = new Intent(getApplicationContext(), ListaCitasActivity.class);
                    ClsCita cita = listaCitas.get(recyclerView.getChildAdapterPosition(v));

                    i.putExtra("nombre", cita.getVeterinaria_fk());
                    i.putExtra("veterinaria_fk", cita.getVeterinaria_fk());
                    i.putExtra("mascota_fk", cita.getMascota_fk());
                    //        i.putExtra("marcadorActivo", true);
                    startActivity(i);
                    System.out.println("entr13");
                    finish();
                    System.out.println("entr14");
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }
        });

    }

    public void Regresar(View view) {
        Intent intent = new Intent(this, MenuVeterinaria.class);
        startActivity(intent);
    }



    public void borrar (View view){

        String url = "http://192.168.1.13/veterinaria/wsJSONADeleteCita.php?id_cita="+txtId_citaCita.getText().toString();

        stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.trim().equalsIgnoreCase("elimina")){
                    txtId_citaCita.setText("");
                    txtHoraCita.setText("");
                    txtDescripcionCita.setText("");



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