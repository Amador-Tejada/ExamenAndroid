package com.example.examen.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.examen.R
import com.example.examen.api.APIService
import com.example.examen.api.DogResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
import androidx.lifecycle.lifecycleScope

// Fragmento que consume una API REST para mostrar imágenes aleatorias de perros
// Fragmento que consume una API REST para mostrar imágenes aleatorias de perros
class ApiRestBoton : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Este método está vacío, no se necesita inicialización aquí
    }

    // Crea y configura una instancia de Retrofit para hacer peticiones HTTP
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/") // URL base de la API de Dog CEO
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor JSON a objetos Kotlin
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout del fragmento (sin usar View Binding)
        val view = inflater.inflate(R.layout.fragment_api_rest_boton, container, false)

        // Obtiene referencia al ImageView del layout
        val imageView = view.findViewById<ImageView>(R.id.imageRandom)

        // Función interna para cargar una imagen aleatoria desde la API
        fun loadRandomImage() {
            // Lanza una corrutina en el ciclo de vida del fragmento
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    Log.d("ApiRestBoton", "Solicitando imágenes aleatorias (DogResponse)")

                    // Ejecuta la petición HTTP en el hilo IO (background)
                    val response: Response<DogResponse> = withContext(Dispatchers.IO) {
                        // Solicita 100 imágenes aleatorias de perros
                        getRetrofit().create(APIService::class.java).getDogsByBreeds("api/breeds/image/random/100")
                    }

                    Log.d("ApiRestBoton", "Código respuesta: ${response.code()}")

                    // Verifica si el fragmento sigue adjunto a la actividad
                    if (!isAdded) return@launch

                    // Procesa la respuesta si es exitosa (código 200)
                    if (response.isSuccessful) {
                        val body = response.body()
                        // Obtiene la lista de URLs de imágenes
                        val images = body?.images ?: emptyList()

                        // Selecciona una URL aleatoria de la lista
                        val url = images.randomOrNull()

                        // Si hay una URL válida, carga la imagen con Picasso
                        if (!url.isNullOrEmpty()) {
                            Picasso.get().load(url)
                                .placeholder(R.drawable.ic_launcher_foreground) // Imagen mientras carga
                                .error(R.drawable.ic_launcher_foreground) // Imagen si hay error
                                .fit() // Ajusta la imagen al tamaño del ImageView
                                .centerCrop() // Recorta desde el centro
                                .into(imageView) // Muestra en el ImageView
                        } else {
                            Log.e("ApiRestBoton", "No hay imágenes en la respuesta")
                        }
                    } else {
                        Log.e("ApiRestBoton", "Respuesta no exitosa: ${response.code()}")
                    }
                } catch (e: Exception) {
                    // Captura errores de red o parsing
                    Log.e("ApiRestBoton", "Error al obtener imagen aleatoria", e)
                }
            }
        }

        // Carga automáticamente la primera imagen al entrar al fragmento
        loadRandomImage()

        // Configura el listener: al pulsar la imagen se carga otra aleatoria
        imageView.setOnClickListener {
            loadRandomImage()
        }

        return view
    }
}