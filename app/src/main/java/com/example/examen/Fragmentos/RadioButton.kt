package com.example.examen.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.examen.R
import com.example.examen.databinding.FragmentRadioButtonBinding

class RadioButton : Fragment() {

    private lateinit var binding: FragmentRadioButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRadioButtonBinding.inflate(inflater, container, false)

        // Mover el listener antes del return para que se ejecute
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            resultado()
        }

        return binding.root

    }

    // Función para calcular el resultado en función de la operación seleccionada
    private fun resultado() {
        // Intentamos obtener los valores de los campos de texto y usamos un
        // try-catch para controlar los errores de campo vacío, división por cero
        // y cualquier otro error inesperado
        try {
            val valor1 = binding.edValor1.text.toString().toInt()
            val valor2 = binding.edValor2.text.toString().toInt()


            // El ID del RadioButton seleccionado ya lo tenemos gracias al listener
            val total = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.rbSuma -> valor1 + valor2
                R.id.rbResta -> valor1 - valor2
                R.id.rbMulti -> valor1 * valor2
                R.id.rbdivi -> {
                    // Manejo de la división por cero para evitar que la app crashe
                    if (valor2 == 0) {
                        throw ArithmeticException("División por cero no es posible")
                    }
                    valor1 / valor2
                }
                else -> 0 // Caso por defecto, aunque no debería ocurrir
            }
            // Mostramos el resultado en el TextView
            binding.tvResultado.text = total.toString()
        } catch (_: NumberFormatException) {
            // Este error ocurre si los campos están vacíos
            binding.tvResultado.text = getString(R.string.ingrese_valores_validos)
        } catch (_: ArithmeticException) {
            // Este error es para la división por cero
            binding.tvResultado.text = getString(R.string.error_div_por_cero)
        } catch (_: Exception) {
            // Captura cualquier otro error inesperado
            binding.tvResultado.text = getString(R.string.error_generico)
        }
    }
}
