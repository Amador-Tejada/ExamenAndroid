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


class ApiRestBusqueda : Fragment(), SearchView.OnQueryTextListener{

    // ViewBinding seguro para fragmentos con binding
    private var _binding: FragmentApiRestBinding? = null

    private val binding get() = _binding!! // Propiedad de solo lectura para evitar nulos


    /**
     * Adaptador del RecyclerView
     */
    private lateinit var adapter: DogAdapter


    /**
     * Lista mutable que contiene las URLs de las imágenes
     */
    private val dosImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Inflar el layout del fragmento usando ViewBinding y devolver la vista
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflamos el layout usando ViewBinding y lo devolvemos
        _binding = FragmentApiRestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuramos el listener del SearchView para detectar búsquedas de texto
        binding.barraBusqueda.setOnQueryTextListener(this)

        // Inicializamos el RecyclerView
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Liberar el binding para evitar fugas de memoria
        _binding = null
    }

    // Inicializamos el RecyclerView con un LinearLayoutManager y el adaptador
    private fun initRecyclerView() {
        adapter = DogAdapter(dosImages)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    /**
     * Crea un objeto Retrofit con la configuración necesaria
     *
     * @return Un objeto Retrofit configurado para interactuar con la API de perros
     */
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Realiza una búsqueda de imágenes de perros por raza
     *
     * @param query La raza de perro a buscar (en minúsculas)
     */
    private fun searchByName(query: String) {
        // Usamos viewLifecycleOwner.lifecycleScope para que la corrutina se cancele cuando el viewLifecycle termine
        viewLifecycleOwner.lifecycleScope.launch {
            // Try-catch para manejar errores de red/parseo en la petición
            try {
                Log.d("ApiRest", "Buscando raza: $query")
                // Llamada de red en contexto de IO
                val response: Response<DogResponse> = withContext(Dispatchers.IO) {
                    getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
                }

                Log.d("ApiRest", "Respuesta recibida: ${response.code()}")

                // Asegurarnos de que el fragmento aún está agregado antes de actualizar UI
                if (!isAdded || _binding == null) return@launch

                if (response.isSuccessful) {
                    val perritos = response.body() // Parseo a DogResponse (data class)
                    val images = perritos?.images ?: emptyList() // Si images es null, usar lista vacía (operador Elvis)

                    dosImages.clear() // Limpiar la lista
                    dosImages.addAll(images) // Añadir las nuevas imágenes
                    // Comprobar que el adaptador ha sido inicializado antes de notificar
                    if (::adapter.isInitialized) { // :: hace referencia a la clase DogAdapter
                        adapter.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
                    }
                } else {
                    showError()
                }

            } catch (e: Exception) {
                // Manejar error de red/parseo
                Log.e("ApiRest", "Error en petición: ", e)
                if (isAdded && _binding != null) showError()
            }
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (!p0.isNullOrEmpty()) {
            searchByName(p0.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }
}