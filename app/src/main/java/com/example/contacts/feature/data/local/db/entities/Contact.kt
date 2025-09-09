package com.example.contacts.feature.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)
