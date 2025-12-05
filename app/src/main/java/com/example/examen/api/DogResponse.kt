package com.example.examen.api

import com.google.gson.annotations.SerializedName

/**
 * Data class que representa la respuesta de la API
 * Gson la usa para convertir el JSON que recibimos en un objeto Kotlin
 */
data class DogResponse (
    /**
     * @SerializedName: Indica qué campo del JSON se mapea a esta propiedad
     * En este caso, el campo "status" del JSON se asigna a la variable status
     */
    @SerializedName("status") var status: String,

    /**
     * El campo "message" del JSON contiene la lista de URLs de imágenes
     * Lo mapeamos a una propiedad llamada "images" para que el código sea más claro
     */
    @SerializedName("message") var images: List<String>
    )