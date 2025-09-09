package com.example.contacts.feature.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contacts.R
import com.example.contacts.feature.ui.viewModel.ContactViewModel

@Composable
fun ContactScreen(contactViewModel: ContactViewModel) {
    val uiState by contactViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    contactViewModel.saveContact()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24),
                    contentDescription = "add contact",
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(uiState.contacts, key = { contact -> contact.id }) { contact ->
                Row(

                ) {
                    Text("${contact.firstName} ${contact.lastName}")
                }
            }
        }
    }
}