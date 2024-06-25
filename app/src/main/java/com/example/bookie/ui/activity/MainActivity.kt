package com.example.bookie.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.bookie.R
import com.example.bookie.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var navegation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.bottomNavigationView
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHost
        navController = navHost.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.inicioFragment, R.id.fragmentApiInicio, R.id.subirLibroFragment, R.id.contactosFragment, R.id.usuarioFragment
            )
        )
        navView.setupWithNavController(navController)

        /*
        * configuracuón del menú para que la navegación se pueda realizar correctamente. De esta manera,
        * aunque se haya navegado a otro fragment desde el iniciar que se encuentra en el menú,
        * se podrá volver al inicial
        *  */

        // se configura un listener para manejar la navegación cuando se selecciona un ítem del menú
        navView.setOnNavigationItemSelectedListener { item ->
        // se verifica el ID del ítem seleccionado y realiza la navegación correspondiente
            when (item.itemId) {
                R.id.inicioFragment,
                R.id.fragmentApiInicio,
                R.id.subirLibroFragment,
                R.id.contactosFragment,
                R.id.usuarioFragment -> {
                    // se verifica si el destino actual es diferente al nuevo destino seleccionado
                if (navController.currentDestination?.id != item.itemId) {
                    // se navega al nuevo destino
                    navController.navigate(item.itemId)
                }
                    // se ha manejado la selección del ítem de menú
                true
            }
                // si el id del ítem no coincide con ninguno de los fragmentos anteriores, se devuelve false
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.detallesLibroFragment)
                binding.bottomNavigationView.visibility = View.GONE
            else
                binding.bottomNavigationView.visibility = View.VISIBLE
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



}

