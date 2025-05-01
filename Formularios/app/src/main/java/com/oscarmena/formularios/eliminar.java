package com.oscarmena.formularios;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;

public class eliminar extends AppCompatActivity {
    private EditText etDocumento, etNombreCatedra;
    private Button btnEliminarEstudiante, btnEliminarCatedra, btnVolver;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etDocumento = findViewById(R.id.etdocumento);
        etNombreCatedra = findViewById(R.id.etnombre);
        btnEliminarEstudiante = findViewById(R.id.btneliminar);
        btnEliminarCatedra = findViewById(R.id.btneliminarcatedra);
        btnVolver = findViewById(R.id.btnvolver);

        dbHandler = new DBHandler(this);

        // Acciones de los botones
        btnEliminarEstudiante.setOnClickListener(v -> eliminarEstudiante());
        btnEliminarCatedra.setOnClickListener(v -> eliminarCatedra());
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para eliminar un estudiante
    private void eliminarEstudiante() {
        String documento = etDocumento.getText().toString().trim();
        if (!documento.isEmpty()) {
            boolean eliminado = dbHandler.eliminarEstudiante(documento);
            if (eliminado) {
                Toast.makeText(this, "Estudiante eliminado con éxito", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "No se encontró el estudiante", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese un documento", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para eliminar una cátedra
    private void eliminarCatedra() {
        String documento = etDocumento.getText().toString().trim();
        String nombreCatedra = etNombreCatedra.getText().toString().trim();

        if (!documento.isEmpty() && !nombreCatedra.isEmpty()) {
            boolean eliminado = dbHandler.eliminarCatedra(documento, nombreCatedra);
            if (eliminado) {
                Toast.makeText(this, "Cátedra eliminada con éxito", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "No se encontró la cátedra para este estudiante", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese documento y nombre de cátedra", Toast.LENGTH_SHORT).show();
        }
    }
    private void limpiarCampos() {
        etDocumento.setText("");
        etNombreCatedra.setText("");
    }
    }
