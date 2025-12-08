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

// Fragmento de inicio/bienvenida de la aplicación
class Inicio : Fragment() {

    // Variable nullable para el binding, se inicializa en onCreateView y se limpia en onDestroyView
    private var _binding: FragmentInicioBinding? = null

    // Propiedad que devuelve el binding de forma segura (!!), solo debe usarse cuando sabemos que no es null
    private val binding get() = _binding!!

    // Método llamado al crear el fragmento, se ejecuta una sola vez
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Este método está vacío, no se necesita inicialización aquí
    }

    // Método que se ejecuta para crear la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout usando View Binding y guarda la referencia
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        // Retorna la vista raíz del binding
        return binding.root
    }

    // Método que se ejecuta después de que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Aquí se configurarían listeners, inicializarían componentes, etc.
        // Actualmente vacío - sin funcionalidad adicional
    }

    // Método que se ejecuta cuando la vista va a ser destruida
    override fun onDestroyView() {
        super.onDestroyView()
        // Limpia la referencia del binding para evitar memory leaks
        _binding = null
    }
}