package com.example.examen.Fragmentos.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.examen.Miscelanea.DBlogin
import com.example.examen.R
import com.example.examen.databinding.FragmentLoginInicioBinding

// Esta es la pantalla de login inicial, donde el usuario puede registrarse o acceder a la aplicación
class LoginInicio : Fragment() {
    // Variable nullable para el binding, se inicializa en onCreateView y se limpia en onDestroyView
    private var _binding: FragmentLoginInicioBinding? = null

    // Propiedad que devuelve el binding de forma segura (!!), solo debe usarse cuando sabemos que no es null
    private val binding get() = _binding!!

    // Método llamado al crear el fragmento, se ejecuta una sola vez
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // No es necesaria inicialización aquí
    }

    // Método que se ejecuta para crear la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el layout usando View Binding y guarda la referencia
        _binding = FragmentLoginInicioBinding.inflate(inflater, container, false)
        // Retorna la vista raíz del binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Creamos la instancia del helper de la BD (no modificamos DBlogin)
        val dbHelper = DBlogin(requireContext())

        // Configuramos los botones registrar y acceder
        binding.btRegistrar.setOnClickListener {
            // Navegamos a la pantalla de registro usando NavController
            findNavController().navigate(R.id.registrarLogin)
        }

        binding.btAcceder.setOnClickListener {
            val inputUser = binding.editTextUsuario.text.toString().trim()
            val inputPassword = binding.editTextTextPassword.text.toString().trim()

            // Comprueba si los campos están vacíos.
            if (inputUser.isEmpty() || inputPassword.isEmpty()) {
                showDialog("Debes rellenar todos los campos")
                return@setOnClickListener
            }

            // Validación usando una consulta directa sobre la BD proporcionada por DBlogin
            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery(
                "SELECT id FROM usuario WHERE nombre = ? AND contrasena = ?",
                arrayOf(inputUser, inputPassword)
            )

            val valido = cursor.moveToFirst()
            cursor.close()
            db.close()

            if (valido) { // Si las credenciales son válidas, navega al fragment de inicio.
                // Navegar al fragment de inicio usando NavController
                // popUpTo elimina loginInicio del back stack para que no pueda volver atrás
                findNavController().navigate(R.id.inicio)
            } else {
                // Si las credenciales no son válidas, muestra un mensaje de error.
                showDialog("Usuario o contraseña incorrectos")
            }
        }
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Evitamos fugas de memoria
        _binding = null
    }
}