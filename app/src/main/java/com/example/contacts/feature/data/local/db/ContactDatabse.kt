package com.example.contacts.feature.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contacts.feature.data.local.db.entities.Contact

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase: RoomDatabase() {
    abstract val dao: ContactDao

}