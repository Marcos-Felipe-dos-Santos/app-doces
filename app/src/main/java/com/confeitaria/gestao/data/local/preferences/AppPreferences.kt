package com.confeitaria.gestao.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class AppPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private val PRECO_POR_KM = doublePreferencesKey("preco_por_km")
    private val NOME_CONFEITEIRA = stringPreferencesKey("nome_confeiteira")
    private val PIX_KEY = stringPreferencesKey("pix_key")
    private val ENDERECO_BASE_TEXTO = stringPreferencesKey("endereco_base_texto")
    private val ENDERECO_BASE_LAT = doublePreferencesKey("endereco_base_lat")
    private val ENDERECO_BASE_LON = doublePreferencesKey("endereco_base_lon")

    val precoPorKm: Flow<Double> = context.dataStore.data.map { it[PRECO_POR_KM] ?: 1.50 }
    val nomeConfeiteira: Flow<String> = context.dataStore.data.map { it[NOME_CONFEITEIRA] ?: "" }
    val pixKey: Flow<String> = context.dataStore.data.map { it[PIX_KEY] ?: "" }
    val enderecoBase: Flow<String> = context.dataStore.data.map { it[ENDERECO_BASE_TEXTO] ?: "" }

    suspend fun setPrecoPorKm(valor: Double) { context.dataStore.edit { it[PRECO_POR_KM] = valor } }
    suspend fun setNomeConfeiteira(nome: String) { context.dataStore.edit { it[NOME_CONFEITEIRA] = nome } }
    suspend fun setPixKey(key: String) { context.dataStore.edit { it[PIX_KEY] = key } }
    suspend fun setEnderecoBase(endereco: String) { context.dataStore.edit { it[ENDERECO_BASE_TEXTO] = endereco } }
}
