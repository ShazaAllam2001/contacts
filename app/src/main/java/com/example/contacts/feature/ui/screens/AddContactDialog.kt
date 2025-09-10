package com.example.contacts.feature.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contacts.R
import com.example.contacts.feature.ui.viewModel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactDialog(
    onDismiss: () -> Unit,
    contactViewModel: ContactViewModel
) {
    val uiState by contactViewModel.uiState.collectAsStateWithLifecycle()

    BasicAlertDialog(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp),
        onDismissRequest = onDismiss
    )  {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.firstName,
                onValueChange = { contactViewModel.setFirstName(it) },
                placeholder = { Text(stringResource(R.string.first_name)) },
                singleLine = true,
                textStyle = MaterialTheme.typography.labelLarge
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.lastName,
                onValueChange = { contactViewModel.setLastName(it) },
                placeholder = { Text(stringResource(R.string.last_name)) },
                singleLine = true,
                textStyle = MaterialTheme.typography.labelLarge
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.phoneNumber,
                onValueChange = { contactViewModel.setPhoneNumber(it) },
                placeholder = { Text(stringResource(R.string.phone_number)) },
                singleLine = true,
                textStyle = MaterialTheme.typography.labelLarge
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onDismiss()
                        contactViewModel.saveContact()
                    }
                ) {
                    Text(stringResource(R.string.add_contact))
                }
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}