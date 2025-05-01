package com.oscarmena.calculadorakotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    private var etValorA: EditText? = null
    private var etValorB: EditText? = null
    private var tvNumero: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        etValorA = findViewById<EditText>(R.id.etValorA)
        etValorB = findViewById<EditText>(R.id.etValorB)
        tvNumero = findViewById<TextView>(R.id.tvNumero)

        val btSuma = findViewById<Button>(R.id.btSuma)
        btSuma.setOnClickListener { v: View? -> fnSuma() }

        val btResta = findViewById<Button>(R.id.btResta)
        btResta.setOnClickListener { v: View? -> fnResta() }

        val btMultiplicacion = findViewById<Button>(R.id.btMultiplicacion)
        btMultiplicacion.setOnClickListener { v: View? -> fnMultiplicacion() }

        val btDivision = findViewById<Button>(R.id.btDivision)
        btDivision.setOnClickListener { v: View? -> fnDivision() }

    }


    private fun fnSuma() {
        val strValorA = etValorA!!.text.toString()
        val strValorB = etValorB!!.text.toString()

        val dblValorA = strValorA.toDouble()
        val dblValorB = strValorB.toDouble()
        var dblNumero = 0.0

        dblNumero = dblValorA + dblValorB

        if (strValorA.isEmpty() || strValorB.isEmpty()) {
            tvNumero!!.text = "Valores vacios"
        } else {
            tvNumero!!.text = dblNumero.toInt().toString()
        }
    }

    private fun fnResta() {
        val strValorA = etValorA!!.text.toString()
        val strValorB = etValorB!!.text.toString()

        val dblValorA = strValorA.toDouble()
        val dblValorB = strValorB.toDouble()
        var dblNumero = 0.0

        dblNumero = dblValorA - dblValorB

        if (strValorA.isEmpty() || strValorB.isEmpty()) {
            tvNumero!!.text = "Valores vacios"
        } else {
            tvNumero!!.text = dblNumero.toInt().toString()
        }
    }

    private fun fnMultiplicacion() {
        val strValorA = etValorA!!.text.toString()
        val strValorB = etValorB!!.text.toString()

        val dblValorA = strValorA.toDouble()
        val dblValorB = strValorB.toDouble()
        var dblNumero = 0.0

        dblNumero = dblValorA * dblValorB

        if (strValorA.isEmpty() || strValorB.isEmpty()) {
            tvNumero!!.text = "Valores vacios"
        } else {
            tvNumero!!.text = dblNumero.toInt().toString()
        }
    }

    private fun fnDivision() {
        val strValorA = etValorA!!.text.toString()
        val strValorB = etValorB!!.text.toString()

        if (strValorA.isEmpty() || strValorB.isEmpty()) {
            tvNumero!!.text = "Valores vacÃ­os"
            return
        }

        val dblValorA = strValorA.toDouble()
        val dblValorB = strValorB.toDouble()

        if (dblValorB == 0.0) {
            tvNumero!!.text = "No se puede dividir por 0"
            return
        }

        val dblNumero = dblValorA / dblValorB
        tvNumero!!.text = dblNumero.toInt().toString()
    }
}