package com.example.contacts.feature.ui.composables.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contacts.R
import com.example.contacts.feature.ui.viewModel.ContactViewModel
import com.example.contacts.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactDialog(
    onDismiss: () -> Unit,
    contactViewModel: ContactViewModel
) {
    val uiState by contactViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        contactViewModel.setFirstName(uiState.contact?.firstName ?: "")
        contactViewModel.setLastName(uiState.contact?.lastName ?: "")
        contactViewModel.setPhoneNumber(uiState.contact?.phoneNumber ?: "")
    }

    BasicAlertDialog(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(MaterialTheme.dimens.paddingExtraLarge),
        onDismissRequest = onDismiss
    )  {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingLarge)
        ) {
            Text(
                text = stringResource(R.string.edit_contact),
                style = MaterialTheme.typography.labelLarge
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.firstName,
                onValueChange = { contactViewModel.setFirstName(it) },
                placeholder = { Text(stringResource(R.string.first_name)) },
                singleLine = true,
                textStyle = MaterialTheme.typography.labelMedium
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.lastName,
                onValueChange = { contactViewModel.setLastName(it) },
                placeholder = { Text(stringResource(R.string.last_name)) },
                singleLine = true,
                textStyle = MaterialTheme.typography.labelMedium
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.phoneNumber,
                onValueChange = { contactViewModel.setPhoneNumber(it) },
                placeholder = { Text(stringResource(R.string.phone_number)) },
                singleLine = true,
                textStyle = MaterialTheme.typography.labelMedium
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ElevatedButton(
                    onClick = {
                        onDismiss()
                        contactViewModel.updateContact()
                    }
                ) {
                    Text(
                        stringResource(R.string.save_contact),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                ElevatedButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(
                        stringResource(R.string.cancel),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}