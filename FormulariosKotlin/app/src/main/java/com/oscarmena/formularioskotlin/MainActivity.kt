package com.oscarmena.formularioskotlin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btningresar: Button
    private lateinit var btnConsultar: Button
    private lateinit var btnModificar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btncontacto: Button
    private lateinit var lvItem: ListView
    private lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btningresar = findViewById(R.id.btnIngresar)
        btnConsultar = findViewById(R.id.btnconsultar)
        btnModificar = findViewById(R.id.btnmodificar)
        btnEliminar = findViewById(R.id.btnRetirar)
        btncontacto = findViewById(R.id.Btncontacto)
        lvItem = findViewById(R.id.lvitem)
        dbHandler = DBHandler(this)

        btningresar.setOnClickListener { irAIngresar() }
        btnConsultar.setOnClickListener { irAConsultar() }
        btnModificar.setOnClickListener { irAModificar() }
        btnEliminar.setOnClickListener { irAEliminar() }
        btncontacto.setOnClickListener { irAContacto() }

        cargarRegistrosEnListView()
    }

    private fun irAIngresar() {
        startActivity(Intent(this, Ingresar::class.java))
    }

    private fun irAConsultar() {
        startActivity(Intent(this, Consultar::class.java))
    }

    private fun irAModificar() {
        startActivity(Intent(this, modificar::class.java))
    }

    private fun irAEliminar() {
        startActivity(Intent(this, eliminar::class.java))
    }

    private fun irAContacto() {
        startActivity(Intent(this, Contacto::class.java))
    }

    private fun cargarRegistrosEnListView() {
        val registros = dbHandler.obtenerTodosLosRegistros()
        if (registros.isNotEmpty()) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, registros)
            lvItem.adapter = adapter
        }
    }
}