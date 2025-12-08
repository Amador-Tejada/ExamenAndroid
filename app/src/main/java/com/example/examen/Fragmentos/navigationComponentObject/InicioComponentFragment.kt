package com.example.examen.Fragmentos.navigationComponentObject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.examen.R
import com.example.examen.databinding.FragmentInicioBinding
import com.example.examen.databinding.FragmentInicioComponentBinding


class InicioComponentFragment : Fragment() {

    private lateinit var binding: FragmentInicioComponentBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        // Iniciamos el binding
        binding = FragmentInicioComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = view.findNavController() // Obtenemos el NavController


        binding.bt1.setOnClickListener {


            if (!camposValidos()) {
                requireContext().mostrarToast("Rellena todos los campos")
            } else {
                try {
                    val persona = Persona(
                        binding.edNombre.text.toString(),
                        binding.edApellido.text.toString(),
                        binding.edTelefono.text.toString().toInt()
                    )
                    val action = InicioComponentFragmentDirections.actionInicioComponentFragmentToSegundoFragment(
                        persona.nombre,
                        persona.apellido,
                        persona.numero
                    )
                    // Navegación directa usando el id de la acción definido en el navgraph
                    navController.navigate(action)
                } catch (e: Exception) {
                    requireContext().mostrarToast("Rellena todos los campos")
                }
            }
        }
    }

    private fun camposValidos(): Boolean =
        binding.edNombre.text.toString().trim().isNotEmpty() &&
                binding.edApellido.text.toString().trim().isNotEmpty() &&
                binding.edTelefono.text.toString().trim().isNotEmpty()

    private fun Context.mostrarToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

}