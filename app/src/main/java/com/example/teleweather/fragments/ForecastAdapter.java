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

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastModel.ForecastDay> lista;

    public ForecastAdapter(List<ForecastModel.ForecastDay> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public void actualizarLista(List<ForecastModel.ForecastDay> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvCondicion, tvTemp;
        ImageView ivClima;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvCondicion = itemView.findViewById(R.id.tvCondicion);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            ivClima = itemView.findViewById(R.id.ivClima);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        ForecastModel.ForecastDay item = lista.get(position);

        holder.tvFecha.setText(item.getDate());
        holder.tvCondicion.setText(item.getDay().getCondition().getText());
        holder.tvTemp.setText("Mín: " + item.getDay().getMintempC() + "°C / Máx: " + item.getDay().getMaxtempC() + "°C");

        // Adicional Mostrar ícono dinámico según condición climática
        String condicion = item.getDay().getCondition().getText().toLowerCase();

        if (condicion.contains("sun") || condicion.contains("clear")) {
            holder.ivClima.setImageResource(R.drawable.ic_sunny);  // ☀️
        } else if (condicion.contains("cloud")) {
            holder.ivClima.setImageResource(R.drawable.ic_cloudy);  // ☁️
        } else if (condicion.contains("rain")) {
            holder.ivClima.setImageResource(R.drawable.ic_rain);  // 🌧️
        } else if (condicion.contains("snow")) {
            holder.ivClima.setImageResource(R.drawable.ic_snow);  // ❄️
        } else {
            holder.ivClima.setImageResource(R.drawable.ic_forecast);  // Desconocido
        }
    }
}
