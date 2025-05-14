package com.example.teleweather.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teleweather.R;
import com.example.teleweather.api.RetrofitClient;
import com.example.teleweather.api.WeatherApiService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FutureFragment extends Fragment {

    private EditText etIdLocation, etFecha;
    private Button btnBuscar;
    private RecyclerView rvHoras;
    private FutureForecastAdapter adapter;

    public FutureFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_future, container, false);

        etIdLocation = view.findViewById(R.id.etIdLocation);
        etFecha = view.findViewById(R.id.etFecha);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        rvHoras = view.findViewById(R.id.rvHoras);

        rvHoras.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FutureForecastAdapter(null);
        rvHoras.setAdapter(adapter);

        // Muestra el selector de fecha al hacer clic
        etFecha.setOnClickListener(v -> mostrarDatePicker());

        btnBuscar.setOnClickListener(v -> {
            String idInput = etIdLocation.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();

            if (idInput.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                String idLoc = "id:" + idInput;  // agrega el prefijo requerido
                obtenerPronosticoFuturo(idLoc, fecha);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new androidx.activity.OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        // Cierra la actividad (vuelve a MainActivity)
                        requireActivity().finish();
                    }
                });


        return view;
    }

    private void mostrarDatePicker() {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    etFecha.setText(fechaSeleccionada);
                }, anio, mes, dia);

        datePickerDialog.show();
    }

    private void obtenerPronosticoFuturo(String idLocation, String fecha) {
        WeatherApiService apiService = RetrofitClient.getInstance().create(WeatherApiService.class);

        Call<FutureForecastModel> call = apiService.obtenerPronosticoFuturo(
                "ec24b1c6dd8a4d528c1205500250305", idLocation, fecha);

        call.enqueue(new Callback<FutureForecastModel>() {
            @Override
            public void onResponse(Call<FutureForecastModel> call, Response<FutureForecastModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FutureForecastModel model = response.body();

                    if (model.getForecast() != null &&
                            model.getForecast().getForecastday() != null &&
                            !model.getForecast().getForecastday().isEmpty() &&
                            model.getForecast().getForecastday().get(0).getHour() != null) {

                        adapter.actualizarLista(model.getForecast().getForecastday().get(0).getHour());
                    } else {
                        Toast.makeText(getContext(), "Sin datos para esta fecha", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_ERROR", "Código HTTP: " + response.code());
                    try {
                        Log.e("API_ERROR_BODY", response.errorBody().string());
                    } catch (Exception e) {
                        Log.e("API_ERROR_BODY", "Error leyendo cuerpo", e);
                    }
                    Toast.makeText(getContext(), "Error en la respuesta (" + response.code() + ")", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FutureForecastModel> call, Throwable t) {
                Log.e("FutureFragment", "Error API: " + t.getMessage());
                Toast.makeText(getContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
