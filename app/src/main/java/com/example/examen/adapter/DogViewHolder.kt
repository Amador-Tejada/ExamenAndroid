package com.example.examen.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

/**
 * ViewHolder que gestiona cada elemento individual del RecyclerView
 *
 * @param view La vista inflada que representa un elemento de la lista
 */
class DogViewHolder(view: View): RecyclerView.ViewHolder(view) {

    /**
     * ViewBinding generado automáticamente desde el layout item_dog.xml
     * Permite acceder a las vistas sin usar findViewById
     */
    private val binding = ItemDogBinding.bind(view)

    /**
     * Vincula los datos (URL de la imagen) con las vistas
     *
     * @param image URL de la imagen del perro que se va a mostrar
     */
    fun bind(image:String){
        try {
            if (image.isBlank()) {
                // Si la URL está vacía, mostrar placeholder local
                Picasso.get()
                    .load(com.example.examen.R.drawable.ic_launcher_foreground)
                    .fit()
                    .centerCrop()
                    .into(binding.imageViewDog)
            } else {
                Picasso.get()
                    .load(image)
                    .fit()
                    .centerCrop()
                    .placeholder(com.example.examen.R.drawable.ic_launcher_foreground)
                    .error(com.example.examen.R.drawable.ic_launcher_foreground)
                    .into(binding.imageViewDog)
            }
        } catch (e: Exception) {
            // En caso de cualquier error al cargar, usar placeholder para evitar crash
            Picasso.get()
                .load(com.example.examen.R.drawable.ic_launcher_foreground)
                .into(binding.imageViewDog)
        }
    }
}