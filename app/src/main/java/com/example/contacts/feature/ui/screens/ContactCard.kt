package com.example.contacts.feature.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.contacts.R
import com.example.contacts.feature.data.local.db.entities.Contact
import com.example.contacts.ui.theme.dimens

@Composable
fun ContactCard(
    contact: Contact,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(MaterialTheme.dimens.paddingLarge)
            ) {
                Text(
                    text = "${contact.firstName} ${contact.lastName}",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = contact.phoneNumber,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Row {
                IconButton(
                    onClick = onEdit
                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit_24),
                        contentDescription = "Delete Contact"
                    )
                }
                IconButton(
                    onClick = onDelete
                ) {
                    Icon(
                        painter = painterResource(R.drawable.delete_24),
                        contentDescription = "Delete Contact"
                    )
                }
            }
        }
    }
}