package com.example.veterinaria.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.Modelo.ClsCita;
import com.example.veterinaria.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MiViewholder> {


    private Context miCtext;
    private List<ClsCita>citaList;

    public Adapter(Context miCtext,List<ClsCita>citaList){
        this.miCtext=miCtext;
        this.citaList=citaList;

    }

    @NonNull
    @Override
    public MiViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(miCtext);
        View view=inflater.inflate(R.layout.listar,null);
        return new MiViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewholder holder, int position) {

        ClsCita clsCita=citaList.get(position);
        holder.lblIdCita.setText(clsCita.getId_cita());
        holder.lblHora.setText(clsCita.getHora());
        holder.lblDescripcion.setText(clsCita.getDescripcion());


    }

    @Override
    public int getItemCount() {
        return citaList.size();
    }
    class MiViewholder extends  RecyclerView.ViewHolder{

        TextView lblIdCita;
        TextView lblHora;
        TextView lblDescripcion;

        public MiViewholder(@NonNull View itemView) {
            super(itemView);
            lblIdCita=itemView.findViewById(R.id.lblIdCita);
            lblHora=itemView.findViewById(R.id.lblhora);
            lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
        }
    }
}
