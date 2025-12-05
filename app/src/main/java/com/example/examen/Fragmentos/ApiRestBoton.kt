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


class ApiRestBoton : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_api_rest_boton, container, false)

        val imageView = view.findViewById<ImageView>(R.id.imageRandom)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBarBoton)

        fun loadRandomImage() {
            progressBar.visibility = View.VISIBLE
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    Log.d("ApiRestBoton", "Solicitando imágenes aleatorias (DogResponse)")
                    val response: Response<DogResponse> = withContext(Dispatchers.IO) {
                        // Pedimos varias (10) para obtener una lista en `DogResponse`
                        getRetrofit().create(APIService::class.java).getDogsByBreeds("api/breeds/image/random/100")
                    }

                    Log.d("ApiRestBoton", "Código respuesta: ${'$'}{response.code()}")

                    if (!isAdded) return@launch

                    progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val body = response.body()
                        val images = body?.images ?: emptyList()
                        // Elegir una URL aleatoria de la lista
                        val url = images.randomOrNull()
                        if (!url.isNullOrEmpty()) {
                            Picasso.get().load(url)
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_foreground)
                                .fit()
                                .centerCrop()
                                .into(imageView)
                        } else {
                            Log.e("ApiRestBoton", "No hay imágenes en la respuesta")
                        }
                    } else {
                        Log.e("ApiRestBoton", "Respuesta no exitosa: ${'$'}{response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e("ApiRestBoton", "Error al obtener imagen aleatoria", e)
                    if (isAdded) progressBar.visibility = View.GONE
                }
            }
        }

        // Cargar primera imagen al entrar
        loadRandomImage()

        imageView.setOnClickListener {
            // Al pulsar pedimos otra imagen aleatoria
            loadRandomImage()
        }

        return view
    }

}