package com.oscarmena.formularioskotlin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Consultar : AppCompatActivity() {
    private lateinit var etDocumento: EditText
    private lateinit var etNombreCatedra: EditText
    private lateinit var btnConsultaIdEstudiante: Button
    private lateinit var btnConsultaIdCatedra: Button
    private lateinit var btnVolver: Button
    private lateinit var lvEstudiante: ListView
    private lateinit var lvCatedras: ListView
    private lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar)

        // Initialize views
        etDocumento = findViewById(R.id.etDocumento)
        etNombreCatedra = findViewById(R.id.etNombreCatedra)
        btnConsultaIdEstudiante = findViewById(R.id.btnConsultaIdEstudiante)
        btnConsultaIdCatedra = findViewById(R.id.btnConsultaIdCatedra)
        btnVolver = findViewById(R.id.btnVolver)
        lvEstudiante = findViewById(R.id.lvEstudiante)
        lvCatedras = findViewById(R.id.lvCatedras)

        dbHandler = DBHandler(this)

        // Action to query student by document
        btnConsultaIdEstudiante.setOnClickListener { consultarEstudiante() }

        // Action to query class by name
        btnConsultaIdCatedra.setOnClickListener { consultarCatedra() }

        // Return to previous screen
        btnVolver.setOnClickListener { finish() }
    }

    // Method to query student
    private fun consultarEstudiante() {
        val documento = etDocumento.text.toString().trim()
        if (documento.isNotEmpty()) {
            val datosEstudiante = dbHandler.consultarEstudiante(documento)
            if (datosEstudiante.isNotEmpty()) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    datosEstudiante
                )
                lvEstudiante.adapter = adapter
            } else {
                Toast.makeText(
                    this,
                    "No se encontraron estudiantes con ese documento",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                this,
                "Ingrese un documento",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Method to query class
    private fun consultarCatedra() {
        val nombre = etNombreCatedra.text.toString().trim()
        val documento = etDocumento.text.toString().trim()

        // Ensure both values are present
        if (nombre.isNotEmpty() && documento.isNotEmpty()) {
            val datosCatedra = dbHandler.consultarCatedra(nombre, documento)
            if (datosCatedra.isNotEmpty()) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    datosCatedra
                )
                lvCatedras.adapter = adapter
            } else {
                Toast.makeText(
                    this,
                    "No se encontraron cátedras con ese nombre para este estudiante",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                this,
                "Ingrese el nombre de la cátedra y el documento del estudiante",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}