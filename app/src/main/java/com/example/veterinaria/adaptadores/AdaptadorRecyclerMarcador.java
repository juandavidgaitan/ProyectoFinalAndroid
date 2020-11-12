package com.example.veterinaria.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.Modelo.Marcador;
import com.example.veterinaria.R;



import java.util.List;


public class AdaptadorRecyclerMarcador extends RecyclerView.Adapter<AdaptadorRecyclerMarcador.MarcadoresViewHolder> implements View.OnClickListener {

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_marcador, null, false);
        view.setOnClickListener(this);
        return new MarcadoresViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MarcadoresViewHolder holder, int position) {
        holder.lblNombre.setText(listaMarcadores.get(position).getNombre());
        holder.lblDescripcion.setText(listaMarcadores.get(position).getDescripcion());
        holder.lblColor.setText(listaMarcadores.get(position).getColor());
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
        //CircleImageView btnEliminar, btnEditar;
        TextView lblNombre, lblDescripcion, lblColor;

        public MarcadoresViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNombre = itemView.findViewById(R.id.lblNombreMarcador);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcionMarcador);
            lblColor = itemView.findViewById(R.id.lblColorMarcador);
         //   btnEliminar = itemView.findViewById(R.id.btnEliminarMarcador);
         //   btnEditar = itemView.findViewById(R.id.btnEditarMarcador);

         /*  btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerBotones.eliminarMarcador(v, getAdapterPosition());
                    listaMarcadores.remove(getAdapterPosition());
                    notifyItemRemoved(getLayoutPosition());
                    notifyItemRangeChanged(getAdapterPosition(), 1);
                    notifyDataSetChanged();
                }
            });

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerBotones.modificarMarcador(v, getAdapterPosition());
                }
            });*/
        }
    }

    public interface AdaptadorRecyclerMarcadorListener{
        void eliminarMarcador(View v, int posicion);
        void modificarMarcador(View v, int posicion);
    }

}
