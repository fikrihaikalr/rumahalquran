package com.fikrihaikal.qurancall.domain.model.user.register

data class User(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)