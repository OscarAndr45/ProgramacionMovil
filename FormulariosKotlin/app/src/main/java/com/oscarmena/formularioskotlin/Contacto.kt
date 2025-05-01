package com.oscarmena.formularioskotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView

class Contacto : AppCompatActivity() {
    private lateinit var tvDatosContacto: TextView
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_contacto)

        // Edge-to-edge insets handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // View binding
        btnVolver = findViewById(R.id.Btnvolver)
        tvDatosContacto = findViewById(R.id.tvdatoscontacto)

        // Set contact information
        tvDatosContacto.text = "Nombre: Oscar Andres \n Documento: 1078456414 \n Telefono: 3222385048 \n Direccion: Calle 57dd#23a16 "

        // Set click listener
        btnVolver.setOnClickListener { volver() }
    }

    private fun volver() {
        finish()
    }
}