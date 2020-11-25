package com.example.veterinaria.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.Modelo.ClsCita;
import com.example.veterinaria.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MiViewholder> implements View.OnClickListener {


    private Context miCtext;
    private ArrayList<ClsCita> citaList;
    private View.OnClickListener listener;
    public AdaptadorRecyclerCitasListener listenerBotonoes;


    public Adapter(Context miCtext, ArrayList<ClsCita> citaList) {
        this.miCtext = miCtext;
        this.citaList = citaList;


    }

    @NonNull
    @Override
    public MiViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_citas, null, false);
        view.setOnClickListener(this);
        return new MiViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewholder holder, int position) {
        holder.binData(citaList.get(position));
       /* ClsCita clsCita = citaList.get(position);
        holder.lblIdCita.setText(clsCita.getId_cita());
        holder.lblHora.setText(clsCita.getHora());
        holder.lblDescripcion.setText(clsCita.getDescripcion());
*/

    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return citaList.size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }

    }

    public class MiViewholder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView lblIdCitaC;
        TextView lblHoraCita;
        TextView lblDescripcionCita;
        TextView lblVeterinariaCita;
        TextView lblNombreMascotaCita;

        public MiViewholder(@NonNull View itemView) {
            super(itemView);
            lblIdCitaC = itemView.findViewById(R.id.txtId_cita);
            lblHoraCita = itemView.findViewById(R.id.txtHoraCita);
            lblDescripcionCita = itemView.findViewById(R.id.txtDescripcionCita);
            lblVeterinariaCita = itemView.findViewById(R.id.txtNombreVeterinariaCita);
            lblNombreMascotaCita = itemView.findViewById(R.id.txtnombreRegistroMascota);
        }

        void  binData(final ClsCita item){
            lblIdCitaC.setText(item.getId_cita());
            lblDescripcionCita.setText(item.getDescripcion());
            lblHoraCita.setText(item.getHora());
            lblVeterinariaCita.setText(item.getVeterinaria_fk());
            lblNombreMascotaCita.setText(item.getMascota_fk());
        }

    }

    public interface AdaptadorRecyclerCitasListener{
        void eliminarMarcador(View v, int posicion);
        void modificarMarcador(View v, int posicion);
    }
}
