package com.example.examen.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.adapter.DogAdapter
import com.example.examen.api.APIService
import com.example.examen.api.DogResponse
import com.example.examen.databinding.FragmentApiRestRandomBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Fragmento que muestra un listado de imágenes aleatorias de perros obtenidas de una API REST
class ApiRestRandom : Fragment() {

    // Variable nullable para el binding, se inicializa en onCreateView y se limpia en onDestroyView
    private var _binding: FragmentApiRestRandomBinding? = null

    // Propiedad que devuelve el binding de forma segura (!!), solo debe usarse cuando sabemos que no es null
    private val binding get() = _binding!!

    // Adaptador personalizado para mostrar las imágenes de perros en el RecyclerView
    private lateinit var adapter: DogAdapter

    // Lista mutable que almacena las URLs de las imágenes obtenidas de la API
    private val imagesList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Este método está vacío, no se necesita inicialización aquí
    }

    // Método que se ejecuta para crear la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el layout usando View Binding y guarda la referencia
        _binding = FragmentApiRestRandomBinding.inflate(inflater, container, false)
        // Retorna la vista raíz del binding
        return binding.root
    }

    // Método que se ejecuta después de que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el RecyclerView con su adaptador y layout manager
        initRecyclerView()

        // Realiza la petición para obtener imágenes aleatorias de perros
        fetchRandomImages()
    }

    // Método que se ejecuta cuando la vista va a ser destruida
    override fun onDestroyView() {
        super.onDestroyView()
        // Limpia la referencia del binding para evitar memory leaks
        _binding = null
    }

    // Configura el RecyclerView con un LinearLayoutManager vertical y el adaptador personalizado
    private fun initRecyclerView() {
        // Crea el adaptador pasando la lista de URLs de imágenes
        adapter = DogAdapter(imagesList)
        // Establece un LinearLayoutManager para mostrar los items en lista vertical
        binding.recyclerViewRandom.layoutManager = LinearLayoutManager(requireContext())
        // Asigna el adaptador al RecyclerView
        binding.recyclerViewRandom.adapter = adapter
    }

    // Crea y configura una instancia de Retrofit para hacer peticiones HTTP a la API de perros
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/") // URL base de la API de Dog CEO
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor JSON a objetos Kotlin
            .build()
    }

    // Realiza la petición a la API para obtener 100 imágenes aleatorias de perros
    private fun fetchRandomImages() {
        // Muestra el ProgressBar mientras se realiza la petición
        binding.progressBar.visibility = VISIBLE

        // Lanza una corrutina vinculada al ciclo de vida de la vista del fragmento
        viewLifecycleOwner.lifecycleScope.launch {
            // Try-catch para capturar errores de red o parsing de JSON
            try {
                Log.d("ApiRestRandom", "Solicitando imágenes aleatorias")

                // Ejecuta la petición HTTP en el hilo IO (background) para no bloquear la UI
                val response: Response<DogResponse> = withContext(Dispatchers.IO) {
                    // Endpoint que devuelve 100 imágenes aleatorias de perros
                    getRetrofit().create(APIService::class.java).getDogsByBreeds("api/breeds/image/random/100")
                }

                Log.d("ApiRestRandom", "Código respuesta: ${response.code()}")

                // Verifica que el fragmento sigue adjunto y el binding no es null antes de actualizar UI
                if (!isAdded || _binding == null) return@launch

                // Oculta el ProgressBar una vez recibida la respuesta
                binding.progressBar.visibility = GONE

                // Procesa la respuesta si es exitosa (código 200)
                if (response.isSuccessful) {
                    val body = response.body() // Obtiene el cuerpo de la respuesta parseado a DogResponse
                    val images = body?.images ?: emptyList() // Si images es null, usa lista vacía

                    imagesList.clear() // Limpia la lista anterior de imágenes
                    imagesList.addAll(images) // Añade las nuevas URLs de imágenes

                    // Verifica que el adaptador ha sido inicializado antes de notificar cambios
                    if (::adapter.isInitialized) adapter.notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado
                } else {
                    // Si la respuesta no es exitosa, registra el error en los logs
                    Log.e("ApiRestRandom", "Respuesta no exitosa: ${response.code()}")
                }

            } catch (e: Exception) {
                // Captura errores de red, timeout o parsing de JSON
                Log.e("ApiRestRandom", "Error al obtener imágenes aleatorias", e)
                // Oculta el ProgressBar si hay error y el fragmento sigue activo
                if (isAdded && _binding != null) binding.progressBar.visibility = GONE
            }
        }
    }
}