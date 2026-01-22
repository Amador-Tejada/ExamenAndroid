package com.example.examen.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.examen.adapter.personaAdapter
import com.example.examen.adapter.tipoPersona
import com.example.examen.databinding.FragmentGridViewBinding

// Fragmento que muestra una cuadrícula de personas en un GridView
class GridView : Fragment() {
    // Variable nullable para el binding, se inicializa en onCreateView y se limpia en onDestroyView
    private lateinit var binding: FragmentGridViewBinding

    // Adaptador personalizado para mostrar las personas en el GridView
    private lateinit var personaAdapter: personaAdapter

    // Lista mutable con datos de ejemplo de personas (nombre, apellidos, sexo, ciclo)
    private val gridlistaPersonas = mutableListOf(
        tipoPersona("Amador", "Tejada", "Hombre", "DAM"),
        tipoPersona("Maria", "Lopez", "Mujer", "DAW"),
        tipoPersona("Pedro", "Garcia", "Hombre", "ASIR"),
        tipoPersona("Ana", "Martin", "Mujer", "DAM"),
        tipoPersona("Luis", "Rodriguez", "Hombre", "DAW")
    )

    // Método que se ejecuta para crear la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el layout usando View Binding y guarda la referencia
        binding = FragmentGridViewBinding.inflate(inflater, container, false)
        // Retorna la vista raíz del binding
        return binding.root
    }

    // Método que se ejecuta después de que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el adaptador pasando el contexto y la lista de personas
        personaAdapter = personaAdapter(requireContext(), gridlistaPersonas)

        // Asigna el adaptador al GridView para mostrar los datos
        binding.gvMain.adapter = personaAdapter

        // Configura el listener para detectar clicks en los items del GridView
        binding.gvMain.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                // Obtiene la persona en la posición clickeada
                val persona = gridlistaPersonas[position]
                // Según el ciclo de la persona, muestra un Toast diferente
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

    // Método que se ejecuta cuando la vista va a ser destruida
    override fun onDestroyView() {
        super.onDestroyView()
    }
}
