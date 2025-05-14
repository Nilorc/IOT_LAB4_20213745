package com.example.teleweather.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teleweather.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(LocationModel location);
    }

    private List<LocationModel> lista;
    private OnItemClickListener listener;

    public LocationAdapter(List<LocationModel> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationModel item = lista.get(position);
        holder.tvNombre.setText(item.getName());
        holder.tvId.setText("ID: " + item.getId());
        holder.tvRegion.setText(item.getRegion());
        holder.tvPais.setText(item.getCountry());

        // ícono fijo de ubicación
        holder.ivIcono.setImageResource(R.drawable.ic_location_pin);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void actualizarLista(List<LocationModel> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvId, tvRegion, tvPais;
        ImageView ivIcono;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvId = itemView.findViewById(R.id.tvId);
            tvRegion = itemView.findViewById(R.id.tvRegion);
            tvPais = itemView.findViewById(R.id.tvPais);
            ivIcono = itemView.findViewById(R.id.ivIcono);
        }
    }
}
