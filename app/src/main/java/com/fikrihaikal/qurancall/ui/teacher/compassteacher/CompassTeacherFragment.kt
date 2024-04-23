package com.fikrihaikal.qurancall.ui.teacher.compassteacher

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentCompassBinding
import com.fikrihaikal.qurancall.databinding.FragmentCompassTeacherBinding

class CompassTeacherFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentCompassTeacherBinding? = null
    private val binding get() = _binding!!

    //record compass picture angle turned
    private var currentDegree = 0f
    //device sensor manager
    private var mSensorManager: SensorManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompassTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        mSensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree = Math.round(event?.values?.get(0)!!)
        val rotateAnimation = RotateAnimation(
            currentDegree, (-degree).toFloat(), Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF,0.5f
        )
        rotateAnimation.duration = 210
        rotateAnimation.fillAfter = true

        binding.ivCompass.startAnimation(rotateAnimation)
        currentDegree = (-degree).toFloat()

        if (currentDegree != 0f){
            binding.cv1.visibility = View.GONE
        }else{
            binding.cv1.visibility = View.VISIBLE
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onResume() {
        super.onResume()
        @Suppress("DEPRECATION")
        mSensorManager?.registerListener(
            this,
            mSensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

}