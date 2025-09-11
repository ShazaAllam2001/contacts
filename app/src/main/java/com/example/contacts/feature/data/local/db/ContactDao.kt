package com.example.contacts.feature.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.contacts.feature.data.local.db.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao{
    @Upsert
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Query("SELECT * FROM contacts ORDER BY firstName ASC")
    fun getContactsByFirstName(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts ORDER BY lastName ASC")
    fun getContactsByLastName(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts ORDER BY phoneNumber ASC")
    fun getContactsByPhoneNumber(): Flow<List<Contact>>
}
