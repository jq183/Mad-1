package com.example.assignmenttest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.collections.set
import com.example.assignmenttest.Data.*
import com.example.assignmenttest.Repository.*
@Composable
fun AdminSignUpPage(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val languages = listOf("English", "Malay", "Chinese", "Tamil", "Others")
    val selectedLanguages = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8DC)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(title = "Admin Sign Up", navController = navController, showBack = true)

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Chalet Admin Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email *") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number *") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Text("Languages Spoken:", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            // 每两列显示一行
            languages.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    row.forEachIndexed { index, lang ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Checkbox(
                                checked = selectedLanguages[lang] ?: false,
                                onCheckedChange = { selectedLanguages[lang] = it }
                            )
                            Text(lang, fontSize = 16.sp)
                        }
                        if (index == 0 && row.size == 2) {
                            Spacer(modifier = Modifier.width(32.dp))
                        }
                    }
                }
            }

            error?.let {
                Text(text = it, color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val chosenLanguages = selectedLanguages
                        .filter { it.value }
                        .map { it.key }

                    if (name.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
                        error = "Please fill all required fields"
                    } else {
                        val newAdmin = AdminSignUpData(
                            name = name,
                            email = email,
                            phone = phone,
                            password = password,
                            languages = chosenLanguages
                        )
                        AuthRepository.registerAdmin(newAdmin)
                        navController.navigate("admin_main")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB347)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Sign Up",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun LoginPage(
    title: String,
    navController: NavController,
    onLoginClick: (String, String) -> Unit,
    onSignUpClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8DC)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(title = title, navController = navController, showBack = true)

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFB347),
                    focusedLabelColor = Color(0xFFFFB347)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFB347),
                    focusedLabelColor = Color(0xFFFFB347)
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    when {
                        AuthRepository.loginUser(email, password) -> {
                            navController.navigate("main")
                        }
                        AuthRepository.loginAdmin(email, password) -> {
                            navController.navigate("admin_main")
                        }
                        else -> {
                            errorMessage = "Invalid email or password"
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB347)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Login",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = it, color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (onSignUpClick != null) {
                Row {
                    Text("No account? ", fontSize = 14.sp, color = Color.Black)
                    Text(
                        text = "Sign up",
                        fontSize = 14.sp,
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { onSignUpClick() }
                    )
                }
            }
        }
    }
}

