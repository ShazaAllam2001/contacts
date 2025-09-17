package com.example.contacts.feature.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.contacts.R
import com.example.contacts.feature.ui.viewModel.ContactViewModel
import com.example.contacts.ui.theme.dimens

@Composable
fun SearchField(
    contactViewModel: ContactViewModel,
    textValue: String,
    onChangeText: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.padding(MaterialTheme.dimens.paddingLarge)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textValue,
            onValueChange = onChangeText,
            prefix = {
                Icon(
                    painter = painterResource(R.drawable.search_24),
                    contentDescription = "Search Button"
                )
            },
            placeholder = {
                Text(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingSmall),
                    text = stringResource(R.string.search_for_contact),
                    style = MaterialTheme.typography.titleSmall
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(MaterialTheme.dimens.roundCorner),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    contactViewModel.searchContacts(textValue)
                    focusManager.clearFocus()
                }
            )
        )
    }
}