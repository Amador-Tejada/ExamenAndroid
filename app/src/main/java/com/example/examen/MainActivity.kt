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

// Actividad principal que gestiona la navegación entre fragmentos usando Navigation Component
// Implementa un DrawerLayout (menú lateral) con NavigationView
class MainActivity : AppCompatActivity() {

    // View Binding para acceder a las vistas del layout de forma segura
    private lateinit var binding: ActivityMainBinding

    // Configuración que define qué fragmentos son de nivel superior (mostrarán icono de menú en lugar de flecha atrás)
    private lateinit var appBarConfiguration: AppBarConfiguration

    // Método que se ejecuta al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // PASO 1: Inflamos el layout usando ViewBinding
        // Esto genera una clase con referencias a todas las vistas del XML
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Establece la vista raíz del binding como contenido de la actividad
        setContentView(binding.root)

        // PASO 2: Configuramos el Toolbar como ActionBar
        // Esto permite mostrar el título y los botones de navegación en la parte superior
        setSupportActionBar(binding.toolbar)

        // PASO 3: Obtenemos el NavController desde el NavHostFragment
        // El NavController gestiona la navegación entre fragmentos
        // NavHostFragment es el contenedor que aloja los fragmentos definidos en el grafo de navegación
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // PASO 4: Definimos los fragmentos de nivel superior
        // Estos fragmentos mostrarán el icono de hamburguesa (menú) en lugar de la flecha de retroceso
        // Corresponden a las opciones principales del NavigationView
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.inicio,          // Fragmento de inicio/bienvenida
                R.id.apiRestRandom,   // Fragmento que muestra imágenes aleatorias de perros
                R.id.apiRestBoton,    // Fragmento con botón para cargar imágenes aleatorias
                R.id.gridview,        // Fragmento con GridView de personas
                R.id.api,             // Fragmento de búsqueda de perros por raza
                R.id.lista,           // Fragmento con ListView (probablemente de personas)
                R.id.spinner,    // Fragmento con RecyclerView (probablemente de personas)
                R.id.inicioComponentFragment, // Fragmento de inicio para componentes personalizado
                R.id.radioButton,
                R.id.sharePreferences// Fragmento con RadioButton (probablemente de personas)

            ),
            binding.drawerLayout // Pasamos el DrawerLayout para que funcione el menú lateral
        )

        // PASO 5: Conectamos el ActionBar con el NavController
        // Esto hace que el título del ActionBar cambie automáticamente según el fragmento actual
        // También gestiona el icono de navegación (hamburguesa o flecha atrás)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Ocultar el título al lado del icono de hamburguesa (solo mostrar el icono)
        // Útil si se quiere un diseño más minimalista o el título se muestra en otro lugar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // PASO 6: Conectamos el NavigationView con el NavController
        // Esto hace que al pulsar un item del menú lateral, navegue automáticamente al fragmento correspondiente
        // Los IDs de los items del menú deben coincidir con los IDs de los fragmentos en el grafo de navegación
        binding.navigationView.setupWithNavController(navController)
    }

    // Este método se llama cuando se pulsa el icono de menú/flecha en el ActionBar
    // Gestiona la navegación hacia atrás o la apertura del menú lateral
    override fun onSupportNavigateUp(): Boolean {
        // Obtenemos nuevamente el NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // navigateUp gestiona la navegación hacia atrás o abre el drawer según el contexto
        // Si estamos en un fragmento de nivel superior, abre el drawer
        // Si estamos en un fragmento hijo, navega hacia atrás
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
