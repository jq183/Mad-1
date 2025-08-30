package com.example.assignmenttest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Data class for homestay items
data class Homestay(
    val id: Int,
    val name: String,
    val imageRes: Int
)

@Composable
fun MainPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8DC))
    ) {
        TopBar(title = "Stay", showBack = false)

        // Search Bar
        SearchBar(navController)

        // Main content with scroll
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            // Recently viewed section
            HomestaySection("Recently viewed", navController)

            Spacer(modifier = Modifier.height(20.dp))

            // Current offers section
            HomestaySection("Current offers", navController)

            Spacer(modifier = Modifier.height(20.dp))

            // Recommend section
            HomestaySection("Recommend", navController)
        }

        // Bottom Navigation Bar
        BottomBar(navController = navController)
    }
}

@Composable
fun SearchBar(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                Color(0xFFFFE4B5),
                RoundedCornerShape(25.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        // Real input field
        BasicTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            decorationBox = { innerTextField ->
                if (searchText.isEmpty()) {
                    Text(
                        text = "Search homestay name",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        )

        // Location button
        IconButton(
            onClick = {
                println("Location button clicked")
            }
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }

        // Filter button
        IconButton(
            onClick = {
                navController.navigate("filter")
            }
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun HomestaySection(
    sectionType: String,
    navController: NavController
) {
    fun getHomestayData(type: String): List<Homestay> {
        return when (type) {
            "Recently viewed" -> listOf(
                Homestay(1, "Sunrise Nest", 0),
                Homestay(2, "Palm Breeze Retreat", 0),
                Homestay(3, "Ocean View Villa", 0),
            ).take(15)
            "Current offers" -> listOf(
                Homestay(4, "View Haven", 0),
                Homestay(5, "Seaside Serenity Homestay", 0),
                Homestay(6, "Coral San", 0),
            )
            "Recommend" -> listOf(
                Homestay(7, "Serenity Shore", 0),
                Homestay(8, "Luna Luxe Kuala Lumpur", 0),
                Homestay(9, "Mountain View", 0),
            )
            else -> emptyList()
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = sectionType,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = ">",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Horizontal scrollable list
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        val homestays = getHomestayData(sectionType)

        items(homestays) { homestay ->
            HomestayCard(homestay = homestay, navController = navController)
        }
    }
}

@Composable
fun HomestayCard(homestay: Homestay, navController: NavController) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable {
                navController.navigate("homestay_detail/${homestay.id}")
            }
    ) {
        Box(
            modifier = Modifier
                .size(120.dp, 80.dp)
                .background(Color.Gray, RoundedCornerShape(8.dp))
        ) {
            // Image(
            //     painter = painterResource(id = homestay.imageRes),
            //     contentDescription = homestay.name,
            //     modifier = Modifier.fillMaxSize(),
            //     contentScale = ContentScale.Crop
            // )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = homestay.name,
            fontSize = 12.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BottomBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomBarItem(
            icon = Icons.Default.Home,
            text = "MAIN",
            onClick = { navController.navigate("main") }
        )
        BottomBarItem(
            icon = Icons.Default.Favorite,
            text = "SAVED",
            onClick = { /* Navigate to saved page */ }
        )
        BottomBarItem(
            icon = Icons.Default.CalendarToday,
            text = "BOOKINGS",
            onClick = { /* Navigate to bookings page */ }
        )
        BottomBarItem(
            icon = Icons.Default.AccountCircle,
            text = "ACCOUNT",
            onClick = { /* Navigate to account page */ }
        )
    }
}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    SelectionButton(
        icon = icon,
        text = text,
        onClick = onClick,
        modifier = Modifier.wrapContentSize(),
        containerColor = Color.Transparent,
        contentColor = Color(0xFFFFF8DC),
        iconSize = 24.dp,
        textSize = 10.sp,
        shape = RoundedCornerShape(0.dp)
    )
}