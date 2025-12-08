package com.example.examen.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.adapter.DogAdapter
import com.example.examen.api.APIService
import com.example.examen.api.DogResponse
import com.example.examen.databinding.FragmentApiRestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Fragmento que permite buscar imágenes de perros por raza usando una API REST
// Implementa SearchView.OnQueryTextListener para detectar cambios en la búsqueda
class ApiRestBusqueda : Fragment(), SearchView.OnQueryTextListener{

    // Variable nullable para el binding, se inicializa en onCreateView y se limpia en onDestroyView
    private var _binding: FragmentApiRestBinding? = null

    // Propiedad que devuelve el binding de forma segura (!!), solo debe usarse cuando sabemos que no es null
    private val binding get() = _binding!!

    // Adaptador personalizado para mostrar las imágenes de perros en el RecyclerView
    private lateinit var adapter: DogAdapter

    // Lista mutable que almacena las URLs de las imágenes obtenidas de la API
    private val dosImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Este método está vacío, no se necesita inicialización aquí
    }

    // Método que se ejecuta para crear la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el layout usando View Binding y guarda la referencia
        _binding = FragmentApiRestBinding.inflate(inflater, container, false)
        // Retorna la vista raíz del binding
        return binding.root
    }

    // Método que se ejecuta después de que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Asigna este fragmento como listener del SearchView para detectar búsquedas
        binding.barraBusqueda.setOnQueryTextListener(this)

        // Inicializa el RecyclerView con su adaptador y layout manager
        initRecyclerView()
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
        adapter = DogAdapter(dosImages)
        // Establece un LinearLayoutManager para mostrar los items en lista vertical
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Asigna el adaptador al RecyclerView
        binding.recyclerView.adapter = adapter
    }

    // Crea y configura una instancia de Retrofit para hacer peticiones HTTP a la API de perros
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/") // URL base de la API (específica para razas)
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor JSON a objetos Kotlin
            .build()
    }

    // Realiza una búsqueda de imágenes de perros filtrando por raza
    private fun searchByName(query: String) {
        // Lanza una corrutina vinculada al ciclo de vida de la vista del fragmento
        viewLifecycleOwner.lifecycleScope.launch {
            // Try-catch para capturar errores de red o parsing de JSON
            try {
                Log.d("ApiRest", "Buscando raza: $query")

                // Ejecuta la petición HTTP en el hilo IO (background) para no bloquear la UI
                val response: Response<DogResponse> = withContext(Dispatchers.IO) {
                    // Llama al endpoint que devuelve imágenes de una raza específica
                    getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
                }

                Log.d("ApiRest", "Respuesta recibida: ${response.code()}")

                // Verifica que el fragmento sigue adjunto y el binding no es null antes de actualizar UI
                if (!isAdded || _binding == null) return@launch

                // Procesa la respuesta si es exitosa (código 200)
                if (response.isSuccessful) {
                    val perritos = response.body() // Obtiene el cuerpo de la respuesta parseado a DogResponse
                    val images = perritos?.images ?: emptyList() // Si images es null, usa lista vacía

                    dosImages.clear() // Limpia la lista anterior de imágenes
                    dosImages.addAll(images) // Añade las nuevas URLs de imágenes

                    // Verifica que el adaptador ha sido inicializado antes de notificar cambios
                    if (::adapter.isInitialized) {
                        adapter.notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado
                    }
                } else {
                    // Si la respuesta no es exitosa, muestra un mensaje de error
                    showError()
                }

            } catch (e: Exception) {
                // Captura errores de red, timeout o parsing de JSON
                Log.e("ApiRest", "Error en petición: ", e)
                // Muestra error solo si el fragmento sigue activo
                if (isAdded && _binding != null) showError()
            }
        }
    }

    // Muestra un mensaje Toast de error al usuario
    private fun showError() {
        Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    // Se ejecuta cuando el usuario envía la búsqueda (pulsa Enter o el botón de búsqueda)
    override fun onQueryTextSubmit(p0: String?): Boolean {
        // Si el texto no está vacío, realiza la búsqueda en minúsculas
        if (!p0.isNullOrEmpty()) {
            searchByName(p0.lowercase())
        }
        return true // Indica que el evento ha sido manejado
    }

    // Se ejecuta cada vez que el texto del SearchView cambia
    override fun onQueryTextChange(p0: String?): Boolean {
        return true // No hace nada, solo detecta el submit
    }
}