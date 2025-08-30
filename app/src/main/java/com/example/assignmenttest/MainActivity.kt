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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import com.example.chaletbooking.ui.theme.AdminMainPage // Make sure this import is correct
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Simple state to decide which screen to show
            var showAdminPage by remember { mutableStateOf(false) }

            if (showAdminPage) {
                AdminMainPage() // Assuming AdminMainPage is a full screen Composable
            } else {
                StayWelcomeScreen(onAdminClick = { showAdminPage = true })
            }
        }
    }
}

@Composable
fun StayWelcomeScreen(onAdminClick: () -> Unit) { // Added a callback
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8DC))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(title = "Stay")

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
            onClick = {
                println("User button clicked")
                // Handle user navigation or action here
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Admin button
        SelectionButton(
            text = "Chalet",
            icon = Icons.Default.Home,
            onClick = onAdminClick // Call the passed lambda
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
    onClick: () -> Unit // Corrected: This is an action
) {
    Button(
        onClick = onClick, // No cast needed
        modifier = Modifier
            .size(140.dp, 120.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFB347) // Example color
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(48.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

@Composable
fun TopBar(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFFFB347), // Example color
                RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewStayWelcomeScreen() {
    // For preview, you might need a dummy callback
    StayWelcomeScreen(onAdminClick = {})
}
