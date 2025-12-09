package com.example.examen.Fragmentos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.examen.databinding.FragmentSpinnerBinding
import java.lang.NumberFormatException

/**
 * Un [Fragment] que permite al usuario realizar operaciones aritméticas básicas
 * (suma, resta, multiplicación, división) con dos números.
 */
class Spinner : Fragment() {

    // Instancia de View Binding para este fragmento
    private lateinit var binding: FragmentSpinnerBinding

    /**
     * Infla el diseño para este fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el diseño para este fragmento usando view binding
        binding = FragmentSpinnerBinding.inflate(inflater, container, false)
        // Devuelve la vista raíz
        return binding.root
    }

    /**
     * Se llama cuando la vista del fragmento ha sido creada.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Crea un TextWatcher para escuchar los cambios en los campos de entrada
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se usa
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Cuando el texto cambia, recalcula el resultado
                calculate()
            }

            override fun afterTextChanged(s: Editable?) {
                // No se usa
            }
        }

        // Agrega el TextWatcher a ambos campos de entrada
        binding.edValor1.addTextChangedListener(textWatcher)
        binding.edValor2.addTextChangedListener(textWatcher)

        // Establece un listener para el spinner para realizar el cálculo cuando se selecciona un elemento
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Cuando se selecciona un elemento, recalcula el resultado
                calculate()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Si no se selecciona ningún elemento, muestra el texto predeterminado
                binding.tvResultado.text = "Resultado"
            }
        }
    }

    /**
     * Realiza el cálculo en función de los valores de entrada y la operación seleccionada.
     */
    private fun calculate() {
        // Obtiene el texto de los campos de entrada
        val valor1Str = binding.edValor1.text.toString()
        val valor2Str = binding.edValor2.text.toString()

        // Comprueba si ambos campos de entrada no están vacíos
        if (valor1Str.isNotEmpty() && valor2Str.isNotEmpty()) {
            try {
                // Convierte las cadenas de entrada a enteros
                val valor1 = valor1Str.toInt()
                val valor2 = valor2Str.toInt()
                // Obtiene la operación seleccionada del spinner
                val operacion = binding.spinner.selectedItem.toString()

                // Maneja la división por cero por separado
                if (operacion == "dividir" && valor2 == 0) {
                    binding.tvResultado.text = "No se puede dividir por cero"
                    return // Sale de la función
                }

                // Realiza el cálculo en función de la operación seleccionada
                val resultado: Int = when (operacion) {
                    "sumar" -> valor1 + valor2
                    "restar" -> valor1 - valor2
                    "multiplicar" -> valor1 * valor2
                    "dividir" -> valor1 / valor2 // Nota: esta es una división de enteros
                    else -> 0
                }

                // Muestra el resultado
                binding.tvResultado.text = "Resultado: $resultado"

            } catch (e: NumberFormatException) {
                // Si la entrada no es un número válido, muestra el texto predeterminado
                binding.tvResultado.text = "Resultado"
            }
        } else {
            // Si alguno de los campos de entrada está vacío, muestra el texto predeterminado
            binding.tvResultado.text = "Resultado"
        }
    }
}
