package com.oscarmena.backend_datospersonaleskotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnConsultar: Button
    private lateinit var tvIdentificacion: TextView
    private lateinit var tvDocumento: TextView
    private lateinit var tvNombres: TextView
    private lateinit var tvApellidos: TextView
    private lateinit var tvFechaNacimiento: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvDireccion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        imageView = findViewById(R.id.imageView)
        btnConsultar = findViewById(R.id.btnConsultar)
        tvIdentificacion = findViewById(R.id.tvIdentificacion)
        tvDocumento = findViewById(R.id.tvDocumento)
        tvNombres = findViewById(R.id.tvNombres)
        tvApellidos = findViewById(R.id.tvApellidos)
        tvFechaNacimiento = findViewById(R.id.tvFechaNacimiento)
        tvEmail = findViewById(R.id.tvEmail)
        tvDireccion = findViewById(R.id.tvDireccion)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8067/") // Cambia aquí tu IP / servidor correcto
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val usuarioApiService = retrofit.create(UsuarioApiService::class.java)

        btnConsultar.setOnClickListener {
            val call = usuarioApiService.getUsuario()

            call.enqueue(object : Callback<UsuarioResponse> {
                override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val usuario = response.body()!!

                        tvIdentificacion.text = "Identificación: ${usuario.identificacion}"
                        tvDocumento.text = "Documento: ${usuario.documento}"
                        tvNombres.text = "Nombres: ${usuario.nombres}"
                        tvApellidos.text = "Apellidos: ${usuario.apellidos}"
                        tvFechaNacimiento.text = "Fecha Nacimiento: ${usuario.fechaNacimiento}"
                        tvEmail.text = "Email: ${usuario.email}"
                        tvDireccion.text = "Dirección: ${usuario.direccion}"

                        Picasso.get().load(usuario.imagen).into(imageView)
                    } else {
                        Toast.makeText(this@MainActivity, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
