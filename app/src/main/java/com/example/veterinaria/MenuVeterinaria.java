package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuVeterinaria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_veterinaria);
    }
    public void GestionarVeterinario(View view) {

        Intent intent = new Intent(this, RegistrarVeterinario.class);
        startActivity(intent);

    }
    public void GestionarServicios(View view) {

        Intent intent = new Intent(this, RegistrarServicios.class);
        startActivity(intent);

    }
    public void GestionarLocalizacion(View view) {

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    public void ListarCitas(View view) {

        Intent intent = new Intent(this, ListaCitasActivity.class);
        startActivity(intent);

    }

    public void Salir(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}