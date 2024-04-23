package com.fikrihaikal.qurancall.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fikrihaikal.qurancall.di.Injection
import com.fikrihaikal.qurancall.ui.teacher.hometeacher.HomeTeacherViewModel
import com.fikrihaikal.qurancall.ui.login.LoginViewModel
import com.fikrihaikal.qurancall.ui.register.RegisterViewModel
import com.fikrihaikal.qurancall.ui.teacher.alquranteacher.viewpager.surah.SurahTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.detailsurahteacher.DetailSurahTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.materibelajar.MateriBelajarTeacherViewModel
import com.fikrihaikal.qurancall.ui.user.alquran.viewpager.surah.SurahViewModel
import com.fikrihaikal.qurancall.ui.user.detaildoa.DetailDoaViewModel
import com.fikrihaikal.qurancall.ui.user.detailguru.DetailGuruViewModel
import com.fikrihaikal.qurancall.ui.user.detailsurah.DetailSurahViewModel
import com.fikrihaikal.qurancall.ui.user.doa.DoaViewModel
import com.fikrihaikal.qurancall.ui.user.gantipassword.GantiPasswordViewModel
import com.fikrihaikal.qurancall.ui.user.home.HomeViewModel
import com.fikrihaikal.qurancall.ui.user.materibelajar.MateriBelajarViewModel
import com.fikrihaikal.qurancall.ui.user.pilihguru.PilihGuruViewModel
import com.fikrihaikal.qurancall.ui.user.profile.ProfileViewModel


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
            modelClass.isAssignableFrom(DetailSurahViewModel::class.java) ->{
                DetailSurahViewModel(Injection.provideSurahRepository(context)) as T
            }
            modelClass.isAssignableFrom(MateriBelajarViewModel::class.java) ->{
                MateriBelajarViewModel(Injection.provideMateriRepository(context)) as T
            }
            modelClass.isAssignableFrom(HomeTeacherViewModel::class.java) ->{
                HomeTeacherViewModel(Injection.provideRepository(context), Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(MateriBelajarTeacherViewModel::class.java) ->{
                MateriBelajarTeacherViewModel(Injection.provideMateriRepository(context)) as T
            }
            modelClass.isAssignableFrom(SurahTeacherViewModel::class.java) ->{
                SurahTeacherViewModel(Injection.provideSurahRepository(context)) as T
            }
            modelClass.isAssignableFrom(DetailSurahTeacherViewModel::class.java) ->{
                DetailSurahTeacherViewModel(Injection.provideSurahRepository(context)) as T
            }


            else ->throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

        }

    }

}