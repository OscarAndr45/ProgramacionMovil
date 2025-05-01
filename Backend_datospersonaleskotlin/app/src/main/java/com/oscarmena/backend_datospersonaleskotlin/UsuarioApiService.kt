package com.oscarmena.backend_datospersonaleskotlin

import retrofit2.Call
import retrofit2.http.GET

interface UsuarioApiService {

    @GET("operaciongetusuario")
    fun getUsuario(): Call<UsuarioResponse>
}