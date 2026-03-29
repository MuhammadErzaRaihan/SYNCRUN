package com.example.syncrun.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val username: String,
    val password: String,
    val name: String,
    val currentWeight: Double,
    val targetWeight: Double,
    val totalKm: Double,
    val role: String, // "Premium" or "Free"
    val dailyCheckInDone: Boolean = false
)
