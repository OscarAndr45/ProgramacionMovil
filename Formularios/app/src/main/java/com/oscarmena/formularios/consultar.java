package com.oscarmena.formularios;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class consultar extends AppCompatActivity {

    private EditText etDocumento, etNombreCatedra;
    private Button btnConsultaIdEstudiante, btnConsultaIdCatedra, btnVolver;
    private ListView lvEstudiante, lvCatedras;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        // Inicializar vistas
        etDocumento = findViewById(R.id.etDocumento);
        etNombreCatedra = findViewById(R.id.etNombreCatedra);
        btnConsultaIdEstudiante = findViewById(R.id.btnConsultaIdEstudiante);
        btnConsultaIdCatedra = findViewById(R.id.btnConsultaIdCatedra);
        btnVolver = findViewById(R.id.btnVolver);
        lvEstudiante = findViewById(R.id.lvEstudiante);
        lvCatedras = findViewById(R.id.lvCatedras);

        dbHandler = new DBHandler(this);

        // Acción para consultar estudiante por documento
        btnConsultaIdEstudiante.setOnClickListener(v -> consultarEstudiante());

        // Acción para consultar cátedra por nombre
        btnConsultaIdCatedra.setOnClickListener(v -> consultarCatedra());

        // Volver a la pantalla anterior
        btnVolver.setOnClickListener(v -> finish());
    }

    // Método para consultar estudiante
    private void consultarEstudiante() {
        String documento = etDocumento.getText().toString().trim();
        if (!documento.isEmpty()) {
            List<String> datosEstudiante = dbHandler.consultarEstudiante(documento);
            if (!datosEstudiante.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosEstudiante);
                lvEstudiante.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron estudiantes con ese documento", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese un documento", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para consultar cátedra
    private void consultarCatedra() {
        String nombre = etNombreCatedra.getText().toString().trim();
        String documento = etDocumento.getText().toString().trim(); // Asegurar que se tiene el documento

        if (!nombre.isEmpty() && !documento.isEmpty()) { // Verifica ambos valores
            List<String> datosCatedra = dbHandler.consultarCatedra(nombre, documento);

            if (!datosCatedra.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosCatedra);
                lvCatedras.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron cátedras con ese nombre para este estudiante", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese el nombre de la cátedra y el documento del estudiante", Toast.LENGTH_SHORT).show();
        }
    }
}