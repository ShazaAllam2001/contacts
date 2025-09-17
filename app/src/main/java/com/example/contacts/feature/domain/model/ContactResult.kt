package com.example.contacts.feature.domain.model

sealed class ContactResult {
    data object Success: ContactResult()
    data class Error(val message: String) : ContactResult()
}