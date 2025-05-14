package com.example.teleweather.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teleweather.R;
import com.example.teleweather.api.RetrofitClient;
import com.example.teleweather.api.WeatherApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecasterFragment extends Fragment implements SensorEventListener {

    private RecyclerView rvPronostico;
    private ForecastAdapter adapter;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean shakeDetected = false;
    private static final float SHAKE_THRESHOLD = 20.0f;

    public ForecasterFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecaster, container, false);

        EditText etLocacion = view.findViewById(R.id.etLocacion);
        EditText etDias = view.findViewById(R.id.etDias);
        Button btnBuscar = view.findViewById(R.id.btnBuscar);
        rvPronostico = view.findViewById(R.id.rvPronostico);

        rvPronostico.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ForecastAdapter(null);
        rvPronostico.setAdapter(adapter);

        // Configurar sensor
        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        // Navegación por argumento
        Bundle args = getArguments();
        if (args != null && args.getString("nombreLocacion") != null) {
            String nombreLocacion = args.getString("nombreLocacion");
            obtenerPronostico(nombreLocacion, 14);
        } else {
            etLocacion.setVisibility(View.VISIBLE);
            etDias.setVisibility(View.VISIBLE);
            btnBuscar.setVisibility(View.VISIBLE);

            btnBuscar.setOnClickListener(v -> {
                String idInput = etLocacion.getText().toString().trim();
                String strDias = etDias.getText().toString().trim();

                if (!idInput.isEmpty() && !strDias.isEmpty()) {
                    try {
                        int dias = Integer.parseInt(strDias);
                        if (dias >= 1 && dias <= 14) {
                            String idLoc = "id:" + idInput;
                            obtenerPronostico(idLoc, dias);
                        } else {
                            etDias.setError("Máximo 14 días");
                        }
                    } catch (NumberFormatException e) {
                        etDias.setError("Número inválido");
                    }
                } else {
                    if (idInput.isEmpty()) etLocacion.setError("Ingrese ID");
                    if (strDias.isEmpty()) etDias.setError("Ingrese días");
                }
            });
        }

        // boton para regresar
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });

        return view;
    }

    private void obtenerPronostico(String lugar, int dias) {
        WeatherApiService apiService = RetrofitClient.getInstance().create(WeatherApiService.class);
        Call<ForecastModel> call = apiService.obtenerPronostico("ec24b1c6dd8a4d528c1205500250305", lugar, dias);

        call.enqueue(new Callback<ForecastModel>() {
            @Override
            public void onResponse(Call<ForecastModel> call, Response<ForecastModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.actualizarLista(response.body().getForecast().getForecastday());
                } else {
                    Log.e("Forecaster", "Respuesta no exitosa");
                }
            }

            @Override
            public void onFailure(Call<ForecastModel> call, Throwable t) {
                Log.e("Forecaster", "Error al obtener pronóstico: " + t.getMessage());
            }
        });
    }

    // === SENSOR DE AGITACIÓN ===
    @Override
    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            double acceleration = Math.sqrt(x * x + y * y + z * z);

            if (acceleration > SHAKE_THRESHOLD && !shakeDetected && adapter != null && adapter.getItemCount() > 0) {
                shakeDetected = true;
                Toast.makeText(getContext(), "¡Agitación detectada!", Toast.LENGTH_SHORT).show();
                mostrarDialogoConfirmacion();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se usa
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Deshacer acción")
                .setMessage("¿Deseas eliminar los pronósticos mostrados?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    adapter.actualizarLista(null);
                    shakeDetected = false;
                })
                .setNegativeButton("Cancelar", (dialog, which) -> shakeDetected = false)
                .setOnCancelListener(dialog -> shakeDetected = false)
                .show();
    }
}
