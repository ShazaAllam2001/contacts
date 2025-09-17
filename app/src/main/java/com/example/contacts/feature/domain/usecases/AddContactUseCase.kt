package com.example.contacts.feature.domain.usecases

import com.example.contacts.feature.data.local.db.entities.Contact
import com.example.contacts.feature.domain.model.ContactResult
import com.example.contacts.feature.domain.repository.ContactsRepository
import javax.inject.Inject

class AddContactUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    suspend operator fun invoke(contact: Contact): ContactResult {
        val result = contactsRepository.addContact(contact)
        if (result.isSuccess)
            return ContactResult.Success
        return ContactResult.Error(result.exceptionOrNull()?.message ?: "")
    }
}