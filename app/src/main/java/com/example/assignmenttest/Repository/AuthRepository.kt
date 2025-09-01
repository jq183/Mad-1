package com.example.assignmenttest.Repository

import com.example.assignmenttest.Data.*


object AuthRepository {
    private val userList = mutableListOf<UserSignUpData>()
    private val adminList = mutableListOf<AdminSignUpData>()

    init {
        userList.add(
            UserSignUpData(
                name = "Test User",
                email = "abc@gmail.com",
                phone = "0123456789",
                password = "123456"
            )
        )

        adminList.add(
            AdminSignUpData(
                name = "Admin A",
                email = "admin@gmail.com",
                phone = "0987654321",
                password = "admin123",
                languages = listOf("English", "Malay")
            )
        )
    }

    fun registerUser(user: UserSignUpData) { userList.add(user) }
    fun registerAdmin(admin: AdminSignUpData) { adminList.add(admin) }

    fun loginUser(email: String, password: String): Boolean {
        return userList.any { it.email == email && it.password == password }
    }

    fun loginAdmin(email: String, password: String): Boolean {
        return adminList.any { it.email == email && it.password == password }
    }
}