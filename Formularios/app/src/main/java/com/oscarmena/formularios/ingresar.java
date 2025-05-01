package com.oscarmena.formularios;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class ingresar extends AppCompatActivity {
    private EditText etNombres, etApellidos, etDocumento, etCorreo;
    private EditText etNombreCatedra, etHorario;
    private Button btnGuardarEstudiante, btnGuardarCatedra, btnVolver;
    private DBHandler dbhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbhandler = new DBHandler(this);


        etNombres = findViewById(R.id.etnombres);
        etApellidos = findViewById(R.id.etapellidos);
        etDocumento = findViewById(R.id.etdocumento);
        etCorreo = findViewById(R.id.etcorreo);
        etNombreCatedra = findViewById(R.id.etnombrecatedra);
        etHorario = findViewById(R.id.ethorario);
        btnGuardarEstudiante = findViewById(R.id.btnguardar);
        btnGuardarCatedra = findViewById(R.id.btnguardarcatedra);
        btnVolver = findViewById(R.id.btnvolver);

        // Listeners para los botones
        btnGuardarEstudiante.setOnClickListener(v -> guardarEstudiante());
        btnGuardarCatedra.setOnClickListener(v -> guardarCatedra());
        btnVolver.setOnClickListener(v -> volver());
    }

    private void guardarEstudiante() {
        String nombres = etNombres.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String documento = etDocumento.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();

        if (nombres.isEmpty() || apellidos.isEmpty() || documento.isEmpty() || correo.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        long id = dbhandler.ingresarEstudiante(nombres, apellidos, documento, correo);
        if (id != -1) {
            Toast.makeText(this, "Estudiante guardado con ID: " + id, Toast.LENGTH_SHORT).show();
            limpiarCamposEstudiante();
        } else {
            Toast.makeText(this, "Error al guardar estudiante", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarCatedra() {
        String nombre = etNombreCatedra.getText().toString().trim();
        String horario = etHorario.getText().toString().trim();
        String documento = etDocumento.getText().toString().trim(); // El documento es la clave

        if (nombre.isEmpty() || horario.isEmpty() || documento.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el documento del estudiante existe
        List<String> estudiante = dbhandler.consultarEstudiante(documento);
        if (estudiante.isEmpty()) {
            Toast.makeText(this, "El estudiante no existe", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardamos la cátedra con el documento como clave
        long id = dbhandler.ingresarCatedra(nombre, horario, documento);

        if (id != -1) {
            Toast.makeText(this, "Cátedra guardada para el estudiante con documento: " + documento, Toast.LENGTH_SHORT).show();
            limpiarCamposCatedra();
        } else {
            Toast.makeText(this, "Error al guardar cátedra", Toast.LENGTH_SHORT).show();
        }
    }
    private void limpiarCamposEstudiante() {
        etNombres.setText("");
        etApellidos.setText("");
        etCorreo.setText("");
    }

    private void limpiarCamposCatedra() {
        etNombreCatedra.setText("");
        etHorario.setText("");
        etDocumento.setText("");
    }
    private void volver() {
        finish();
    }
}
