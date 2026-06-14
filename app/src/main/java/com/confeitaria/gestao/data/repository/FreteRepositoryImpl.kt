package com.confeitaria.gestao.data.repository

import com.confeitaria.gestao.data.remote.api.NominatimApi
import com.confeitaria.gestao.data.remote.api.OsrmApi
import com.confeitaria.gestao.data.remote.api.ViaCepApi
import com.confeitaria.gestao.domain.model.Endereco
import com.confeitaria.gestao.domain.repository.FreteRepository
import javax.inject.Inject

class FreteRepositoryImpl @Inject constructor(
    private val viaCepApi: ViaCepApi,
    private val nominatimApi: NominatimApi,
    private val osrmApi: OsrmApi
) : FreteRepository {

    // ViaCEP deve ser chamado apenas via botão explícito do usuário,
    // nunca automaticamente. Falha de rede não bloqueia salvar o cliente.
    override suspend fun buscarCep(cep: String): Endereco? = try {
        val response = viaCepApi.buscarCep(cep)
        if (response.erro == true) null
        else Endereco(
            logradouro = response.logradouro ?: "",
            bairro = response.bairro ?: "",
            cidade = response.localidade ?: "",
            uf = response.uf ?: "",
            cep = response.cep ?: "",
            clienteId = 0,
            numero = ""
        )
    } catch (e: Exception) { null }

    // Geocodificação automática é opcional. MVP usa distância manual.
    override suspend fun geocodificar(endereco: String): Pair<Double, Double>? = try {
        val response = nominatimApi.geocodificar(endereco)
        if (response.isEmpty()) null
        else response[0].lat.toDouble() to response[0].lon.toDouble()
    } catch (e: Exception) { null }

    // Cálculo de rota via OSRM é opcional. MVP usa distância manual via CalcularPedido.freteCalculado().
    override suspend fun calcularDistancia(origem: Pair<Double, Double>, destino: Pair<Double, Double>): Double? = try {
        val response = osrmApi.calcularRota(origem.second, origem.first, destino.second, destino.first)
        if (response.code == "Ok" && response.routes.isNotEmpty()) {
            response.routes[0].distance / 1000.0
        } else null
    } catch (e: Exception) { null }
}
