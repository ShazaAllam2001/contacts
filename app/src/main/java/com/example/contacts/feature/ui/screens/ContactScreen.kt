package com.example.contacts.feature.ui.screens

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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contacts.R
import com.example.contacts.feature.ui.states.SortType
import com.example.contacts.feature.ui.viewModel.ContactViewModel

@Composable
fun ContactScreen(contactViewModel: ContactViewModel) {
    val uiState by contactViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    contactViewModel.showDialog()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24),
                    contentDescription = "add contact",
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth()
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.contacts, key = { contact -> contact.id }) { contact ->
                    ContactCard(
                        contact = contact,
                        onDelete = { contactViewModel.deleteContact(contact) }
                    )
                }
            }
        }

        if (uiState.showDialog) {
            AddContactDialog(
                onDismiss = { contactViewModel.hideDialog() },
                contactViewModel = contactViewModel
            )
        }
    }
}