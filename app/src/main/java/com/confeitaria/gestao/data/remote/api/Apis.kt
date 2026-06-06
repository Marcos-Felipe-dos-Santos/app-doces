package com.confeitaria.gestao.data.remote.api

import com.confeitaria.gestao.data.remote.dto.*
import retrofit2.http.*

interface ViaCepApi {
    @GET("ws/{cep}/json/")
    suspend fun buscarCep(@Path("cep") cep: String): ViaCepResponse
}

interface NominatimApi {
    @Headers("User-Agent: ConfeitariaApp/1.0")
    @GET("search")
    suspend fun geocodificar(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 1
    ): List<NominatimResponse>
}

interface OsrmApi {
    @GET("route/v1/driving/{lon1},{lat1};{lon2},{lat2}")
    suspend fun calcularRota(
        @Path("lon1") lon1: Double,
        @Path("lat1") lat1: Double,
        @Path("lon2") lon2: Double,
        @Path("lat2") lat2: Double,
        @Query("overview") overview: String = "false"
    ): OsrmResponse
}
