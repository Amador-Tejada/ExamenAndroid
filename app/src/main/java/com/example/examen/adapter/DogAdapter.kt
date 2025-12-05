package com.example.examen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.R

/**
 * Adaptador del RecyclerView que gestiona la lista de imágenes de perros
 *
 * @param images Lista de URLs de imágenes que se mostrarán en el RecyclerView
 */
class DogAdapter(private val images: List<String>) : RecyclerView.Adapter<DogViewHolder>() {
    /**
     * Crea nuevas instancias de ViewHolder cuando el RecyclerView las necesita
     * Solo se crean las vistas visibles en pantalla (más algunas extra para scroll suave)
     *
     * @param parent El ViewGroup contenedor (el RecyclerView)
     * @param viewType Tipo de vista (útil cuando hay diferentes tipos de items)
     * @return Un nuevo DogViewHolder con la vista inflada
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    /**
     * Vincula los datos con un ViewHolder existente que se está reutilizando
     * Se llama cada vez que un item se hace visible en pantalla
     *
     * @param holder El ViewHolder que se va a reutilizar
     * @param position La posición del item en la lista
     */
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item: String = images[position]
        holder.bind(item)
    }

    /**
     * Informa al RecyclerView cuántos elementos tiene la lista
     *
     * @return El número total de items a mostrar
     */
    override fun getItemCount(): Int = images.size
}