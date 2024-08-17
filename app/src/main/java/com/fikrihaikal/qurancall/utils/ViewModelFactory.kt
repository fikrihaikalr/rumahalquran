package com.fikrihaikal.qurancall.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fikrihaikal.qurancall.di.Injection
import com.fikrihaikal.qurancall.ui.auth.forgotpassword.ForgotPasswordViewModel
import com.fikrihaikal.qurancall.ui.teacher.hometeacher.HomeTeacherViewModel
import com.fikrihaikal.qurancall.ui.auth.login.LoginViewModel
import com.fikrihaikal.qurancall.ui.auth.onboarding.OnBoardingViewModel
import com.fikrihaikal.qurancall.ui.auth.register.RegisterViewModel
import com.fikrihaikal.qurancall.ui.auth.splashscreen.SplashScreenFragment
import com.fikrihaikal.qurancall.ui.auth.splashscreen.SplashScreenViewModel
import com.fikrihaikal.qurancall.ui.teacher.alquranteacher.viewpager.surah.SurahTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.detaildoateacher.DetailDoaTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.detailmateriteacher.DetailContentTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.detailsurahteacher.DetailSurahTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.doateacher.DoaTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.gantifototeacher.GantiFotoTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.gantipasswordteacher.GantiPasswordTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.materibelajar.MateriBelajarTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.profileteacher.ProfileTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.submateriteacher.SubMenuMateriTeacherViewModel
import com.fikrihaikal.qurancall.ui.teacher.tambahkategory.TambahKategoriViewModel
import com.fikrihaikal.qurancall.ui.teacher.uploadmateri.UploadMateriViewModel
import com.fikrihaikal.qurancall.ui.user.alquran.viewpager.surah.SurahViewModel
import com.fikrihaikal.qurancall.ui.user.detaildoa.DetailDoaViewModel
import com.fikrihaikal.qurancall.ui.user.detailguru.DetailGuruViewModel
import com.fikrihaikal.qurancall.ui.user.detailmateri.DetailContentViewModel
import com.fikrihaikal.qurancall.ui.user.detailsurah.DetailSurahViewModel
import com.fikrihaikal.qurancall.ui.user.doa.DoaViewModel
import com.fikrihaikal.qurancall.ui.user.fotoprofile.uploadfoto.UploadFotoViewModel
import com.fikrihaikal.qurancall.ui.user.gantipassword.GantiPasswordViewModel
import com.fikrihaikal.qurancall.ui.user.home.HomeViewModel
import com.fikrihaikal.qurancall.ui.user.listsubmateri.SubMenuMateriViewModel
import com.fikrihaikal.qurancall.ui.user.materibelajar.MateriBelajarViewModel
import com.fikrihaikal.qurancall.ui.user.pilihguru.PilihGuruViewModel
import com.fikrihaikal.qurancall.ui.user.profile.ProfileViewModel
import javax.inject.Inject


class ViewModelFactory(private val context: Context)  : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create (modelClass: Class<T>) : T{
        return when {
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) ->{
                SplashScreenViewModel(Injection.provideDataStore(context)) as T
            }
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
                DoaViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(DetailDoaViewModel::class.java) ->{
                DetailDoaViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(GantiPasswordViewModel::class.java) ->{
                GantiPasswordViewModel(Injection.provideRepository(context), Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(PilihGuruViewModel::class.java) ->{
                PilihGuruViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(DetailGuruViewModel::class.java) ->{
                DetailGuruViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(SurahViewModel::class.java) ->{
                SurahViewModel(Injection.provideSurahRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(DetailSurahViewModel::class.java) ->{
                DetailSurahViewModel(Injection.provideSurahRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(MateriBelajarViewModel::class.java) ->{
                MateriBelajarViewModel(Injection.provideMateriRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(HomeTeacherViewModel::class.java) ->{
                HomeTeacherViewModel(Injection.provideRepository(context), Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(MateriBelajarTeacherViewModel::class.java) ->{
                MateriBelajarTeacherViewModel(Injection.provideMateriRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(SubMenuMateriViewModel::class.java) ->{
                SubMenuMateriViewModel(Injection.provideMateriRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(DetailContentViewModel::class.java) ->{
                DetailContentViewModel(Injection.provideMateriRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(SurahTeacherViewModel::class.java) ->{
                SurahTeacherViewModel(Injection.provideSurahRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(DetailSurahTeacherViewModel::class.java) ->{
                DetailSurahTeacherViewModel(Injection.provideSurahRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(ProfileTeacherViewModel::class.java) -> {
                ProfileTeacherViewModel(Injection.provideRepository(context), Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(DoaTeacherViewModel::class.java) ->{
                DoaTeacherViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(DetailDoaTeacherViewModel::class.java) ->{
                DetailDoaTeacherViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(GantiPasswordTeacherViewModel::class.java) ->{
                GantiPasswordTeacherViewModel(Injection.provideRepository(context), Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(TambahKategoriViewModel::class.java) ->{
                TambahKategoriViewModel(Injection.provideMateriRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(OnBoardingViewModel::class.java) ->{
                OnBoardingViewModel(Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(SubMenuMateriTeacherViewModel::class.java) ->{
                SubMenuMateriTeacherViewModel(Injection.provideMateriRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(DetailContentTeacherViewModel::class.java) ->{
                DetailContentTeacherViewModel(Injection.provideMateriRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(UploadMateriViewModel::class.java) ->{
                UploadMateriViewModel(Injection.provideMateriRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(UploadFotoViewModel::class.java) ->{
                UploadFotoViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(GantiFotoTeacherViewModel::class.java) ->{
                GantiFotoTeacherViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) ->{
                ForgotPasswordViewModel(Injection.provideRepository(context)) as T
            }
            else ->throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

        }

    }

}