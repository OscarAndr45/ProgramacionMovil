package com.oscarmena.formularioskotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class modificar : AppCompatActivity() {

    private lateinit var etDocumento: EditText
    private lateinit var etNombreCatedra: EditText
    private lateinit var etHorario: EditText
    private lateinit var btnActualizar: Button
    private lateinit var btnVolver: Button
    private lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_modificar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        etDocumento = findViewById(R.id.etdocumento)
        etNombreCatedra = findViewById(R.id.etNombreCatedra)
        etHorario = findViewById(R.id.ethorario)
        btnActualizar = findViewById(R.id.btnactualizar)
        btnVolver = findViewById(R.id.btnvolver)

        dbHandler = DBHandler(this)

        // Acciones de los botones
        btnActualizar.setOnClickListener { actualizarCatedraEstudiante() }
        btnVolver.setOnClickListener { finish() }
    }

    private fun actualizarCatedraEstudiante() {
        val documento = etDocumento.text.toString().trim()
        val nombreCatedra = etNombreCatedra.text.toString().trim()
        val horario = etHorario.text.toString().trim()

        if (documento.isEmpty() || nombreCatedra.isEmpty() || horario.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificar si el estudiante existe
        if (dbHandler.existeEstudiante(documento)) {
            // Actualizar o insertar la cátedra para este estudiante
            val resultado = dbHandler.actualizarCatedraEstudiante(documento, nombreCatedra, horario)

            if (resultado) {
                Toast.makeText(this, "Cátedra actualizada con éxito para el estudiante", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Error al actualizar la cátedra", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "El estudiante con documento $documento no existe", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        etDocumento.setText("")
        etNombreCatedra.setText("")
        etHorario.setText("")
        etDocumento.requestFocus()
    }
}
