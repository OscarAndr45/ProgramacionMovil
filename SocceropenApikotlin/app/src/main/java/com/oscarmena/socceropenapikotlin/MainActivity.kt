package com.oscarmena.socceropenapikotlin

import android.os.Bundle
import android.view.View
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var edtStadiumName: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvName: TextView
    private lateinit var tvCity: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvCapacity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtStadiumName = findViewById(R.id.edtStadiumName)
        btnSearch = findViewById(R.id.btnSearch)
        tvName = findViewById(R.id.tvName)
        tvCity = findViewById(R.id.tvCity)
        tvAddress = findViewById(R.id.tvAddress)
        tvCapacity = findViewById(R.id.tvCapacity)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.sportmonks.com/v3/football/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val soccerApiService = retrofit.create(SoccerApiService::class.java)

        btnSearch.setOnClickListener {
            val stadiumName = edtStadiumName.text.toString().trim()

            if (stadiumName.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese un nombre de estadio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showLoading(true)

            val apiToken = "6vb94XlnTA8Hm8wAEOupJL87C40YjJkOtyJRIGioIhm8Oc6EKkdJSrdm83wH"
            val call = soccerApiService.searchVenues(apiToken, stadiumName)

            call.enqueue(object : Callback<VenuesResponse> {
                override fun onResponse(call: Call<VenuesResponse>, response: Response<VenuesResponse>) {
                    showLoading(false)

                    if (response.isSuccessful && response.body() != null) {
                        val venues = response.body()?.data
                        if (!venues.isNullOrEmpty()) {
                            val searchName = stadiumName.lowercase()
                            val venue = venues.find { it.name?.lowercase()?.contains(searchName) == true }
                            if (venue != null) {
                                displayVenueInfo(venue)
                            } else {
                                Toast.makeText(this@MainActivity, "No se encontró un estadio exacto", Toast.LENGTH_SHORT).show()
                                clearVenueInfo()
                            }
                        } else {
                            Toast.makeText(this@MainActivity, "No se encontraron estadios", Toast.LENGTH_SHORT).show()
                            clearVenueInfo()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show()
                        clearVenueInfo()
                    }
                }

                override fun onFailure(call: Call<VenuesResponse>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    clearVenueInfo()
                }
            })
        }
    }

    private fun displayVenueInfo(venue: VenuesResponse.Venue) {
        tvName.text = "Nombre: ${venue.name}"
        tvCity.text = "Ciudad: ${venue.cityName}"
        tvAddress.text = "Dirección: ${venue.address}"
        tvCapacity.text = "Capacidad: ${venue.capacity}"

        tvName.visibility = View.VISIBLE
        tvCity.visibility = View.VISIBLE
        tvAddress.visibility = View.VISIBLE
        tvCapacity.visibility = View.VISIBLE
    }

    private fun clearVenueInfo() {
        tvName.text = ""
        tvCity.text = ""
        tvAddress.text = ""
        tvCapacity.text = ""
    }

    private fun showLoading(isLoading: Boolean) {
        btnSearch.isEnabled = !isLoading
    }
}
