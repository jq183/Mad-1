package com.example.assignmenttest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.app.DatePickerDialog
import androidx.compose.material.icons.Icons
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import androidx.compose.material.icons.filled.ArrowDropDown
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.snapshots.SnapshotStateList
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.example.assignmenttest.Data.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChaletPage(
    navController: NavController,
    showTopBar: Boolean = true
) {
    // ... 其他状态变量保持不变 ...
    var chaletName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var guestNo by remember { mutableStateOf("2") }
    var price by remember { mutableStateOf("") }
    var rooms by remember { mutableStateOf("") }
    var bathrooms by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val bedTypes = listOf("Single", "Double", "Queen", "King", "Bunk Bed", "Mixed")
    val bedList = remember { mutableStateListOf(BedItem("1", "Queen")) }

    var checkInHour by remember { mutableStateOf("2:00") }
    var checkInPeriod by remember { mutableStateOf("PM") }
    var checkOutHour by remember { mutableStateOf("12:00") }
    var checkOutPeriod by remember { mutableStateOf("PM") }

    var showSuccessDialog by remember { mutableStateOf(false) }
    val selectedFilters = remember { mutableStateMapOf<String, Boolean>() }
    val nearbyTimes = remember { mutableStateMapOf<String, String>() }
    val specialPrices = remember { mutableStateListOf<Pair<String, String>>() }

    // 图片选择相关状态
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // 图片选择器
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    fun saveChaletData() {
        val chaletData = ChaletData(
            name = chaletName,
            address = address,
            maxGuests = guestNo,
            rooms = rooms,
            bathrooms = bathrooms,
            pricePerNight = price,
            specialPrices = specialPrices.toList(),
            beds = bedList.toList(),
            description = description,
            checkInTime = "$checkInHour $checkInPeriod",
            checkOutTime = "$checkOutHour $checkOutPeriod",
            selectedFilters = selectedFilters.toMap(),
            nearbyTimes = nearbyTimes.toMap(),
            imageUri = selectedImageUri?.toString(), // 保存图片 URI
            status = ChaletStatus.PENDING
        )

        ChaletDataManager.addChalet(chaletData)
        showSuccessDialog = true
    }

    if (showTopBar) {
        Scaffold(
            modifier = Modifier
                .background(Color(0xFFFFE0A0))
                .statusBarsPadding(),
            containerColor = Color(0xFFFFF0D0),
            topBar = {
                TopAppBar(
                    title = { Text("Add Chalet") },
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
            }
        ) { scaffoldPadding ->
            AddChaletContent(
                scaffoldPadding = scaffoldPadding,
                chaletName = chaletName,
                onChaletNameChange = { chaletName = it },
                address = address,
                onAddressChange = { address = it },
                guestNo = guestNo,
                onGuestNoChange = { guestNo = it },
                price = price,
                onPriceChange = { price = it },
                rooms = rooms,
                onRoomsChange = { rooms = it },
                bathrooms = bathrooms,
                onBathroomsChange = { bathrooms = it },
                description = description,
                onDescriptionChange = { description = it },
                bedList = bedList,
                bedTypes = bedTypes,
                checkInHour = checkInHour,
                onCheckInHourChange = { checkInHour = it },
                checkInPeriod = checkInPeriod,
                onCheckInPeriodChange = { checkInPeriod = it },
                checkOutHour = checkOutHour,
                onCheckOutHourChange = { checkOutHour = it },
                checkOutPeriod = checkOutPeriod,
                onCheckOutPeriodChange = { checkOutPeriod = it },
                specialPrices = specialPrices,
                selectedImageUri = selectedImageUri,
                onImageSelect = { imagePickerLauncher.launch("image/*") },
                onSaveChalet = { saveChaletData() },
                navController = navController
            )
        }
    } else {
        // 在 AdminMainPage 中时不显示自己的 TopBar
        AddChaletContent(
            scaffoldPadding = PaddingValues(0.dp),
            chaletName = chaletName,
            onChaletNameChange = { chaletName = it },
            address = address,
            onAddressChange = { address = it },
            guestNo = guestNo,
            onGuestNoChange = { guestNo = it },
            price = price,
            onPriceChange = { price = it },
            rooms = rooms,
            onRoomsChange = { rooms = it },
            bathrooms = bathrooms,
            onBathroomsChange = { bathrooms = it },
            description = description,
            onDescriptionChange = { description = it },
            bedList = bedList,
            bedTypes = bedTypes,
            checkInHour = checkInHour,
            onCheckInHourChange = { checkInHour = it },
            checkInPeriod = checkInPeriod,
            onCheckInPeriodChange = { checkInPeriod = it },
            checkOutHour = checkOutHour,
            onCheckOutHourChange = { checkOutHour = it },
            checkOutPeriod = checkOutPeriod,
            onCheckOutPeriodChange = { checkOutPeriod = it },
            specialPrices = specialPrices,
            selectedImageUri = selectedImageUri,
            onImageSelect = { imagePickerLauncher.launch("image/*") },
            onSaveChalet = { saveChaletData() },
            navController = navController
        )
    }

    // Success Dialog
    if (showSuccessDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                navController.popBackStack()
            },
            title = { Text("Success!") },
            text = { Text("Your chalet has been submitted and is pending approval.") },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

// 6. 图片上传组件
@Composable
fun ImageUploadSection(
    selectedImageUri: Uri?,
    onImageSelect: () -> Unit
) {
    Text("Photo:", fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEFEFEF))
            .clickable { onImageSelect() },
        contentAlignment = Alignment.Center
    ) {
        if (selectedImageUri != null) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            // 显示上传占位符
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_image_placeholder),
                    contentDescription = "Upload",
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Tap to upload photo", color = Color.Gray)
            }
        }
    }
}

// 7. AddChaletContent 函数
@Composable
private fun AddChaletContent(
    scaffoldPadding: PaddingValues,
    chaletName: String,
    onChaletNameChange: (String) -> Unit,
    address: String,
    onAddressChange: (String) -> Unit,
    guestNo: String,
    onGuestNoChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    rooms: String,
    onRoomsChange: (String) -> Unit,
    bathrooms: String,
    onBathroomsChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    bedList: SnapshotStateList<BedItem>,
    bedTypes: List<String>,
    checkInHour: String,
    onCheckInHourChange: (String) -> Unit,
    checkInPeriod: String,
    onCheckInPeriodChange: (String) -> Unit,
    checkOutHour: String,
    onCheckOutHourChange: (String) -> Unit,
    checkOutPeriod: String,
    onCheckOutPeriodChange: (String) -> Unit,
    specialPrices: SnapshotStateList<Pair<String, String>>,
    selectedImageUri: Uri?,
    onImageSelect: () -> Unit,
    onSaveChalet: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(scaffoldPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFFFF0D0))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        AddChaletTextField(chaletName, onChaletNameChange, "Chalet Name", Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        AddChaletTextField(address, onAddressChange, "Address", Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        AddChaletTextField(guestNo, onGuestNoChange, "Max Guests", Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        AddChaletTextField(rooms, onRoomsChange, "Number of Rooms", Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        AddChaletTextField(bathrooms, onBathroomsChange, "Bathrooms", Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        PriceTextField(price, onValueChange = onPriceChange)
        Spacer(modifier = Modifier.height(16.dp))

        // Special Pricing Section
        Text("Special Pricing", fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))

        specialPrices.forEachIndexed { index, (date, specialPrice) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        val year = calendar.get(Calendar.YEAR)
                        val month = calendar.get(Calendar.MONTH)
                        val day = calendar.get(Calendar.DAY_OF_MONTH)

                        DatePickerDialog(
                            context,
                            { _, pickedYear, pickedMonth, pickedDay ->
                                val newDate = "$pickedDay/${pickedMonth + 1}/$pickedYear"
                                specialPrices[index] = newDate to specialPrice
                            },
                            year, month, day
                        ).show()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (date.isEmpty()) "Select Date" else date, color = Color.Black)
                }

                OutlinedTextField(
                    value = specialPrice,
                    onValueChange = { newPrice ->
                        specialPrices[index] = date to newPrice
                    },
                    label = { Text("Price (RM)") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedButton(
                    onClick = { specialPrices.removeAt(index) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("X", color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = { specialPrices.add("" to "") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE0A0))
        ) {
            Text("Add Date", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Beds section
        Text("Beds", fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(Modifier.height(8.dp))
        bedList.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = item.qty,
                    onValueChange = { newQty ->
                        bedList[index] = item.copy(qty = newQty)
                    },
                    label = { Text("Qty") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.DarkGray,
                        cursorColor = Color.Black
                    )
                )
                DropdownSelector(
                    selected = item.type,
                    options = bedTypes,
                    onSelect = { selectedType ->
                        bedList[index] = item.copy(type = selectedType)
                    },
                    modifier = Modifier.weight(2f)
                )
                OutlinedButton(
                    onClick = { if (bedList.size > 1) bedList.removeAt(index) },
                    enabled = bedList.size > 1
                ) {
                    Text("Remove")
                }
            }
        }
        OutlinedButton(onClick = { bedList.add(BedItem("1", "Single")) }) {
            Text("Add Bed Type")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.DarkGray,
                cursorColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Check-in / Check-out Time", fontWeight = FontWeight.Bold, color = Color.Black)
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Check-in", fontSize = 14.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DropdownTimeSelector(checkInHour, onCheckInHourChange)
                    DropdownAmPmSelector(checkInPeriod, onCheckInPeriodChange)
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Check-out", fontSize = 14.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DropdownTimeSelector(checkOutHour, onCheckOutHourChange)
                    DropdownAmPmSelector(checkOutPeriod, onCheckOutPeriodChange)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("add_filter") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE0A0))
        ) {
            Text("Select filter", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 图片上传部分
        ImageUploadSection(
            selectedImageUri = selectedImageUri,
            onImageSelect = onImageSelect
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Save button
        Button(
            onClick = onSaveChalet,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE0A0)),
            enabled = chaletName.isNotBlank() && address.isNotBlank() && price.isNotBlank()
        ) {
            Text("Save Chalet", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun AddChaletTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.DarkGray,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.DarkGray
        )
    )
}

@Composable
private fun PriceTextField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Price / night") },
        leadingIcon = { Text("RM", fontWeight = FontWeight.Bold) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.DarkGray,
            cursorColor = Color.Black
        )
    )
}
    @Composable
    fun DropdownSelector(
        selected: String,
        options: List<String>,
        onSelect: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = modifier) {
            OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(selected, color = Color.Black)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            onSelect(it)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun DropdownTimeSelector(selected: String, onSelect: (String) -> Unit) {
        val options = listOf(
            "12:00", "1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00"
        )
        var expanded by remember { mutableStateOf(false) }
        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(selected, color = Color.Black)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = {
                        onSelect(it); expanded = false
                    })
                }
            }
        }
    }

    @Composable
    fun DropdownAmPmSelector(selected: String, onSelect: (String) -> Unit) {
        val options = listOf("AM", "PM")
        var expanded by remember { mutableStateOf(false) }
        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(selected, color = Color.Black)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = {
                        onSelect(it); expanded = false
                    })
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterOptionsPage(navController: NavController) {
    val selectedOptions = remember { mutableStateMapOf<String, Boolean>() }
    val nearbyTimes = remember { mutableStateMapOf<String, String>() } // For storing time selections

    // Nearby options
    val nearbyOptions = listOf(
        "Restaurant", "Grocery Store", "Gas Station", "Hospital",
        "Shopping Mall", "Beach", "Tourist Attraction", "Public Transport"
    )

    val timeOptions = listOf("5 min", "10 min", "15 min", "30 min")

    Scaffold(
        modifier = Modifier.background(Color(0xFFFFE0A0)),
        containerColor = Color(0xFFFFF3E0),
        topBar = {
            TopAppBar(
                title = { Text("Filter Options") },
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
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Environment & Surroundings Section
            Text("Environment & Surroundings", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            OptionGroup(
                options = listOf(
                    "Beachfront",
                    "Forest / Nature Retreat",
                    "Mountain View",
                    "Lakefront",
                    "Countryside",
                    "Island Getaway"
                ),
                selected = selectedOptions
            )

            Text("Nearby", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            OptionGroup(
                options = nearbyOptions,
                selected = selectedOptions
            )

            // Show dropdowns for each selected nearby option
            nearbyOptions.forEach { nearbyOption ->
                if (selectedOptions[nearbyOption] == true) {
                    Spacer(modifier = Modifier.height(4.dp))

                    // Individual nearby option with its dropdowns
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = nearbyOption,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // By Car dropdown (smaller)
                            var expandedCarTime by remember { mutableStateOf(false) }
                            Box(modifier = Modifier.weight(1f)) {
                                OutlinedButton(
                                    onClick = { expandedCarTime = true },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(36.dp),
                                    contentPadding = PaddingValues(8.dp)
                                ) {
                                    Text(
                                        "Car: ${nearbyTimes["${nearbyOption}_car"] ?: "5 min"}",
                                        fontSize = 12.sp,
                                        color = Color.Black
                                    )
                                    Icon(
                                        Icons.Default.ArrowDropDown,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                DropdownMenu(
                                    expanded = expandedCarTime,
                                    onDismissRequest = { expandedCarTime = false }
                                ) {
                                    timeOptions.forEach { time ->
                                        DropdownMenuItem(
                                            text = { Text(time, fontSize = 14.sp) },
                                            onClick = {
                                                nearbyTimes["${nearbyOption}_car"] = time
                                                expandedCarTime = false
                                            }
                                        )
                                    }
                                }
                            }

                            // By Walking dropdown (smaller)
                            var expandedWalkingTime by remember { mutableStateOf(false) }
                            Box(modifier = Modifier.weight(1f)) {
                                OutlinedButton(
                                    onClick = { expandedWalkingTime = true },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(36.dp),
                                    contentPadding = PaddingValues(8.dp)
                                ) {
                                    Text(
                                        "Walk: ${nearbyTimes["${nearbyOption}_walk"] ?: "5 min"}",
                                        fontSize = 12.sp,
                                        color = Color.Black
                                    )
                                    Icon(
                                        Icons.Default.ArrowDropDown,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                DropdownMenu(
                                    expanded = expandedWalkingTime,
                                    onDismissRequest = { expandedWalkingTime = false }
                                ) {
                                    timeOptions.forEach { time ->
                                        DropdownMenuItem(
                                            text = { Text(time, fontSize = 14.sp) },
                                            onClick = {
                                                nearbyTimes["${nearbyOption}_walk"] = time
                                                expandedWalkingTime = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Property Features Section
            Text("Property Features", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            OptionGroup(
                options = listOf(
                    "Private Pool",
                    "Free Parking",
                    "BBQ Facilities",
                    "Kitchen Access",
                    "Pet Friendly",
                    "Jacuzzi / Hot Tub"
                ),
                selected = selectedOptions
            )

            // Stay Type & Experience Section
            Text("Stay Type & Experience", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            OptionGroup(
                options = listOf(
                    "Family Friendly",
                    "Romantic Staycation",
                    "Solo Retreat",
                    "Group Getaway",
                    "Luxury Escape",
                    "Budget Friendly"
                ),
                selected = selectedOptions
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Confirm Button at the bottom
            Button(
                onClick = {
                    // TODO: Handle selectedOptions (Boolean) and nearbyTimes (String)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCC80))
            ) {
                Text("Confirm", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}




@Composable
    fun OptionGroup(options: List<String>, selected: MutableMap<String, Boolean>) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            options.chunked(2).forEach { rowOptions ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowOptions.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Checkbox(
                                checked = selected[option] ?: false,
                                onCheckedChange = { isChecked -> selected[option] = isChecked }
                            )
                            Text(option, fontSize = 16.sp)
                        }
                    }
                    if (rowOptions.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
