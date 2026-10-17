package com.kawanova.fontstyler.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fontstyler_prefs")

class TypographyRepository(private val context: Context) {

    private val SAVED_STYLES_KEY = stringPreferencesKey("saved_type_styles_json")

    /**
     * 保存済みスタイル一覧のFlow
     */
    val savedStylesFlow: Flow<List<SavedTypeStyle>> = context.dataStore.data.map { prefs ->
        val raw = prefs[SAVED_STYLES_KEY]
        if (raw.isNullOrEmpty()) {
            emptyList()
        } else {
            try {
                val array = JSONArray(raw)
                val list = mutableListOf<SavedTypeStyle>()
                for (i in 0 until array.length()) {
                    list.add(SavedTypeStyle.fromJson(array.getJSONObject(i)))
                }
                list.sortedByDescending { it.updatedAt }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    /**
     * スタイルの保存または更新
     */
    suspend fun saveStyle(style: SavedTypeStyle) {
        context.dataStore.edit { prefs ->
            val raw = prefs[SAVED_STYLES_KEY]
            val list = if (raw.isNullOrEmpty()) {
                mutableListOf()
            } else {
                try {
                    val array = JSONArray(raw)
                    val l = mutableListOf<SavedTypeStyle>()
                    for (i in 0 until array.length()) {
                        l.add(SavedTypeStyle.fromJson(array.getJSONObject(i)))
                    }
                    l
                } catch (_: Exception) {
                    mutableListOf()
                }
            }

            val index = list.indexOfFirst { it.id == style.id }
            if (index >= 0) {
                list[index] = style.copy(updatedAt = System.currentTimeMillis())
            } else {
                list.add(0, style.copy(updatedAt = System.currentTimeMillis()))
            }

            val array = JSONArray()
            list.forEach { array.put(it.toJson()) }
            prefs[SAVED_STYLES_KEY] = array.toString()
        }
    }

    /**
     * スタイルの削除
     */
    suspend fun deleteStyle(id: String) {
        context.dataStore.edit { prefs ->
            val raw = prefs[SAVED_STYLES_KEY] ?: return@edit
            try {
                val array = JSONArray(raw)
                val newArray = JSONArray()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    if (obj.optString("id") != id) {
                        newArray.put(obj)
                    }
                }
                prefs[SAVED_STYLES_KEY] = newArray.toString()
            } catch (_: Exception) {}
        }
    }
}
