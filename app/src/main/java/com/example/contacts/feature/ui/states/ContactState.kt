package com.example.contacts.feature.ui.states

import com.example.contacts.feature.data.local.db.entities.Contact

data class ContactState(
    val contact: Contact? = null,
    val contacts: List<Contact> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val showAddDialog: Boolean = false,
    val showEditDialog: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME,
    val errorMessage: String = ""
)