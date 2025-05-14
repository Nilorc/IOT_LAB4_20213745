package com.example.teleweather.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teleweather.R;
import com.example.teleweather.api.RetrofitClient;
import com.example.teleweather.api.WeatherApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {

    private EditText etBuscar;
    private Button btnBuscar;
    private RecyclerView rvResultados;
    private LocationAdapter adapter;

    public LocationFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        etBuscar = view.findViewById(R.id.etBuscar);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        rvResultados = view.findViewById(R.id.rvResultados);

        adapter = new LocationAdapter(new ArrayList<>(), location -> {
            Bundle bundle = new Bundle();
            bundle.putString("nombreLocacion", location.getName());

            NavController navController = Navigation.findNavController(requireView());

            NavOptions options = new NavOptions.Builder()
                    .setPopUpTo(R.id.locationFragment, true)  // limpia el stack
                    .build();

            navController.navigate(R.id.forecasterFragment, bundle, options);


        });

        rvResultados.setLayoutManager(new LinearLayoutManager(getContext()));
        rvResultados.setAdapter(adapter);

        btnBuscar.setOnClickListener(v -> {
            String query = etBuscar.getText().toString().trim();
            if (!query.isEmpty()) {
                buscarLocaciones(query);
            }
        });

        return view;
    }

    private void buscarLocaciones(String query) {
        WeatherApiService apiService = RetrofitClient.getInstance().create(WeatherApiService.class);
        Call<List<LocationModel>> call = apiService.buscarLocaciones("ec24b1c6dd8a4d528c1205500250305", query);

        call.enqueue(new Callback<List<LocationModel>>() {
            @Override
            public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                if (response.isSuccessful()) {
                    List<LocationModel> resultados = response.body();
                    adapter.actualizarLista(resultados);
                } else {
                    Toast.makeText(getContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LocationModel>> call, Throwable t) {
                Log.e("API_ERROR", "Fallo en la API: " + t.getMessage());
                Toast.makeText(getContext(), "No se pudo conectar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
