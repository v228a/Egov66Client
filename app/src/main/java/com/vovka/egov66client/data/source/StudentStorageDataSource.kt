package com.vovka.egov66client.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentStorageDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val Context.storage: DataStore<Preferences> by preferencesDataStore(name = NAME)

    val studentId: Flow<String?> = context.storage.data.map { preferences ->
        preferences[STUDENT_KEY]
    }

    val aiss2Auth: Flow<String?> = context.storage.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    suspend fun updateStudentId(token: String?) {
        context.storage.edit { settings ->
            if (token != null) {
                settings[STUDENT_KEY] = "$token"
            } else {
                settings.remove(STUDENT_KEY)
            }
        }
    }

    suspend fun updateAiss2Auth(token: String?) {
        context.storage.edit { settings ->
            if (token != null) {
                settings[TOKEN_KEY] = "$token"
            } else {
                settings.remove(TOKEN_KEY)
            }
        }
    }
    
    

    private companion object {
        const val NAME = "Account"
        val STUDENT_KEY = stringPreferencesKey("studentId")
        val TOKEN_KEY = stringPreferencesKey("Aiss2Auth")
     }
}