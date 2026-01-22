package com.example.examen.Fragmentos.GridviewConDB

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.examen.Miscelanea.DBdataGridview
import com.example.examen.R
import com.example.examen.adapter.tipoPersona
import com.example.examen.databinding.FragmentIngresoDatosBinding

class IngresoDatos : Fragment() {

    private lateinit var binding : FragmentIngresoDatosBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingreso_datos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIngresoDatosBinding.bind(view)

        // Depuración: indicar que el fragment se ha creado
        Toast.makeText(requireContext(), "IngresoDatos abierto", Toast.LENGTH_SHORT).show()

        // recogemos los datos de la pantalla de ingresoDatos y los guardamos en la base de datos DBGridViewDB()
        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val apellidos = binding.etApellidos.text.toString()
            val sexo = binding.etSexo.text.toString()
            val ciclo = binding.etCiclo.text.toString()
            val persona = tipoPersona(nombre, apellidos, sexo, ciclo)

            // Usar una instancia de DBdataGridview para insertar
            val dbHelper = DBdataGridview(requireContext())
            val id = dbHelper.insertarUsuario(persona)
            if (id == -1L) {
                Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Guardado", Toast.LENGTH_SHORT).show()
            }
            dbHelper.close()

            // Navegar de regreso al GridView que muestra los datos guardados
            try {
                findNavController().navigate(R.id.action_ingresoDatos_to_GridViewDB)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error navegación: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCancelar.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_ingresoDatos_to_GridViewDB)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error navegación: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

}