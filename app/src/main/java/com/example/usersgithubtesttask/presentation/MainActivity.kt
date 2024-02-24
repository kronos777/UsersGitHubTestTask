package com.example.usersgithubtesttask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.usersgithubtesttask.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (this.supportFragmentManager.findFragmentById(R.id.fragment_item_container) as NavHostFragment).navController
    }

    val bottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.nav_view_bottom)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigationJetpack()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            onDestinationChanged(destination.id)
        }
    }

    private fun onDestinationChanged(currentDestination: Int) {
        try {
            when(currentDestination) {
                R.id.listOrgRepositoryFragment -> {
                    activeRepoBottomNav()
                }
                R.id.repositoryItemFragment -> {
                    activeRepoBottomNav()
                }
                R.id.listUserFragment -> {
                    activeUserBottomNav()
                }
                R.id.userItemFragment -> {
                    activeUserBottomNav()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun activeRepoBottomNav() {
        this.findViewById<BottomNavigationView>(R.id.nav_view_bottom)
        bottomNavigationView.menu.findItem(R.id.bottomItem2).isChecked = true
    }

    private fun activeUserBottomNav() {
        this.findViewById<BottomNavigationView>(R.id.nav_view_bottom)
        bottomNavigationView.menu.findItem(R.id.bottomItem1).isChecked = true
    }
    private fun initBottomNavigationJetpack() {
        setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.bottomItem1 -> {
                    navController.navigate(R.id.listUserFragment, null, null)
                }
                R.id.bottomItem2 -> {
                    navController.navigate(R.id.listOrgRepositoryFragment, null, null)
                }

            }
            true
        }

    }
}