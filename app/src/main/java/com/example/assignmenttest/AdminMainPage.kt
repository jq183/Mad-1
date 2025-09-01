package com.example.assignmenttest


import androidx.compose.material3.NavigationBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.assignmenttest.Data.*

@Composable
fun AdminTopBar(
    title: String = "Stay",
    showSearch: Boolean = true,
    onSearchValueChange: (String) -> Unit = {},
    searchValue: String = "",
    showFilter: Boolean = false,
    onFilterClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF8E7))
            .padding(10.dp)
    ) {

        Text(
            text = "Stay",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp),
            color = Color(0xFF000000)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFE2A9), shape = RoundedCornerShape(50))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search property name") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )

            Icon(Icons.Default.FilterList, contentDescription = "Filter", tint = Color.Black)


                if (showFilter) {
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = "Filter",
                        tint = Color.Black,
                        modifier = Modifier.clickable { onFilterClick() }
                    )
                }
            }
        }
    }

@Composable
fun AdminBottomBar(
    selectedItem: Int,
    onItemSelected: (Int, String) -> Unit
) {
    val items = listOf("chaletList", "addChalet", "bookings", "account")
    val labels = listOf("My Chalet", "Add Chalet", "Bookings", "Account")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Add,
        Icons.Default.List,
        Icons.Default.Person
    )

    NavigationBar(containerColor = Color(0xFF000000)) {
        items.forEachIndexed { index, route ->
            NavigationBarItem(
                icon = {
                    Icon(
                        icons[index],
                        contentDescription = labels[index],
                        tint = Color(0xFFFFE0A0)
                    )
                },
                label = { Text(labels[index], color = Color(0xFFFFE0A0)) },
                selected = selectedItem == index,
                onClick = {
                    onItemSelected(index, route)
                }
            )
        }
    }
}
@Composable
fun AdminMainPage(navController: NavController) {
    val bottomNavController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier
            .background(Color(0xFFFFE0A0))
            .statusBarsPadding(),
        topBar = {
            // 添加统一的 TopBar
            AdminTopBar(
                title = "Admin Dashboard",
                showSearch = true,
                onSearchValueChange = { /* TODO:  */ },
                searchValue = "",
                showFilter = false,
                onFilterClick = { }
            )
        },
        bottomBar = {
            AdminBottomBar(
                selectedItem = selectedItem,
                onItemSelected = { index, route ->
                    selectedItem = index
                    bottomNavController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(bottomNavController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        AdminNavGraph(bottomNavController, innerPadding, navController)
    }
}

@Composable
fun AdminNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    mainNavController: NavController
) {
    NavHost(
        navController = navController,
        startDestination = "chaletList",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("chaletList") {
            ChaletListPage(navController, showTopBar = false)
        }
        composable("addChalet") {
            AddChaletPage(navController, showTopBar = false)
        }
        composable("bookings") { BookingPage(navController) }
        composable("account") { AccountPage(navController) }
        composable("add_filter") { FilterOptionsPage(navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChaletListPage(
    navController: NavController,
    showTopBar: Boolean = true
) {
    var chalets by remember { mutableStateOf(ChaletDataManager.getAllChalets()) }

    LaunchedEffect(Unit) {
        chalets = ChaletDataManager.getAllChalets()
    }

    if (showTopBar) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My Chalets") },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { navController.popBackStack() }
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFFFE0A0),
                        titleContentColor = Color.Black,
                        navigationIconContentColor = Color.Black
                    )
                )
            },
            containerColor = Color(0xFFFFF0D0)
        ) { paddingValues ->
            ChaletListContent(chalets, paddingValues)
        }
    } else {
        ChaletListContent(chalets, PaddingValues(0.dp))
    }
}

@Composable
private fun ChaletListContent(chalets: List<ChaletData>, paddingValues: PaddingValues) {
    if (chalets.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFFF0D0)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_image_placeholder),
                    contentDescription = "No chalets",
                    tint = Color.Gray,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No chalets uploaded yet",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Add your first chalet to get started",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFFF0D0))
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(chalets) { chalet ->
                ChaletCard(
                    title = chalet.name,
                    status = when (chalet.status) {
                        ChaletStatus.PENDING -> "Pending"
                        ChaletStatus.APPROVED -> "Uploaded"
                        ChaletStatus.REJECTED -> "Rejected"
                    },
                    onViewClick = {
                    }
                )
            }
        }
    }
}


@Composable
fun ChaletCard(
    title: String,
    status: String,
    onViewClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFFFF0D0))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chalet image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFEFEFEF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_image_placeholder),
                    contentDescription = "Chalet Image",
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Chalet info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Status: $status",
                    fontSize = 14.sp,
                    color = when (status) {
                        "Uploaded" -> Color(0xFF4CAF50)
                        "Pending" -> Color(0xFFFF9800)
                        "Rejected" -> Color(0xFFF44336)
                        else -> Color.Gray
                    },
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                onClick = onViewClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFE0A0)
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("View", color = Color.Black, fontWeight = FontWeight.Medium)
            }
        }
    }
}



@Composable
fun AccountPage(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Account Page")
    }
}
