package com.example.tele2demo

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.example.tele2demo.domain.LocalRepository
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val localRepository: LocalRepository by inject()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navGraph: NavGraph
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.statusBarColor = getColor(R.color.black)


        navController = findNavController(R.id.mfs_main_page_container)
        navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        if (localRepository.getDeviceInfo() == null) {
            navGraph.setStartDestination(R.id.FirstFragment)
        } else {
            navGraph.setStartDestination(R.id.mainFragment)

        }
        navController.setGraph(navGraph, null)
    }

    override fun onBackPressed() {
        val currentDestinationId = navController.currentDestination?.id
        if (currentDestinationId == navGraph.startDestinationId || currentDestinationId == R.id.mainFragment)
            finish()
        else
            navController.navigateUp()
    }
}