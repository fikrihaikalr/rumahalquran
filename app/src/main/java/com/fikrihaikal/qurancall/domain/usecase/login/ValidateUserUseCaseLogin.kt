package com.fikrihaikal.qurancall.domain.usecase.login

import com.fikrihaikal.qurancall.domain.model.user.login.UserLogin
import com.fikrihaikal.qurancall.domain.usecase.register.ValidationResult

class ValidateUserUseCaseLogin {
    fun validate(user: UserLogin): ValidationResultLogin{
        val errors = mutableListOf<String>()

        if (user.email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            errors.add("Email harap diisi")
        }

        if (user.password.isBlank()) {
            errors.add("Password harap diisi")
        } else if (user.password.length < 8) {
            errors.add("Password minimal 8 karakter")
        }

        return ValidationResultLogin(errors.isEmpty(), errors)
    }
}