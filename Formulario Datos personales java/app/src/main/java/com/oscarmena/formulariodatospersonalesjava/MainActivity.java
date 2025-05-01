package com.oscarmena.formulariodatospersonalesjava;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etNombres;
    private EditText etApellidos;
    private EditText etDocumentos;
    private EditText etEdad;
    private Button btAceptar;
    private TextView tvDatos;

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
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etDocumentos = findViewById(R.id.etDocumentos);
        etEdad = findViewById(R.id.etEdad);
        btAceptar = findViewById(R.id.btAceptar);
        tvDatos = findViewById(R.id.tvDatos);

        btAceptar.setOnClickListener(v -> mostrarDatos());
    }

    private void mostrarDatos() {
        String nombre = etNombres.getText().toString();
        String apellidos = etApellidos.getText().toString();
        String documento = etDocumentos.getText().toString();
        String edad = etEdad.getText().toString();

        if (nombre.isEmpty()|| apellidos.isEmpty()|| documento.isEmpty() || edad.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese los datos", Toast.LENGTH_SHORT).show();
        } else {
            String datos = "Nombres: " + nombre + "\nApellidos: " + apellidos + "\nDocumentos: " + documento + "\nEdad: " + edad;
            tvDatos.setText(datos);
            Toast.makeText(this, "Datos mostrados correctamente", Toast.LENGTH_SHORT).show();
        }
    }
}
