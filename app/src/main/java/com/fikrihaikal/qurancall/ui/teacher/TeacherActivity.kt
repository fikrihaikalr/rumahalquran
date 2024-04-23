package com.fikrihaikal.qurancall.ui.teacher

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.ActivityTeacherBinding
import com.fikrihaikal.qurancall.databinding.ActivityUserBinding

class TeacherActivity : AppCompatActivity() {
    private var _binding : ActivityTeacherBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            binding.fragmentContainerViewTeacher.getFragment<Fragment>() as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNavTeacher.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            Log.d("Navigation", "Navigating to destination: ${destination.label}")
            when(destination.id){
                R.id.homeTeacherFragment -> hideBottom(false)
                R.id.waktuAdzanTeacherFragment -> hideBottom(false)
                R.id.compassTeacherFragment -> hideBottom(false)
                R.id.alquranTeacherFragment -> hideBottom(false)
                R.id.profileTeacherFragment -> hideBottom(false)
                else -> hideBottom(true)
            }
        }
    }

    private fun hideBottom(hide: Boolean) {
        if (hide){
            binding.bottomNavTeacher.visibility = View.GONE
        }else{
            binding.bottomNavTeacher.visibility = View.VISIBLE
        }
    }
}