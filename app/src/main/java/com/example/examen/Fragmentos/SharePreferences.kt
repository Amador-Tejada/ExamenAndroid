package com.example.examen.Fragmentos

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.example.examen.databinding.FragmentSharePreferencesBinding

class SharePreferences : Fragment() {

    // El 'lateinit' promete que inicializaremos esta variable más tarde (en onCreateView)
    private lateinit var binding: FragmentSharePreferencesBinding
    private lateinit var sharedPref: SharedPreferences

    // companion object: es el lugar perfecto para constantes. Así evitamos errores
    // al escribir las claves a mano y mantenemos el código limpio.
    companion object {
        private const val PREF_NAME = "UserPreferences"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_AGE = "age"
        private const val KEY_NOTIFICATIONS = "notifications_enabled"
        private const val KEY_VOLUME = "volume"
    }

    /**
     * onCreateView: Su ÚNICA responsabilidad es crear y devolver la vista del fragmento.
     * Aquí es donde se "infla" el layout y se inicializa el 'binding'.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSharePreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * onViewCreated: ¡EL LUGAR CORRECTO PARA LA LÓGICA!
     * Este método se ejecuta justo después de onCreateView, cuando la vista ya está creada
     * y es seguro acceder al 'binding' y al contexto del fragmento.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Ahora es seguro obtener SharedPreferences porque tenemos un contexto válido.
        // requireContext() es la forma segura de obtener el contexto en un Fragment.
        sharedPref = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // 2. Ahora sí podemos llamar a nuestros métodos que usan 'binding' y 'sharedPref'.
        loadSavedData()
        setupListeners()
    }

    /**
     * Carga los datos desde SharedPreferences y los muestra en los controles de la UI.
     */
    private fun loadSavedData() {
        val userName = sharedPref.getString(KEY_USER_NAME, "") ?: ""
        binding.etUserName.setText(userName)

        val age = sharedPref.getInt(KEY_AGE, 0)
        if (age > 0) {
            binding.etAge.setText(age.toString())
        }

        val notificationsEnabled = sharedPref.getBoolean(KEY_NOTIFICATIONS, true)
        binding.switchNotifications.isChecked = notificationsEnabled

        val volume = sharedPref.getFloat(KEY_VOLUME, 50f)
        binding.seekBarVolume.progress = volume.toInt()
        binding.tvVolumeValue.text = "${volume.toInt()}%"

        if (userName.isNotEmpty()) {
            binding.tvInfo.text = "¡Bienvenido de nuevo, $userName!"
        } else {
            binding.tvInfo.text = "Introduce tus datos y presiona Guardar"
        }
    }

    /**
     * Configura todos los listeners para los botones y otros controles interactivos.
     */
    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            saveData()
        }

        binding.btnClear.setOnClickListener {
            clearAllData()
        }

        binding.seekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvVolumeValue.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    /**
     * Recoge los datos de la UI, los valida y los guarda en SharedPreferences.
     */
    private fun saveData() {
        val userName = binding.etUserName.text.toString()
        val ageText = binding.etAge.text.toString()
        val notificationsEnabled = binding.switchNotifications.isChecked
        val volume = binding.seekBarVolume.progress.toFloat()

        if (userName.isEmpty()) {
            // 3. Usamos requireContext() para mostrar Toasts en un Fragment.
            Toast.makeText(requireContext(), "Por favor, introduce un nombre", Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageText.toIntOrNull()
        if (age == null || age <= 0) {
            Toast.makeText(requireContext(), "Por favor, introduce una edad válida", Toast.LENGTH_SHORT).show()
            return
        }

        sharedPref.edit().apply {
            putString(KEY_USER_NAME, userName)
            putInt(KEY_AGE, age)
            putBoolean(KEY_NOTIFICATIONS, notificationsEnabled)
            putFloat(KEY_VOLUME, volume)
            apply() // Guardado asíncrono, no bloquea la UI.
        }

        Toast.makeText(requireContext(), "✓ Datos guardados correctamente", Toast.LENGTH_SHORT).show()
        binding.tvInfo.text = "Datos de $userName guardados permanentemente"
    }

    /**
     * Borra todas las preferencias guardadas y reinicia la interfaz a sus valores por defecto.
     */
    private fun clearAllData() {
        sharedPref.edit().clear().apply()

        binding.etUserName.setText("")
        binding.etAge.setText("")
        binding.switchNotifications.isChecked = true
        binding.seekBarVolume.progress = 50
        binding.tvVolumeValue.text = "50%"
        binding.tvInfo.text = "Todos los datos han sido eliminados"

        Toast.makeText(requireContext(), "✓ Datos eliminados", Toast.LENGTH_SHORT).show()
    }

    /**
     * Demostración: verificar si existe una clave
     */
    private fun checkIfUserExists(): Boolean {
        return sharedPref.contains(KEY_USER_NAME)
    }

    /**
     * Demostración: obtener todas las preferencias
     */
    private fun getAllPreferences() {
        val allPrefs = sharedPref.all
        allPrefs.forEach { (key, value) ->
            println("Clave: $key, Valor: $value")
        }
    }
}
