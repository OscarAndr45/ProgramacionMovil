package com.oscarmena.backend_datospersonales;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioApiService {

    @GET("operaciongetusuario")
    Call<UsuarioResponse> getUsuario();
}
