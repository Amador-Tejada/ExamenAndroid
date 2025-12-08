package com.example.examen.Fragmentos.navigationComponentObject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.examen.R
import com.example.examen.databinding.FragmentSegundoBinding
import kotlin.toString

class SegundoFragment : Fragment() {

    private lateinit var binding: FragmentSegundoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSegundoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        val args = SegundoFragmentArgs.fromBundle(requireArguments())

        val nombre = args.nombre
        val apellido = args.apellido
        val telefono = args.telefono

        binding.tvNombre.text = nombre
        binding.tvApellido.text = apellido
        binding.tvTelefono.text = telefono.toString()


        binding.button.setOnClickListener {
            // Navegar usando el ID de la acción en el nav graph
            navController.navigate(R.id.action_SegundoFragment_to_FinallFragment)
        }
    }
}