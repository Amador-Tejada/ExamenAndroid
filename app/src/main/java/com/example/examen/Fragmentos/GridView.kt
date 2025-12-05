package com.example.examen.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.examen.R
import com.example.examen.adapter.DogAdapter
import com.example.examen.databinding.FragmentApiRestBinding
import com.example.examen.databinding.FragmentApiRestRandomBinding
import com.example.examen.databinding.FragmentGridViewBinding

class GridView : Fragment() {
    private var _binding: FragmentGridViewBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: DogAdapter

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
        _binding = FragmentGridViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}