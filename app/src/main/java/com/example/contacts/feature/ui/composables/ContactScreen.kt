package com.example.contacts.feature.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contacts.R
import com.example.contacts.feature.ui.composables.dialogs.AddContactDialog
import com.example.contacts.feature.ui.composables.dialogs.EditContactDialog
import com.example.contacts.feature.ui.states.SortType
import com.example.contacts.feature.ui.viewModel.ContactViewModel
import com.example.contacts.ui.theme.dimens

@Composable
fun ContactScreen(contactViewModel: ContactViewModel) {
    val uiState by contactViewModel.uiState.collectAsStateWithLifecycle()

    var searchText by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    contactViewModel.showAddDialog()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24),
                    contentDescription = "Add contact",
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth()
                    .padding(MaterialTheme.dimens.paddingLarge)
            ) {
                items(SortType.entries, key = { sortType -> sortType }) { sortType ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = uiState.sortType == sortType,
                            onClick = {
                                contactViewModel.sortContacts(sortType)
                            }
                        )
                        Text(sortType.name)
                    }
                }
            }
            SearchField(
                contactViewModel = contactViewModel,
                textValue = searchText,
                onChangeText = { searchText = it }
            )
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingExtraLarge),
                text = stringResource(R.string.contacts),
                style = MaterialTheme.typography.titleLarge
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.paddingLarge),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingMedium)
            ) {
                items(uiState.contacts, key = { contact -> contact.id }) { contact ->
                    ContactCard(
                        contact = contact,
                        onEdit = { contactViewModel.showEditDialog(contact) },
                        onDelete = { contactViewModel.deleteContact(contact) }
                    )
                }
            }
        }

        if (uiState.showAddDialog) {
            AddContactDialog(
                onDismiss = { contactViewModel.hideAddDialog() },
                contactViewModel = contactViewModel
            )
        }
        if (uiState.showEditDialog) {
            EditContactDialog(
                onDismiss = { contactViewModel.hideEditDialog() },
                contactViewModel = contactViewModel
            )
        }
    }
}