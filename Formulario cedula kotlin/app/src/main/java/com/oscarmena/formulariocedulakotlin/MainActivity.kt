package com.oscarmena.formulariocedulakotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.EditText
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var resultado: TextView
    private lateinit var documento: EditText
    private lateinit var guardar: Button
    private lateinit var mostrar: Button
    private lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        documento = findViewById(R.id.Etdocumento)
        resultado = findViewById(R.id.tvResultado)
        guardar = findViewById(R.id.Btnguardar)
        mostrar = findViewById(R.id.Btnmostrar)

        dbHandler = DBHandler(this)

        guardar.setOnClickListener { guardar() }
        mostrar.setOnClickListener { mostrar() }
    }

    private fun guardar() {
        val doc = documento.text.toString()
        if (doc.isEmpty()) {
            Message.message(this, "Campo Vacio")
            return
        }
        dbHandler.ingresar(doc)
        Message.message(this, "Ingreso Exitoso")
    }

    private fun mostrar() {
        val resultadoTexto = dbHandler.consultar()
        resultado.text = resultadoTexto
        Message.message(this, resultadoTexto)
    }
}