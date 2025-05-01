package com.oscarmena.backend_datospersonaleskotlin

data class UsuarioResponse(
    val identificacion: String,
    val documento: String,
    val nombres: String,
    val apellidos: String,
    val fechaNacimiento: String,
    val email: String,
    val direccion: String,
    val imagen: String
)