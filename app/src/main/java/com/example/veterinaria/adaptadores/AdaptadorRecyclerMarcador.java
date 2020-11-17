package com.example.veterinaria.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.Modelo.Marcador;
import com.example.veterinaria.R;



import java.util.List;


public class AdaptadorRecyclerMarcador extends RecyclerView.Adapter<AdaptadorRecyclerMarcador.MarcadoresViewHolder> implements View.OnClickListener {
    private List<String> listaPuntosUsuarioString;
    public List<Marcador> listaMarcadores;
    private View.OnClickListener listener;
    public Context context;
    public AdaptadorRecyclerMarcadorListener listenerBotones;

    public AdaptadorRecyclerMarcador(List<Marcador> listaMarcadores, Context context, AdaptadorRecyclerMarcadorListener listenerBotones) {
        this.listaMarcadores = listaMarcadores;
        this.context = context;
        this.listenerBotones = listenerBotones;
    }

    @NonNull
    @Override
    public MarcadoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, null, false);
        view.setOnClickListener(this);
        return new MarcadoresViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MarcadoresViewHolder holder, int position) {
   holder.binData(listaMarcadores.get(position));

      /*  holder.lblNombre.setText(listaMarcadores.get(position).getNombre());
        holder.lblDescripcion.setText(listaMarcadores.get(position).getDescripcion());
        holder.lblColor.setText(listaMarcadores.get(position).getColor());
    */
    }


    @Override
    public int getItemCount() {
        return listaMarcadores.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }


    public class MarcadoresViewHolder extends RecyclerView.ViewHolder {
       ImageView iconImage;
        TextView lblNombre, lblDescripcion, lblColor;

        public MarcadoresViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            lblNombre = itemView.findViewById(R.id.lblNombreMarcador);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcionMarcador);
            lblColor = itemView.findViewById(R.id.lblColorMarcador);

        }

        void binData(final Marcador item){
         //   iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            lblNombre.setText(item.getNombre());
            lblColor.setText(item.getColor());
            lblDescripcion.setText(item.getDescripcion());
        }
    }

    public interface AdaptadorRecyclerMarcadorListener{
        void eliminarMarcador(View v, int posicion);
        void modificarMarcador(View v, int posicion);
    }

}
