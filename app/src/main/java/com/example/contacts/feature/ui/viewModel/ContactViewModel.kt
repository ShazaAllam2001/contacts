package com.example.contacts.feature.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.feature.data.local.db.ContactDao
import com.example.contacts.feature.data.local.db.entities.Contact
import com.example.contacts.feature.ui.states.ContactState
import com.example.contacts.feature.ui.states.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactDao: ContactDao
): ViewModel() {

    private val _uiState = MutableStateFlow(ContactState())
    private var _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _contacts = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.FIRST_NAME -> contactDao.getContactsByFirstName()
            SortType.LAST_NAME -> contactDao.getContactsByLastName()
            SortType.PHONE_NUMBER -> contactDao.getContactsByPhoneNumber()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val uiState: StateFlow<ContactState> =
        combine(_uiState, _sortType, _contacts) { uiState, sortType, contacts ->
            uiState.copy(
                sortType = sortType,
                contacts = contacts
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())



    fun setFirstName(firstName: String) {
        _uiState.update {
            it.copy(firstName = firstName)
        }
    }

    fun setLastName(lastName: String) {
        _uiState.update {
            it.copy(lastName = lastName)
        }
    }

    fun setPhoneNumber(phoneNumber: String) {
        _uiState.update {
            it.copy(phoneNumber = phoneNumber)
        }
    }

    fun sortContacts(sortType: SortType) {
        _sortType.value = sortType
    }

    /* Dialogs */
    fun showAddDialog() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(showAddDialog = true)
            }
        }
    }

    fun hideAddDialog() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(showAddDialog = false)
            }
        }
    }

    fun showEditDialog() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(showEditDialog = true)
            }
        }
    }

    fun hideEditDialog() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(showEditDialog = false)
            }
        }
    }

    /* Update Database */
    fun addContact() {
        viewModelScope.launch {
            val firstName = _uiState.value.firstName
            val lastName = _uiState.value.lastName
            val phoneNumber = _uiState.value.phoneNumber
            val contact = Contact(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
            contactDao.insertContact(contact)
        }
    }

    fun updateContact() {
        viewModelScope.launch {
            val firstName = _uiState.value.firstName
            val lastName = _uiState.value.lastName
            val phoneNumber = _uiState.value.phoneNumber
            val contact = Contact(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
            contactDao.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            contactDao.deleteContact(contact)
        }
    }
}