package com.example.examen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.examen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // PASO 1: Inflamos el layout usando ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // PASO 2: Configuramos el Toolbar como ActionBar
        setSupportActionBar(binding.toolbar)

        // PASO 3: Obtenemos el NavController desde el NavHostFragment
        // El NavController gestiona la navegación entre fragmentos
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // PASO 4: Definimos los fragmentos de nivel superior
        // Estos fragmentos mostrarán el icono de hamburguesa en lugar de la flecha de retroceso
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.gridview,
                R.id.api,
                R.id.lista,
                R.id.recyclerView,
            ),

            binding.drawerLayout // Pasamos el DrawerLayout para que funcione el menú
        )
        // PASO 5: Conectamos el ActionBar con el NavController
        // Esto hace que el título cambie automáticamente según el fragmento
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Ocultar el título al lado del icono de hamburguesa (solo mostrar el icono)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // PASO 6: Conectamos el NavigationView con el NavController
        // Esto hace que al pulsar un item del menú, navegue al fragmento correspondiente
        binding.navigationView.setupWithNavController(navController)
    }

    // Este método se llama cuando pulsáis la flecha/icono de menú en el ActionBar
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // navigateUp gestiona la navegación hacia atrás o abre el drawer
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
