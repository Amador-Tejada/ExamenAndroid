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

    // Binding generado por view binding para acceder a las vistas del layout
    private lateinit var binding: FragmentInicioComponentBinding

    // onCreate: ciclo de vida del fragmento, aquí no se usa pero se mantiene por claridad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // onCreateView: inflamos el layout usando view binding y devolvemos la vista raíz
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        // Inicializar el binding con el inflater y el contenedor
        binding = FragmentInicioComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // onViewCreated: aquí se pueden manipular las vistas y configurar listeners
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el NavController asociado a esta vista para realizar la navegación
        val navController = view.findNavController()

        // Listener del botón (bt1) para iniciar la navegación al segundo fragmento
        binding.bt1.setOnClickListener {

            // Validar que los campos no estén vacíos antes de intentar crear la persona
            if (!camposValidos()) {
                // Mostrar un toast si faltan campos
                requireContext().mostrarToast("Rellena todos los campos")
            } else {
                try {
                    // Crear un objeto Persona tomando los valores de los EditText
                    // Se convierte el teléfono a Int con toInt()
                    val persona = Persona(
                        binding.edNombre.text.toString(),
                        binding.edApellido.text.toString(),
                        binding.edTelefono.text.toString().toInt()
                    )

                    // Crear la acción de navegación generada por Safe Args
                    // actionInicioComponentFragmentToSegundoFragment recibe los argumentos definidos en el nav graph
                    val action = InicioComponentFragmentDirections.actionInicioComponentFragmentToSegundoFragment(
                        persona.nombre,
                        persona.apellido,
                        persona.numero
                    )

                    // Navegar pasando la acción (que contiene los args)
                    navController.navigate(action)
                } catch (e: Exception) {
                    // Si ocurre cualquier excepción (por ejemplo, parseo de Int), mostrar un mensaje
                    requireContext().mostrarToast("Rellena todos los campos")
                }
            }
        }
    }

    // Función auxiliar para comprobar que los tres campos tienen texto
    private fun camposValidos(): Boolean =
        binding.edNombre.text.toString().trim().isNotEmpty() &&
                binding.edApellido.text.toString().trim().isNotEmpty() &&
                binding.edTelefono.text.toString().trim().isNotEmpty()

    // Extensión privada para mostrar Toast desde el Context de forma concisa
    private fun Context.mostrarToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

}