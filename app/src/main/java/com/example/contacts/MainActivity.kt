package com.example.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contacts.feature.ui.ContactScreen
import com.example.contacts.feature.ui.viewModel.ContactViewModel
import com.example.contacts.ui.theme.ContactsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsTheme {
                val contactViewModel: ContactViewModel = hiltViewModel()
                ContactScreen(contactViewModel)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContactsTheme {
        val contactViewModel: ContactViewModel = hiltViewModel()
        ContactScreen(contactViewModel)
    }
}