package com.fikrihaikal.qurancall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fikrihaikal.qurancall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            binding.fragmentContainerView.getFragment<Fragment>() as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.homeFragment -> hideBottom(false)
                R.id.waktuAdzanFragment -> hideBottom(false)
                R.id.compassFragment -> hideBottom(false)
                R.id.alquranFragment -> hideBottom(false)
                R.id.profileFragment -> hideBottom(false)
                else -> hideBottom(true)
            }
        }
    }

    private fun hideBottom(hide: Boolean) {
        if (hide){
            binding.bottomNav.visibility = View.GONE
        }else{
            binding.bottomNav.visibility = View.VISIBLE
        }
    }
}