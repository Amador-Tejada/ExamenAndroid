package com.example.examen.Fragmentos.navigationComponentObject
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.examen.R
import com.example.examen.databinding.FragmentSegundoBinding

class SegundoFragment : Fragment() {

    // Binding para acceder a las vistas del layout de forma segura
    private lateinit var binding: FragmentSegundoBinding

    // onCreate: ciclo de vida del fragmento; se mantiene por claridad aunque no se use aquí
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // onCreateView: inflar el layout usando view binding y devolver la vista raíz
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inicializa el binding con el inflater y el contenedor
        binding = FragmentSegundoBinding.inflate(inflater, container, false)
        return binding.root
    }

    // onViewCreated: aquí se manipulan las vistas y se recuperan los argumentos
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener NavController para poder navegar a otros destinos
        val navController = Navigation.findNavController(view)

        // Recuperar los argumentos pasados mediante Safe Args.
        // Nota: requireArguments() lanzará una excepción si no hay Bundle;
        // esto asume que la navegación siempre pasa los argumentos esperados.
        val args = SegundoFragmentArgs.fromBundle(requireArguments())

        // Extraer valores de los argumentos generados por Safe Args
        val nombre = args.nombre
        val apellido = args.apellido
        val telefono = args.telefono

        // Asignar los valores a los TextView del layout
        binding.tvNombre.text = nombre
        binding.tvApellido.text = apellido
        binding.tvTelefono.text = telefono.toString()

        // Configurar el botón para navegar al siguiente fragmento usando el ID de la acción
        binding.button.setOnClickListener {
            // Asegúrate de que el ID de la acción coincida con el definido en el nav graph
            navController.navigate(R.id.action_SegundoFragment_to_FinallFragment)
        }
    }
}