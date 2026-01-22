package com.example.examen.Fragmentos.GridviewConDB

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.examen.Miscelanea.DBdataGridview
import com.example.examen.R
import com.example.examen.adapter.personaAdapter
import com.example.examen.databinding.FragmentGridViewDBBinding

class GridViewDB : Fragment() {
    // Usar patrón de binding seguro
    private lateinit var binding: FragmentGridViewDBBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGridViewDBBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // recogemos los datos de la DBdataGridview y los mostramos en el gridview
        val dbHelper = DBdataGridview(requireContext())
        val listaPersonas = dbHelper.obtenerTodos()

        val adapter = personaAdapter(requireContext(), listaPersonas)
        binding.gvMain.adapter = adapter
        binding.gvMain.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val persona = listaPersonas[position]
                when (persona.ciclo) {
                    "DAM" -> {
                        Toast.makeText(requireContext(), "DAM", Toast.LENGTH_SHORT).show()
                    }

                    "DAW" -> {
                        Toast.makeText(requireContext(), "DAW", Toast.LENGTH_SHORT).show()

                    }

                    "ASIR" -> {
                        Toast.makeText(requireContext(), "ASIR", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}