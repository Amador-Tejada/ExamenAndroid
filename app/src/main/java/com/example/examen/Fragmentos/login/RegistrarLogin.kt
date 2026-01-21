package com.example.examen.Fragmentos.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.examen.Miscelanea.DBlogin
import com.example.examen.R
import com.example.examen.databinding.FragmentRegistrarLoginBinding

// Pantalla que se centra en la creacion de usuarios para acceder a la app
class RegistrarLogin : Fragment() {
    // iniciamos el binding
    private lateinit var binding: FragmentRegistrarLoginBinding

    // inflamos el layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrarLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DBlogin(requireContext())

        // añadimos el listener al boton aceptar
        binding.btAceptar.setOnClickListener {
            // guardamos los datos en variables
            val nombre = binding.editTextNombre.text.toString().trim()
            val contrasena = binding.editTextApellidos.text.toString().trim()

            if (nombre.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // intentamos insertar el usuario en la BD
            val id = db.insertarUsuario(nombre, contrasena)
            if (id == -1L) {
                Toast.makeText(requireContext(), "Error: el usuario ya existe o no se pudo registrar", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                // limpiamos los campos
                binding.editTextNombre.text.clear()
                binding.editTextApellidos.text.clear()
                // volvemos a la pantalla de inicio (login)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        binding.btCancelar.setOnClickListener {
            // limpiamos los campos
            binding.editTextNombre.text.clear()
            binding.editTextApellidos.text.clear()
            // volvemos a la pantalla de inicio
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
