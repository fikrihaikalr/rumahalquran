package com.fikrihaikal.qurancall.domain.usecase.unggahmateri


import com.fikrihaikal.qurancall.domain.model.user.unggahmateri.UploadMateri


class ValidateUploadMateriUseCase {
    fun validate(uploadMateri: UploadMateri): ValidationResultMateri {
        val errors = mutableListOf<String>()

        if (uploadMateri.title.isBlank()) {
            errors.add("Title harap diisi")
        }

        if (uploadMateri.author.isBlank()) {
            errors.add("Author harap diisi")
        }

        if (uploadMateri.content.isBlank()) {
            errors.add("Content harap diisi")
        } else if (uploadMateri.content.length < 10) {
            errors.add("Content minimal 10 karakter")
        }

        return ValidationResultMateri(errors.isEmpty(), errors)
    }
}