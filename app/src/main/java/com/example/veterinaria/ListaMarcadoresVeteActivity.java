package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.veterinaria.Controller.CtlMarcador;
import com.example.veterinaria.Modelo.ClsCliente;
import com.example.veterinaria.Modelo.Marcador;
import com.example.veterinaria.adaptadores.AdaptadorRecyclerMarcador;

import java.util.List;

public class ListaMarcadoresVeteActivity extends AppCompatActivity {


    List<Marcador> listaMarcadores;
    private List<String> listaPuntosUsuarioString;
    RecyclerView recyclerView;
    Bundle bundle;
    ClsCliente userActivo;
    CtlMarcador ctlMarca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_marcadores);
        recyclerView = findViewById(R.id.recyclerMarcadores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ctlMarca = new CtlMarcador(this);
        listaPuntosUsuarioString = ctlMarca.listarPuntosUsuarioString();

        bundle = getIntent().getExtras();
//    userActivo = (ClsCliente) bundle.getSerializable("userActivo");
        listar();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void imprimir(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        //    intent.putExtra("userActivo", (Parcelable) userActivo);
        //   intent.putExtra("marcadorActivo", false);
        startActivity(intent);
        finish();
    }

    private void listar() {
        listaMarcadores = ctlMarca.listarPuntosUsuario();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaPuntosUsuarioString);

        final AdaptadorRecyclerMarcador adaptador = new AdaptadorRecyclerMarcador(listaMarcadores, getApplicationContext(), new AdaptadorRecyclerMarcador.AdaptadorRecyclerMarcadorListener() {


            @Override
            public void eliminarMarcador(View v, int posicion) {
                try {
                    imprimir("" + posicion);

                    listaMarcadores.remove(posicion);

                } catch (Exception e) {
                    imprimir(e.getMessage());
                }
            }

            @Override
            public void modificarMarcador(View v, int posicion) {
                Intent i = new Intent(getApplicationContext(), MarcadorActivity.class);
                i.putExtra("estadoAccion", true);

                i.putExtra("modificar", listaMarcadores.get(posicion));
                startActivity(i);
                finish();
            }
        });
        recyclerView.setAdapter(adaptador);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Marcador marcador = listaMarcadores.get(recyclerView.getChildAdapterPosition(v));
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("latitud", marcador.getLatitud());
                i.putExtra("longitud", marcador.getLongitud());

                //        i.putExtra("marcadorActivo", true);
                startActivity(i);
                finish();
            }
        });


    }
    public  void Regresar(View view){
        Intent intent = new Intent(this, MenuVeterinaria.class);
        startActivity(intent);
    }

}