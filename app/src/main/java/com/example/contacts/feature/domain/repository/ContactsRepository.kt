package com.example.contacts.feature.domain.repository

import com.example.contacts.feature.data.local.db.entities.Contact
import com.example.contacts.feature.ui.states.SortType
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    suspend fun addContact(contact: Contact): Result<Unit>
    suspend fun updateContact(contact: Contact): Result<Unit>
    suspend fun deleteContact(contact: Contact): Result<Unit>
    suspend fun getContacts(sortType: SortType): Result<Flow<List<Contact>>>
}