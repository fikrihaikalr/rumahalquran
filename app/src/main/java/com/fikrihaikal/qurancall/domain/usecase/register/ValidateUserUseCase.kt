package com.fikrihaikal.qurancall.domain.usecase.register

import com.fikrihaikal.qurancall.domain.model.user.register.User

class ValidateUserUseCase {
    fun validate(user: User): ValidationResult {
        val errors = mutableListOf<String>()

        if (user.username.isBlank()) {
            errors.add("Username harap diisi")
        }

        if (user.email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            errors.add("Email harap diisi")
        }

        if (user.password.isBlank()) {
            errors.add("Password harap diisi")
        } else if (user.password.length < 8) {
            errors.add("Password minimal 8 karakter")
        }

        if (user.confirmPassword.isBlank()) {
            errors.add("Konfirmasi password harap diisi")
        } else if (user.confirmPassword != user.password) {
            errors.add("Konfirmasi password tidak sesuai")
        }

        return ValidationResult(errors.isEmpty(), errors)
    }
}