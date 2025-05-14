package com.example.teleweather;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teleweather.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!hayConexion()) {
            mostrarDialogoSinInternet();
        }

        binding.btnIngresar.setOnClickListener(view -> {
            if (hayConexion()) {
                Intent intent = new Intent(MainActivity.this, AppActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Sin conexión a internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean hayConexion() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNet = cm.getActiveNetworkInfo();
        return activeNet != null && activeNet.isConnected();
    }

    private void mostrarDialogoSinInternet() {
        new AlertDialog.Builder(this)
                .setTitle("Sin conexión")
                .setMessage("Conéctate a Internet para continuar.")
                .setCancelable(false)
                .setPositiveButton("Configuración", (dialog, which) -> {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
