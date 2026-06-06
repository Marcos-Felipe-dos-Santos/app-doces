package com.confeitaria.gestao.data.remote.dto

data class ViaCepResponse(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
    val erro: Boolean?
)

data class NominatimResponse(
    val lat: String,
    val lon: String,
    val display_name: String
)

data class OsrmResponse(
    val code: String,
    val routes: List<OsrmRoute>
)

data class OsrmRoute(
    val distance: Double,
    val duration: Double
)
