package com.fikrihaikal.qurancall.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fikrihaikal.qurancall.di.Injection
import com.fikrihaikal.qurancall.ui.alquran.viewpager.surah.SurahViewModel
import com.fikrihaikal.qurancall.ui.detaildoa.DetailDoaViewModel
import com.fikrihaikal.qurancall.ui.detailguru.DetailGuruViewModel
import com.fikrihaikal.qurancall.ui.doa.DoaViewModel
import com.fikrihaikal.qurancall.ui.gantipassword.GantiPasswordViewModel
import com.fikrihaikal.qurancall.ui.home.HomeViewModel
import com.fikrihaikal.qurancall.ui.login.LoginViewModel
import com.fikrihaikal.qurancall.ui.pilihguru.PilihGuruViewModel
import com.fikrihaikal.qurancall.ui.profile.ProfileViewModel
import com.fikrihaikal.qurancall.ui.register.RegisterViewModel


class ViewModelFactory(private val context: Context)  : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create (modelClass: Class<T>) : T{
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java)  ->{
                RegisterViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(DoaViewModel::class.java) ->{
                DoaViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(DetailDoaViewModel::class.java) ->{
                DetailDoaViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(GantiPasswordViewModel::class.java) ->{
                GantiPasswordViewModel(Injection.provideRepository(context), Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(PilihGuruViewModel::class.java) ->{
                PilihGuruViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(DetailGuruViewModel::class.java) ->{
                DetailGuruViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(SurahViewModel::class.java) ->{
                SurahViewModel(Injection.provideSurahRepository(context)) as T
            }

            else ->throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

        }

    }

}