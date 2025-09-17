package com.example.contacts.feature.domain.usecases

import com.example.contacts.feature.domain.model.ContactsResult
import com.example.contacts.feature.domain.repository.ContactsRepository
import com.example.contacts.feature.ui.states.SortType
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    suspend operator fun invoke(sortType: SortType, name: String): ContactsResult {
        val result = contactsRepository.getContacts(sortType, name)
        if (result.isSuccess)
            return ContactsResult.Success(result.getOrNull() ?: emptyFlow())
        return ContactsResult.Error(result.exceptionOrNull()?.message ?: "")
    }
}