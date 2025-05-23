package com.example.currencyconverter.presentation.navigation

import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {
    lateinit var navController: NavController
        private set

    fun setNavController(controller: NavController) {
        this.navController = controller
    }
}