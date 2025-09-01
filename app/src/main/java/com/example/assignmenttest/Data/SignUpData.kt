package com.example.assignmenttest.Data

data class UserSignUpData(
    val name: String,
    val email: String,
    val phone: String,
    val password: String
)

data class AdminSignUpData(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val languages: List<String>
)