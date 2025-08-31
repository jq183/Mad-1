package com.example.assignmenttest.Data

data class ChaletData(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val maxGuests: String = "",
    val rooms: String = "",
    val bathrooms: String = "",
    val pricePerNight: String = "",
    val specialPrices: List<Pair<String, String>> = emptyList(),
    val beds: List<BedItem> = emptyList(),
    val description: String = "",
    val checkInTime: String = "",
    val checkOutTime: String = "",
    val selectedFilters: Map<String, Boolean> = emptyMap(),
    val nearbyTimes: Map<String, String> = emptyMap(),
    val imageUri: String? = null,
    val status: ChaletStatus = ChaletStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis()
)

enum class ChaletStatus {
    PENDING,
    APPROVED,
    REJECTED
}

data class BedItem(val qty: String, val type: String)

// Global Data Manager
object ChaletDataManager {
    private val chalets = mutableListOf<ChaletData>()

    fun addChalet(chalet: ChaletData): String {
        val id = "chalet_${System.currentTimeMillis()}"
        val chaletWithId = chalet.copy(id = id)
        chalets.add(chaletWithId)
        return id
    }

    fun getAllChalets(): List<ChaletData> = chalets.toList()

    fun updateChaletStatus(id: String, status: ChaletStatus) {
        val index = chalets.indexOfFirst { it.id == id }
        if (index != -1) {
            chalets[index] = chalets[index].copy(status = status)
        }
    }

    fun deleteChalet(id: String) {
        chalets.removeAll { it.id == id }
    }
}