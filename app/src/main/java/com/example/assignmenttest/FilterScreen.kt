package com.example.assignmenttest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Data classes for filter options
data class FilterOptions(
    val environment: Set<String> = setOf(),
    val nearbyActivities: Set<String> = setOf(),
    val propertyFeatures: Set<String> = setOf(),
    val stayType: Set<String> = setOf(),
    val bookingFlexibility: Set<String> = setOf(),
    val sortBy: String = ""
)

@Composable
fun FilterScreen(navController: NavController) {
    var filterOptions by remember { mutableStateOf(FilterOptions()) }

    fun toggleOption(currentSet: Set<String>, option: String): Set<String> {
        return if (currentSet.contains(option)) {
            currentSet - option
        } else {
            currentSet + option
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8DC))
    ) {
        TopBar(
            title = "Filter",
            navController = navController,
            showBack = true
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Environment & Surroundings Section
            FilterSection(
                title = "Environment & Surroundings",
                options = listOf(
                    "Beachfront", "Forest / Nature Retreat",
                    "Mountain View", "Lakefront",
                    "Countryside", "Island Getaway"
                ),
                selectedOptions = filterOptions.environment,
                onOptionToggle = { option ->
                    filterOptions = filterOptions.copy(
                        environment = toggleOption(filterOptions.environment, option)
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Nearby Activities Section
            FilterSection(
                title = "Nearby Activities",
                options = listOf(
                    "Hiking Trails", "Shopping & Markets",
                    "Water Sports", "Historical Landmarks",
                    "Nightlife", "Local Food Spots"
                ),
                selectedOptions = filterOptions.nearbyActivities,
                onOptionToggle = { option ->
                    filterOptions = filterOptions.copy(
                        nearbyActivities = toggleOption(filterOptions.nearbyActivities, option)
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Property Features Section
            FilterSection(
                title = "Property Features",
                options = listOf(
                    "Private Pool", "Free Parking",
                    "BBQ Facilities", "Kitchen Access",
                    "Pet Friendly", "Jacuzzi / Hot Tub"
                ),
                selectedOptions = filterOptions.propertyFeatures,
                onOptionToggle = { option ->
                    filterOptions = filterOptions.copy(
                        propertyFeatures = toggleOption(filterOptions.propertyFeatures, option)
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Stay Type & Experience Section
            FilterSection(
                title = "Stay Type & Experience",
                options = listOf(
                    "Family Friendly", "Romantic Staycation",
                    "Solo Retreat", "Group Getaway",
                    "Luxury Escape", "Budget Friendly"
                ),
                selectedOptions = filterOptions.stayType,
                onOptionToggle = { option ->
                    filterOptions = filterOptions.copy(
                        stayType = toggleOption(filterOptions.stayType, option)
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Booking Flexibility Section
            FilterSection(
                title = "Booking Flexibility",
                options = listOf(
                    "Partial Refund", "Last-Minute Changes",
                    "No-Refund Policy", "Free Date Change",
                    "Free Cancellation"
                ),
                selectedOptions = filterOptions.bookingFlexibility,
                onOptionToggle = { option ->
                    filterOptions = filterOptions.copy(
                        bookingFlexibility = toggleOption(filterOptions.bookingFlexibility, option)
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Sort By Section
            SortBySection(
                selectedSort = filterOptions.sortBy,
                onSortSelected = { sortOption ->
                    filterOptions = filterOptions.copy(sortBy = sortOption)
                }
            )
        }

        // Show Result Button
        Button(
            onClick = {
                // TODO: Apply filters and navigate back
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFB347)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "SHOW RESULT",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun FilterSection(
    title: String,
    options: List<String>,
    selectedOptions: Set<String>,
    onOptionToggle: (String) -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Create rows with 2 items each
        for (i in options.indices step 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // First checkbox in row
                CheckboxOption(
                    text = options[i],
                    isChecked = selectedOptions.contains(options[i]),
                    onCheckedChange = { _ -> onOptionToggle(options[i]) },
                    modifier = Modifier.weight(1f)
                )

                // Second checkbox in row (if exists)
                if (i + 1 < options.size) {
                    CheckboxOption(
                        text = options[i + 1],
                        isChecked = selectedOptions.contains(options[i + 1]),
                        onCheckedChange = { _ -> onOptionToggle(options[i + 1]) },
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CheckboxOption(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFFFB347),
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

@Composable
fun SortBySection(
    selectedSort: String,
    onSortSelected: (String) -> Unit
) {
    val sortOptions = listOf(
        "Most Popular",
        "Nearest First",
        "Highest Rated",
        "Price (Lowest to Highest)"
    )

    Column {
        Text(
            text = "Sort By",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Create rows with 2 items each
        for (i in sortOptions.indices step 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // First radio button in row
                RadioOption(
                    text = sortOptions[i],
                    isSelected = selectedSort == sortOptions[i],
                    onSelected = { onSortSelected(sortOptions[i]) },
                    modifier = Modifier.weight(1f)
                )

                // Second radio button in row (if exists)
                if (i + 1 < sortOptions.size) {
                    RadioOption(
                        text = sortOptions[i + 1],
                        isSelected = selectedSort == sortOptions[i + 1],
                        onSelected = { onSortSelected(sortOptions[i + 1]) },
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RadioOption(
    text: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFFFFB347),
                unselectedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}