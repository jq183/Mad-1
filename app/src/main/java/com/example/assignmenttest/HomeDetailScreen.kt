package com.example.assignmenttest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomestayDetailPage(
    navController: NavController,
    homestayId: Int
) {
    val homestayDetails = getHomestayDetails(homestayId)
    var selectedTab by remember { mutableStateOf("Info") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8DC))
    ) {
        // Top Bar with back button
        TopBar(title = "   ", navController = navController, showBack = true)


        // Main content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Homestay image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.BottomEnd
            ) {
                // Image counter
                Text(
                    text = "1/22",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .background(
                            Color.Black.copy(alpha = 0.6f),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .offset(x = (-12).dp, y = (-12).dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Homestay name
            Text(
                text = homestayDetails.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rating
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(4) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "4.5 / 5.0",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Review count
            Text(
                text = "(153 reviews)",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Check-in/out section
            Text(
                text = "Check-in/out",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Tue, 22 Jul - Wed, 23 Jul",
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Search details
            Text(
                text = "You searched for",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "1 room • 1 adult • No children",
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Price
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "From RM180 / night",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Free cancellation",
                    fontSize = 12.sp,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier
                        .background(
                            Color(0xFFE8F5E8),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tabs
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TabItem(
                    text = "Info",
                    isSelected = selectedTab == "Info",
                    onClick = { selectedTab = "Info" }
                )
                Spacer(modifier = Modifier.width(32.dp))
                TabItem(
                    text = "Review",
                    isSelected = selectedTab == "Review",
                    onClick = { selectedTab = "Review" }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tab content
            when (selectedTab) {
                "Info" -> InfoContent(homestayDetails)
                "Review" -> ReviewContent()
            }
        }

        // Select button
        Button(
            onClick = { /* Handle selection */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFE4B5)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "SELECT",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = Color.Black
        )
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .width(40.dp)
                    .background(Color.Black)
            )
        }
    }
}

@Composable
fun InfoContent(homestayDetails: HomestayDetails) {
    Column {
        // Description
        Text(
            text = "Description",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = homestayDetails.description,
            fontSize = 14.sp,
            color = Color.Black,
            lineHeight = 20.sp
        )

        Text(
            text = "Read more",
            fontSize = 14.sp,
            color = Color.Blue,
            modifier = Modifier
                .padding(top = 4.dp)
                .clickable { /* Handle read more */ }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Amenities
        Text(
            text = "Amenities & Features",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Amenities grid
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AmenityItem(
                    icon = Icons.Default.Wifi,
                    text = "Free WiFi",
                    modifier = Modifier.weight(1f)
                )
                AmenityItem(
                    icon = Icons.Default.LocalParking,
                    text = "Free Parking",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AmenityItem(
                    icon = Icons.Default.SmokeFree,
                    text = "Non-smoking rooms",
                    modifier = Modifier.weight(1f)
                )
                AmenityItem(
                    icon = Icons.Default.FitnessCenter,
                    text = "On-site Gym",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AmenityItem(
                    icon = Icons.Default.Waves,
                    text = "Private Beach",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Text(
            text = "Read more",
            fontSize = 14.sp,
            color = Color.Blue,
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable { /* Handle read more */ }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Exploring the area
        Text(
            text = "Exploring the area",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Map placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "5 min to Cenang Beach\nNear shopping & food",
            fontSize = 14.sp,
            color = Color.Black
        )

        Text(
            text = "Read more",
            fontSize = 14.sp,
            color = Color.Blue,
            modifier = Modifier
                .padding(top = 4.dp)
                .clickable { /* Handle read more */ }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Host information
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Hosted by Sonia Yong",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Superhost • 3 years hosting",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            Text(
                text = "Host details:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Response rate: 100%\nResponds within an hour",
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            Text(
                text = "Speaks:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "• English\n• Malay\n• Chinese",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun AmenityItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun ReviewContent() {
    Column {
        Text(
            text = "Reviews will be displayed here",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Data class for homestay details
data class HomestayDetails(
    val id: Int,
    val name: String,
    val description: String,
    val rating: Float,
    val reviewCount: Int,
    val pricePerNight: String,
    val amenities: List<String>
)

// Function to get homestay details by ID
fun getHomestayDetails(id: Int): HomestayDetails {
    return when (id) {
        1 -> HomestayDetails(
            id = 1,
            name = "Sunrise Resort Langkawi",
            description = "Nestled along the white sandy shores of Langkawi, Sunrise Resort offers a peaceful retreat with stunning ocean views, private beach access, and spacious rooms. Guests can enjoy an infinity pool, complimentary breakfast, and easy access to local dining and shopping. Ideal for couples and families looking for a relaxing seaside getaway.",
            rating = 4.5f,
            reviewCount = 153,
            pricePerNight = "RM180",
            amenities = listOf("Free WiFi", "Free Parking", "Non-smoking rooms", "On-site Gym", "Private Beach")
        )
        2 -> HomestayDetails(
            id = 2,
            name = "Palm Breeze Retreat",
            description = "A tropical paradise featuring lush gardens and modern amenities. Perfect for those seeking tranquility and comfort.",
            rating = 4.3f,
            reviewCount = 89,
            pricePerNight = "RM150",
            amenities = listOf("Free WiFi", "Swimming Pool", "Garden View", "Air Conditioning")
        )
        // Add more cases for other homestays
        else -> HomestayDetails(
            id = id,
            name = "Default Homestay",
            description = "A comfortable stay with basic amenities.",
            rating = 4.0f,
            reviewCount = 50,
            pricePerNight = "RM120",
            amenities = listOf("Free WiFi", "Air Conditioning")
        )
    }
}