package com.oscarmena.socceropenapi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText edtStadiumName;
    private Button btnSearch;
    private TextView tvName, tvCity, tvAddress, tvCapacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialización de vistas
        edtStadiumName = findViewById(R.id.edtStadiumName);
        btnSearch = findViewById(R.id.btnSearch);
        tvName = findViewById(R.id.tvName);
        tvCity = findViewById(R.id.tvCity);
        tvAddress = findViewById(R.id.tvAddress);
        tvCapacity = findViewById(R.id.tvCapacity);

        // Configuración de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.sportmonks.com/v3/football/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SoccerApiService soccerApiService = retrofit.create(SoccerApiService.class);

        // Configuración del botón de búsqueda
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stadiumName = edtStadiumName.getText().toString().trim();

                if (stadiumName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, ingrese un nombre de estadio", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mostrar indicador de carga
                showLoading(true);

                // Llamada a la API para buscar el estadio por nombre
                String apiToken = "6vb94XlnTA8Hm8wAEOupJL87C40YjJkOtyJRIGioIhm8Oc6EKkdJSrdm83wH";

                Call<VenuesResponse> call = soccerApiService.searchVenues(apiToken, stadiumName);
                call.enqueue(new Callback<VenuesResponse>() {
                    @Override
                    public void onResponse(Call<VenuesResponse> call, Response<VenuesResponse> response) {
                        showLoading(false);

                        if (response.isSuccessful() && response.body() != null) {
                            List<VenuesResponse.Venue> venues = response.body().getData();

                            if (venues != null && !venues.isEmpty()) {
                                boolean found = false;
                                String stadiumName = edtStadiumName.getText().toString().trim().toLowerCase();

                                for (VenuesResponse.Venue venue : venues) {
                                    if (venue.getName().toLowerCase().contains(stadiumName)) {
                                        displayVenueInfo(venue);
                                        found = true;
                                        break;
                                    }
                                }

                                if (!found) {
                                    Toast.makeText(MainActivity.this, "No se encontró un estadio exacto con ese nombre", Toast.LENGTH_SHORT).show();
                                    clearVenueInfo();
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "No se encontraron estadios", Toast.LENGTH_SHORT).show();
                                clearVenueInfo();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                            clearVenueInfo();
                        }
                    }
                    @Override
                    public void onFailure(Call<VenuesResponse> call, Throwable t) {
                        showLoading(false);
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        clearVenueInfo();
                    }
                });
            }
        });
    }

    private void displayVenueInfo(VenuesResponse.Venue venue) {
        tvName.setText("Nombre: " + venue.getName());
        tvCity.setText("Ciudad: " + venue.getCity());
        tvAddress.setText("Dirección: " + venue.getAddress());
        tvCapacity.setText("Capacidad: " + venue.getCapacity());

        // Hacer visibles los TextView
        tvName.setVisibility(View.VISIBLE);
        tvCity.setVisibility(View.VISIBLE);
        tvAddress.setVisibility(View.VISIBLE);
        tvCapacity.setVisibility(View.VISIBLE);
    }

    private void clearVenueInfo() {
        tvName.setText("");
        tvCity.setText("");
        tvAddress.setText("");
        tvCapacity.setText("");
    }

    private void showLoading(boolean isLoading) {
        // Aquí puedes implementar un ProgressBar si lo deseas
        if (isLoading) {
            btnSearch.setEnabled(false);
        } else {
            btnSearch.setEnabled(true);
        }
    }
}