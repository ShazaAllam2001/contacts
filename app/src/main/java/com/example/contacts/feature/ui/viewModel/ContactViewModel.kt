package com.example.contacts.feature.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.feature.data.local.db.entities.Contact
import com.example.contacts.feature.domain.model.ContactsResult
import com.example.contacts.feature.domain.usecases.AddContactUseCase
import com.example.contacts.feature.domain.usecases.DeleteContactUseCase
import com.example.contacts.feature.domain.usecases.GetContactsUseCase
import com.example.contacts.feature.domain.usecases.UpdateContactUseCase
import com.example.contacts.feature.ui.states.ContactState
import com.example.contacts.feature.ui.states.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ContactViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val getContactsUseCase: GetContactsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ContactState())
    private var _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private var _searchText = MutableStateFlow("")

    private val _contacts: StateFlow<List<Contact>> = _sortType
        .combine(_searchText) { sortType, searchText ->
            sortType to searchText
        }
        .flatMapLatest { (sortType, searchText) ->
            when (val result = getContactsUseCase(sortType, searchText)) {
                is ContactsResult.Success -> result.contacts
                is ContactsResult.Error -> emptyFlow()
            }
        }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val uiState: StateFlow<ContactState> =
        combine(_uiState, _sortType, _contacts) { uiState, sortType, contacts ->
            uiState.copy(
                sortType = sortType,
                contacts = contacts
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())


    fun setFirstName(firstName: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(firstName = firstName)
            }
        }
    }

    fun setLastName(lastName: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(lastName = lastName)
            }
        }
    }

    fun setPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(phoneNumber = phoneNumber)
            }
        }
    }

    fun searchContacts(searchText: String) {
        viewModelScope.launch {
            _searchText.value = searchText
        }
    }

    fun sortContacts(sortType: SortType) {
        viewModelScope.launch {
            _sortType.value = sortType
        }
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

    fun showEditDialog(contact: Contact) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    showEditDialog = true,
                    contact = contact
                )
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
            addContactUseCase(contact)
        }
    }

    fun updateContact() {
        viewModelScope.launch {
            val firstName = _uiState.value.firstName
            val lastName = _uiState.value.lastName
            val phoneNumber = _uiState.value.phoneNumber
            val contact = Contact(
                id = _uiState.value.contact?.id ?: 0,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
            updateContactUseCase(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            deleteContactUseCase(contact)
        }
    }
}