package com.oscarmena.formularioskotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Ingresar : AppCompatActivity() {
    private lateinit var etNombres: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etDocumento: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etNombreCatedra: EditText
    private lateinit var etHorario: EditText
    private lateinit var btnGuardarEstudiante: Button
    private lateinit var btnGuardarCatedra: Button
    private lateinit var btnVolver: Button
    private lateinit var dbhandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbhandler = DBHandler(this)

        etNombres = findViewById(R.id.etnombres)
        etApellidos = findViewById(R.id.etapellidos)
        etDocumento = findViewById(R.id.etdocumento)
        etCorreo = findViewById(R.id.etcorreo)
        etNombreCatedra = findViewById(R.id.etnombrecatedra)
        etHorario = findViewById(R.id.ethorario)
        btnGuardarEstudiante = findViewById(R.id.btnguardar)
        btnGuardarCatedra = findViewById(R.id.btnguardarcatedra)
        btnVolver = findViewById(R.id.btnvolver)

        btnGuardarEstudiante.setOnClickListener { guardarEstudiante() }
        btnGuardarCatedra.setOnClickListener { guardarCatedra() }
        btnVolver.setOnClickListener { volver() }
    }

    private fun guardarEstudiante() {
        val nombres = etNombres.text.toString().trim()
        val apellidos = etApellidos.text.toString().trim()
        val documento = etDocumento.text.toString().trim()
        val correo = etCorreo.text.toString().trim()

        if (nombres.isEmpty() || apellidos.isEmpty() || documento.isEmpty() || correo.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val id = dbhandler.ingresarEstudiante(nombres, apellidos, documento, correo)
        if (id != -1L) {
            Toast.makeText(this, "Estudiante guardado con ID: $id", Toast.LENGTH_SHORT).show()
            limpiarCamposEstudiante()
        } else {
            Toast.makeText(this, "Error al guardar estudiante", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarCatedra() {
        val nombre = etNombreCatedra.text.toString().trim()
        val horario = etHorario.text.toString().trim()
        val documento = etDocumento.text.toString().trim()

        if (nombre.isEmpty() || horario.isEmpty() || documento.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val estudiante = dbhandler.consultarEstudiante(documento)
        if (estudiante.isEmpty()) {
            Toast.makeText(this, "El estudiante no existe", Toast.LENGTH_SHORT).show()
            return
        }

        val id = dbhandler.ingresarCatedra(nombre, horario, documento)
        if (id != -1L) {
            Toast.makeText(this, "Cátedra guardada para el estudiante con documento: $documento", Toast.LENGTH_SHORT).show()
            limpiarCamposCatedra()
        } else {
            Toast.makeText(this, "Error al guardar cátedra", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCamposEstudiante() {
        etNombres.setText("")
        etApellidos.setText("")
        etCorreo.setText("")
    }

    private fun limpiarCamposCatedra() {
        etNombreCatedra.setText("")
        etHorario.setText("")
        etDocumento.setText("")
    }

    private fun volver() {
        finish()
    }
}
