package com.oscarmena.formulariodatospersonaleskotlin
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    // 1. Declaracion de variables
    private lateinit var etNombre: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etDocumento: EditText
    private lateinit var etEdad: EditText
    private lateinit var btnAceptar: Button
    private lateinit var tvDatos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 2. Referenciar campos del formulario

        etNombre = findViewById(R.id.etNombre)
        etApellidos = findViewById(R.id.etApellidos)
        etDocumento = findViewById(R.id.etDocumento)
        etEdad = findViewById(R.id.etEdad)
        btnAceptar = findViewById(R.id.btnAceptar)
        tvDatos = findViewById(R.id.tvDatos)

        //3. Configurar listener del boton
        btnAceptar.setOnClickListener{
            mostrarDatos()
        }
    }

    private fun mostrarDatos()
    {
        val nombre = etNombre.text.toString()
        val apellidos = etApellidos.text.toString()
        val documento = etDocumento.text.toString()
        val edad = etEdad.text.toString()

        // Verificar si algún campo está vacío
        if (nombre.isEmpty() || apellidos.isEmpty() || documento.isEmpty() || edad.isEmpty()) {
            tvDatos.text = "Ingrese todos los datos"
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
        } else {
            // Mostrar los datos ingresados
            tvDatos.text = "Nombre: $nombre\nApellidos: $apellidos\nDocumento: $documento\nEdad: $edad"
            Toast.makeText(this, "Datos mostrados correctamente", Toast.LENGTH_SHORT).show()
        }

    }

}
