package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);
    }
    public void GestionarCita(View view) {

        Intent intent = new Intent(this, RegistrarCita.class);
        startActivity(intent);

    }

    public void RegistarMascota(View view) {

        Intent intent = new Intent(this, RegistrarMascota.class);
        startActivity(intent);

    }
    public void Actualizardatos(View view) {

        Intent intent = new Intent(this, BuscarCliente.class);
        startActivity(intent);

    }

    public void ListarVete(View view) {

        Intent intent = new Intent(this, ListaMarcadoresActivity.class);
        startActivity(intent);

    }

    public void Salir(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}