package com.example.contacts.feature.domain.model

import com.example.contacts.feature.data.local.db.entities.Contact
import kotlinx.coroutines.flow.Flow

sealed class ContactsResult {
    data class Success(val contacts: Flow<List<Contact>>) : ContactsResult()
    data class Error(val message: String) : ContactsResult()
}