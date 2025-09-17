package com.example.contacts.feature.data.repository

import com.example.contacts.feature.data.local.db.ContactDao
import com.example.contacts.feature.data.local.db.entities.Contact
import com.example.contacts.feature.domain.repository.ContactsRepository
import com.example.contacts.feature.ui.states.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao
): ContactsRepository {

    override suspend fun addContact(contact: Contact): Result<Unit> {
        return runCatching {
            contactDao.insertContact(contact)
        }
    }

    override suspend fun updateContact(contact: Contact): Result<Unit> {
        return runCatching {
            contactDao.updateContact(contact)
        }
    }

    override suspend fun deleteContact(contact: Contact): Result<Unit> {
        return runCatching {
            contactDao.deleteContact(contact)
        }
    }

    override suspend fun getContacts(sortType: SortType, name: String): Result<Flow<List<Contact>>> {
        return runCatching {
            when (sortType) {
                SortType.FIRST_NAME -> {
                    if (name.isBlank())
                        contactDao.getContactsByFirstName()
                    else
                        contactDao.getContactsByFirstName(name)
                }
                SortType.LAST_NAME -> {
                    if (name.isBlank())
                        contactDao.getContactsByFirstName()
                    else
                        contactDao.getContactsByLastName(name)
                }
                SortType.PHONE_NUMBER -> {
                    if (name.isBlank())
                        contactDao.getContactsByPhoneNumber()
                    else
                        contactDao.getContactsByPhoneNumber(name)
                }
            }
        }
    }
}