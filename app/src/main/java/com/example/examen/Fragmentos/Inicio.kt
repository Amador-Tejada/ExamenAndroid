package com.example.examen.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatToggleButton
import com.example.examen.R
import com.example.examen.databinding.FragmentApiRestBinding
import com.example.examen.databinding.FragmentInicioBinding

class Inicio : Fragment() {

    // ViewBinding seguro para fragmentos con binding
    private var _binding: FragmentInicioBinding? = null

    private val binding get() = _binding!! // Propiedad de solo lectura para evitar nulos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}