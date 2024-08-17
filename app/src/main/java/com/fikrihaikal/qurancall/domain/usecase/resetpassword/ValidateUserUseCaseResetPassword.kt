package com.fikrihaikal.qurancall.domain.usecase.resetpassword

import com.fikrihaikal.qurancall.domain.model.user.resetpassword.UserResetPassword

class ValidateUserUseCaseResetPassword {
    fun validate(user:UserResetPassword): ValidationResultResetPassword{
        val errors = mutableListOf<String>()

        if (user.oldPassword.isBlank()){
            errors.add("Password lama harap diisi")
        }else if (user.oldPassword.length < 8){
            errors.add("Password minimal 8 karakter")
        }

        if (user.newPassword.isBlank()){
            errors.add("Password baru harap diisi")
        }else if (user.newPassword.length < 8){
            errors.add("Password minimal 8 karakter")
        }

        if (user.confirmNewPassword.isBlank()) {
            errors.add("Konfirmasi password harap diisi")
        } else if (user.confirmNewPassword != user.newPassword) {
            errors.add("Konfirmasi password tidak sesuai")
        }

        return ValidationResultResetPassword(errors.isEmpty(),errors)
    }
}