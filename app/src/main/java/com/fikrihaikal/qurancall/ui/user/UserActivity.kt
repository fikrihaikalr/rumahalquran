package com.fikrihaikal.qurancall.ui.user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private var _binding : ActivityUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            binding.fragmentContainerViewUser.getFragment<Fragment>() as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNavUser.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            Log.d("Navigation", "Navigating to destination: ${destination.label}")
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
            binding.bottomNavUser.visibility = View.GONE
        }else{
            binding.bottomNavUser.visibility = View.VISIBLE
        }
    }
}