package com.example.contacts.feature.data

import android.content.Context
import com.example.contacts.feature.data.local.db.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.room.Room
import com.example.contacts.feature.data.local.db.ContactDao
import com.example.contacts.feature.data.repository.ContactsRepositoryImpl
import com.example.contacts.feature.domain.repository.ContactsRepository

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ContactDatabase {
        return Room.databaseBuilder(
            context,
            ContactDatabase::class.java,
            "contacts_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactDao(db: ContactDatabase): ContactDao = db.dao

    @Provides
    @Singleton
    fun provideContactRepo(dao: ContactDao): ContactsRepository {
        return ContactsRepositoryImpl(dao)
    }
}