package com.example.chaletbooking.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.example.assignmenttest.R

@Composable
fun AdminMainPage() {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(Color(0xFFFFE0A0))
                    .fillMaxWidth()
                    .padding(8.dp)

            ) {
                Text(
                    "Stay", style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Search property name") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(50)
                    )
                }

            }
        }

    )
    { innerpadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerpadding)
                .fillMaxSize()
        ) {
            items(5) {
                ChaletCard("Sunrise Nest", if (it % 2 == 0) "Uploaded" else "Pending")
            }

        }
    }
}

@Composable
fun ChaletCard(title: String, status: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFFFF0D0))
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(status)
            }
            Button(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(50)
            ) {
                Text("View")
            }
        }
    }
}