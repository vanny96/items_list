package com.training.itemcreator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val toolbarConfig = AppBarConfiguration(navController.graph)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setupWithNavController(navController, toolbarConfig)

    }
}
