package com.fikrihaikal.qurancall.domain.usecase.register

data class ValidationResult(val isValid: Boolean, val errors: List<String>)