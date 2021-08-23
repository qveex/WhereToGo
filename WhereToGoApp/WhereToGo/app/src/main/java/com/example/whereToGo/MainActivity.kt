package com.example.whereToGo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


// https://developers.google.com/maps
// https://developers.google.com/maps/documentation/android-sdk/start#None-kotlin
// https://console.cloud.google.com/google/maps-apis/credentials?project=kuda-poyti
// https://github.com/googlemaps/android-samples


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.mainFragment))
        bottomNavigationView.setupWithNavController(findNavController(R.id.mainFragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.mainFragment)
        return (navController.navigateUp() || super.onSupportNavigateUp())
    }
}