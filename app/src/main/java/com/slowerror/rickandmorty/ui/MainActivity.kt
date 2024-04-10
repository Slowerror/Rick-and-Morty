package com.slowerror.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.customview.widget.Openable
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.slowerror.rickandmorty.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
    }

    private fun setupActionBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navItem = findViewById<NavigationView>(R.id.navView)

        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout = drawerLayout)

        setupActionBarWithNavController(navController, appBarConfig)
        navItem.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()

    }
}