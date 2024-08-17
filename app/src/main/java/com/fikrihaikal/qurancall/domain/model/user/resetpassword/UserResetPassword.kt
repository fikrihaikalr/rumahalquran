package com.fikrihaikal.qurancall.domain.model.user.resetpassword

data class UserResetPassword(
    val oldPassword: String,
    val newPassword: String,
    val confirmNewPassword: String
)