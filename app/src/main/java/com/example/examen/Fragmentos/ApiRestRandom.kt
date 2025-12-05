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

class ApiRestRandom : Fragment() {

    private var _binding: FragmentApiRestRandomBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DogAdapter
    private val imagesList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiRestRandomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        fetchRandomImages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        adapter = DogAdapter(imagesList)
        binding.recyclerViewRandom.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRandom.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun fetchRandomImages() {
        binding.progressBar.visibility = VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                Log.d("ApiRestRandom", "Solicitando imágenes aleatorias")
                val response: Response<DogResponse> = withContext(Dispatchers.IO) {
                    // endpoint que devuelve 10 imágenes aleatorias
                    getRetrofit().create(APIService::class.java).getDogsByBreeds("api/breeds/image/random/100")
                }

                Log.d("ApiRestRandom", "Código respuesta: ${'$'}{response.code()}")

                if (!isAdded || _binding == null) return@launch

                binding.progressBar.visibility = GONE

                if (response.isSuccessful) {
                    val body = response.body()
                    val images = body?.images ?: emptyList()
                    imagesList.clear()
                    imagesList.addAll(images)
                    if (::adapter.isInitialized) adapter.notifyDataSetChanged()
                } else {
                    Log.e("ApiRestRandom", "Respuesta no exitosa: ${'$'}{response.code()}")
                }

            } catch (e: Exception) {
                Log.e("ApiRestRandom", "Error al obtener imágenes aleatorias", e)
                if (isAdded && _binding != null) binding.progressBar.visibility = GONE
            }
        }
    }
}