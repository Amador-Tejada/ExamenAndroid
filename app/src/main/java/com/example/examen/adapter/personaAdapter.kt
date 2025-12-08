package com.example.examen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.examen.R
import com.example.examen.databinding.ItemPersonaBinding


class personaAdapter (
    context: Context,
    private val listaPersona: List<tipoPersona>
) : ArrayAdapter<tipoPersona>(context, 0, listaPersona) {

    // Inflar la vista para cada elemento del GridView
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            ItemPersonaBinding.inflate(inflater, parent, false)
        } else {
            ItemPersonaBinding.bind(convertView)
        }

        // Obtener la persona actual
        val persona = listaPersona[position]
        binding.nombre.text = persona.nombre // Asignar el nombre de la persona al TextView
        binding.apellidos.text = persona.apellidos // Asignar los apellidos de la persona al TextView
        binding.ciclo.text = persona.ciclo // Asignar el ciclo de la persona al TextView

        // Asignar la imagen según el sexo de la persona
        val imagenId = if (persona.sexo == "Hombre") {
            R.drawable.hombre
        } else {
            R.drawable.mujer
        }
        binding.imagen.setImageResource(imagenId)
        return binding.root
    }
}
