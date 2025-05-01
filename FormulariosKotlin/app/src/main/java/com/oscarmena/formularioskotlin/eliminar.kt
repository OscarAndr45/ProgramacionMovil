package com.oscarmena.formularioskotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class eliminar : AppCompatActivity() {
    private lateinit var etDocumento: EditText
    private lateinit var etNombreCatedra: EditText
    private lateinit var btnEliminarEstudiante: Button
    private lateinit var btnEliminarCatedra: Button
    private lateinit var btnVolver: Button
    private lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_eliminar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etDocumento = findViewById(R.id.etdocumento)

        etNombreCatedra = findViewById(R.id.etnombre)
        btnEliminarEstudiante = findViewById(R.id.btneliminar)
        btnEliminarCatedra = findViewById(R.id.btneliminarcatedra)
        btnVolver = findViewById(R.id.btnvolver)

        dbHandler = DBHandler(this)

        btnEliminarEstudiante.setOnClickListener { eliminarEstudiante() }
        btnEliminarCatedra.setOnClickListener { eliminarCatedra() }
        btnVolver.setOnClickListener { finish() }
    }

    private fun eliminarEstudiante() {
        val documento = etDocumento.text.toString().trim()
        if (documento.isNotEmpty()) {
            val eliminado = dbHandler.eliminarEstudiante(documento)
            if (eliminado) {
                showToast("Estudiante eliminado con éxito")
                limpiarCampos()
            } else {
                showToast("No se encontró el estudiante")
            }
        } else {
            showToast("Ingrese un documento")
        }
    }

    private fun eliminarCatedra() {
        val documento = etDocumento.text.toString().trim()
        val nombreCatedra = etNombreCatedra.text.toString().trim()

        if (documento.isNotEmpty() && nombreCatedra.isNotEmpty()) {
            val eliminado = dbHandler.eliminarCatedra(documento, nombreCatedra)
            if (eliminado) {
                showToast("Cátedra eliminada con éxito")
                limpiarCampos()
            } else {
                showToast("No se encontró la cátedra para este estudiante")
            }
        } else {
            showToast("Ingrese documento y nombre de cátedra")
        }
    }

    private fun limpiarCampos() {
        etDocumento.text.clear()
        etNombreCatedra.text.clear()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}