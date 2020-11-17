package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.veterinaria.Controller.CtlMarcador;
import com.example.veterinaria.Modelo.ClsCliente;
import com.example.veterinaria.Modelo.Marcador;

import java.util.List;

public class MostrarVeterinariaActivity extends AppCompatActivity {

    List<Marcador> listaMarcadores;
    private List<String> listaPuntosUsuarioString;
    RecyclerView recyclerView;
    Bundle bundle;
    ClsCliente userActivo;
    CtlMarcador ctlMarcador;

    boolean marcadorActivo = false, recibioDatos = false;
    private TextView lblNombres;
    private TextView lblDescripciones;
    private TextView lblColores;
    CtlMarcador gestionMarcador;
    Marcador recibir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_veterinaria);
        recyclerView = findViewById(R.id.recyclerMarcadores);

        lblNombres = (TextView) findViewById(R.id.lblNombreVete);
        lblDescripciones = (TextView) findViewById(R.id.lblDescripcionVete);
        lblColores = (TextView) findViewById(R.id.lblColorVet);

        gestionMarcador = new CtlMarcador(this);
        recibir = gestionMarcador.getMarcador();





    }
    public  void Regresar(View view){
        Intent intent = new Intent(this, ListaMarcadoresActivity.class);
        startActivity(intent);
    }

}