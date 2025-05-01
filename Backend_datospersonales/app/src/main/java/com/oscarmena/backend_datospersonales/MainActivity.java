package com.oscarmena.backend_datospersonales;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnConsultar;
    private TextView tvIdentificacion, tvDocumento, tvNombres, tvApellidos, tvFechaNacimiento, tvEmail, tvDireccion;

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

        // Inicializar vistas
        imageView = findViewById(R.id.imageView);
        btnConsultar = findViewById(R.id.btnConsultar);
        tvIdentificacion = findViewById(R.id.tvIdentificacion);
        tvDocumento = findViewById(R.id.tvDocumento);
        tvNombres = findViewById(R.id.tvNombres);
        tvApellidos = findViewById(R.id.tvApellidos);
        tvFechaNacimiento = findViewById(R.id.tvFechaNacimiento);
        tvEmail = findViewById(R.id.tvEmail);
        tvDireccion = findViewById(R.id.tvDireccion);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8067/") // <-- aquí cambia la base URL correcta
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuarioApiService usuarioApiService = retrofit.create(UsuarioApiService.class);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<UsuarioResponse> call = usuarioApiService.getUsuario();
                call.enqueue(new Callback<UsuarioResponse>() {
                    @Override
                    public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UsuarioResponse usuario = response.body();

                            tvIdentificacion.setText("Identificación: " + usuario.getIdentificacion());
                            tvDocumento.setText("Documento: " + usuario.getDocumento());
                            tvNombres.setText("Nombres: " + usuario.getNombres());
                            tvApellidos.setText("Apellidos: " + usuario.getApellidos());
                            tvFechaNacimiento.setText("Fecha Nacimiento: " + usuario.getFechaNacimiento());
                            tvEmail.setText("Email: " + usuario.getEmail());
                            tvDireccion.setText("Dirección: " + usuario.getDireccion());

                            Picasso.get().load(usuario.getImagen()).into(imageView);
                        } else {
                            Toast.makeText(MainActivity.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}