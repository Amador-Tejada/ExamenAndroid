package com.example.examen.Fragmentos.navigationComponentObject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.examen.R
import com.example.examen.databinding.FragmentFinalBinding

class FinalFragment : Fragment() {

    // Binding generado por ViewBinding para acceder a las vistas del layout de forma segura
    private lateinit var binding: FragmentFinalBinding

    // onCreate: método del ciclo de vida del Fragment.
    // Aquí se puede inicializar lógica no relacionada con la vista.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // onCreateView: inflamos el layout del Fragment usando ViewBinding
    // y devolvemos la vista raíz para que el sistema la dibuje.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inicializar binding con el inflater y el contenedor
        binding = FragmentFinalBinding.inflate(inflater, container, false)
        // Devolver la vista raíz asociada al binding
        return binding.root
    }

    // onViewCreated: aquí las vistas ya están creadas; se configuran listeners y navegación.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener NavController a partir de la vista para realizar acciones de navegación
        val navController = view.findNavController()

        // Configurar el listener del botón "btVolver" para navegar al fragmento de inicio.
        // El ID de la acción debe coincidir con el definido en el nav graph.
        binding.btVolver.setOnClickListener {
            navController.navigate(R.id.action_finalFragment_to_inicioComponentFragment)
        }

    }
}