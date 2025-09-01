package com.example.assignmenttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.assignmenttest.Repository.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") { StayWelcomeScreen(navController) }
        composable("user_login") {LoginPage(
            title = "User Login",
            navController = navController,
            onLoginClick = { email, password ->
                val success = AuthRepository.loginUser(email, password)
                if (success) {
                    navController.navigate("main")
                } else {
                }
            },
            onSignUpClick = {
                navController.navigate("user_signup")
            }
        )}
        composable("main") { MainPage(navController) }
        composable("filter") { FilterScreen(navController) }
        composable("admin_main") { AdminMainPage(navController) }
        composable("admin_login") { LoginPage(
            title = "Admin Login",
            navController = navController,
            onLoginClick = { email, password ->
                val success = AuthRepository.loginAdmin(email, password)
                if (success) {
                    navController.navigate("admin_main")
                } else {
                    // TODO:
                }
            },
            onSignUpClick = {
                navController.navigate("admin_signup")
            }
        )}
        composable("admin_signup") { AdminSignUpPage(navController) }
        composable("homestay_detail/{homestayId}") { backStackEntry ->
            val homestayId = backStackEntry.arguments?.getString("homestayId")?.toIntOrNull() ?: 1
            HomestayDetailPage(
                navController = navController,
                homestayId = homestayId
            )
        }
    }
}


@Composable
fun StayWelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8DC)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(title = "Stay", showBack = false)

        Spacer(modifier = Modifier.height(40.dp))

        TypewriterText(
            text = "Welcome to Stay.. Please select whether you are chalet or user...",
            modifier = Modifier.padding(horizontal = 20.dp),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            delayPerChar = 50 // Adjust typing speed
        )

        Spacer(modifier = Modifier.height(60.dp))

        // User button
        SelectionButton(
            text = "User",
            icon = Icons.Default.Person,
            onClick = { navController.navigate("user_login") },
            modifier = Modifier.size(140.dp, 120.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        SelectionButton(
            text = "Chalet",
            icon = Icons.Default.Home,
            onClick = {
                navController.navigate("admin_login")
            },
            modifier = Modifier.size(140.dp, 120.dp)
        )
    }
}



@Composable
fun TypewriterText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    delayPerChar: Int = 100, // Delay time per character (milliseconds)
    onComplete: () -> Unit = {} // Completion callback
) {
    var currentText by remember { mutableStateOf("") }
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(text) {
        currentText = ""
        currentIndex = 0
        while (currentIndex < text.length) {
            currentText += text[currentIndex]
            currentIndex++
            delay(delayPerChar.toLong())
        }
        onComplete()
    }

    BasicText(
        text = currentText,
        modifier = modifier,
        style = style
    )
}

@Composable
fun SelectionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFFFFB347),
    contentColor: Color = Color.Black,
    iconSize: androidx.compose.ui.unit.Dp = 48.dp,
    textSize: androidx.compose.ui.unit.TextUnit = 16.sp,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        shape = shape
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(iconSize),
                tint = contentColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontSize = textSize,
                fontWeight = FontWeight.Medium,
                color = contentColor
            )
        }
    }
}
@Composable
fun TopBar(
    title: String,
    navController: NavController? = null,
    showBack: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFB347))
            .padding(20.dp)
    ) {

        if (showBack && navController != null) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }


        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStayWelcomeScreen() {
    Navigation()
}


