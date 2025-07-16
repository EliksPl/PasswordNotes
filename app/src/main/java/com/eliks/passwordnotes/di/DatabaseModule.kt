package com.eliks.passwordnotes.di

import android.content.Context
import androidx.room.Room
import com.eliks.passwordnotes.data.local.NoteDao
import com.eliks.passwordnotes.data.local.NoteDatabase
import com.eliks.passwordnotes.data.local.NoteDatabase.Companion.NOTE_DB_TABLE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): NoteDatabase{
        return Room.databaseBuilder(appContext, NoteDatabase::class.java, NOTE_DB_TABLE_NAME).build()
    }

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao{
        return database.NoteDao()
    }

}