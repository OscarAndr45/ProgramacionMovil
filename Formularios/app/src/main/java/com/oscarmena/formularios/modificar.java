package com.oscarmena.formularios;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class modificar extends AppCompatActivity {

    private EditText etDocumento, etNombreCatedra, etHorario;
    private Button btnActualizar, btnVolver;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        etDocumento = findViewById(R.id.etdocumento);
        etNombreCatedra = findViewById(R.id.etNombreCatedra);
        etHorario = findViewById(R.id.ethorario);
        btnActualizar = findViewById(R.id.btnactualizar);
        btnVolver = findViewById(R.id.btnvolver);

        dbHandler = new DBHandler(this);

        // Acciones de los botones
        btnActualizar.setOnClickListener(v -> actualizarCatedraEstudiante());
        btnVolver.setOnClickListener(v -> finish());
    }

    private void actualizarCatedraEstudiante() {
        String documento = etDocumento.getText().toString().trim();
        String nombreCatedra = etNombreCatedra.getText().toString().trim();
        String horario = etHorario.getText().toString().trim();

        if (documento.isEmpty() || nombreCatedra.isEmpty() || horario.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el estudiante existe
        if (dbHandler.existeEstudiante(documento)) {
            // Actualizar o insertar la cátedra para este estudiante
            boolean resultado = dbHandler.actualizarCatedraEstudiante(documento, nombreCatedra, horario);

            if (resultado) {
                Toast.makeText(this, "Cátedra actualizada con éxito para el estudiante", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al actualizar la cátedra", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "El estudiante con documento " + documento + " no existe", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etDocumento.setText("");
        etNombreCatedra.setText("");
        etHorario.setText("");
        etDocumento.requestFocus();
    }
}