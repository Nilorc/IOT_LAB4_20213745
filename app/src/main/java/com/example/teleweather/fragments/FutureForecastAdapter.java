package com.example.teleweather.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teleweather.R;

import java.util.List;

public class FutureForecastAdapter extends RecyclerView.Adapter<FutureForecastAdapter.ViewHolder> {

    private List<FutureForecastModel.HourData> lista;

    public FutureForecastAdapter(List<FutureForecastModel.HourData> lista) {
        this.lista = lista;
    }

    public void actualizarLista(List<FutureForecastModel.HourData> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_future, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FutureForecastModel.HourData item = lista.get(position);

        holder.tvHora.setText(item.getTime().split(" ")[1]); // extrae solo la hora
        holder.tvTemp.setText("Temp: " + item.getTempC() + "°C");
        holder.tvCondicion.setText(item.getCondition().getText());
        holder.tvHumedad.setText("Humedad: " + item.getHumidity() + "%");
        holder.tvLluvia.setText("Lluvia: " + item.getChanceOfRain() + "%");

        // Cargar ícono desde la URL (requiere Glide)
        String iconUrl = "https:" + item.getCondition().getIcon();
        Glide.with(holder.itemView.getContext())
                .load(iconUrl)
                .into(holder.ivIcono);
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHora, tvTemp, tvCondicion, tvHumedad, tvLluvia;
        ImageView ivIcono;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHora = itemView.findViewById(R.id.tvHora);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            tvCondicion = itemView.findViewById(R.id.tvCondicion);
            tvHumedad = itemView.findViewById(R.id.tvHumedad);
            tvLluvia = itemView.findViewById(R.id.tvLluvia);
            ivIcono = itemView.findViewById(R.id.ivIcono);
        }
    }
}
