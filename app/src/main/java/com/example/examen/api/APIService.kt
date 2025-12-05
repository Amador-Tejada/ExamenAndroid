package com.example.examen.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Interfaz Kotlin para Retrofit. Declara métodos suspend para usarse dentro de corrutinas.
 */
interface APIService {
    /**
     * Función suspendida que realiza una petición GET a la API de perros
     *
     * @GET: Anotación que indica que este método hace una petición HTTP GET
     * @Url: Permite pasar la URL completa como parámetro (útil para URLs dinámicas)
     *
     * suspend: Palabra clave que indica que esta función debe ejecutarse en una corrutina
     * Es necesario porque las operaciones de red no deben bloquear el hilo principal
     *
     * @param url La ruta específica del endpoint (ej: "hound/images")
     * @return Response<DogResponse> El objeto Response contiene tanto el resultado como metadatos (código HTTP, headers, etc.)
     */
    @GET
    suspend fun getDogsByBreeds(@Url url: String):Response<DogResponse>
}