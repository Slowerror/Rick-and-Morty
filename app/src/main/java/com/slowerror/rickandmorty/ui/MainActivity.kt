package com.slowerror.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.navigation.NavController
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
    private val drawerLayout: DrawerLayout by lazy { findViewById(R.id.drawerLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()

        val closeDrawerCallback = onBackPressedDispatcher.addCallback(this@MainActivity, false) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        drawerLayout.addDrawerListener(object : DrawerListener {

            override fun onDrawerOpened(drawerView: View) {
                closeDrawerCallback.isEnabled = true
            }

            override fun onDrawerClosed(drawerView: View) {
                closeDrawerCallback.isEnabled = false
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit
            override fun onDrawerStateChanged(newState: Int) = Unit

        })
    }

    private fun setupActionBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        val navItem = findViewById<NavigationView>(R.id.navView)

        appBarConfig = AppBarConfiguration(
            setOf(
                R.id.characterListFragment,
                R.id.episodeListFragment,
                R.id.searchCharacterListFragment
            ),
            drawerLayout = drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfig)
        navItem.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}