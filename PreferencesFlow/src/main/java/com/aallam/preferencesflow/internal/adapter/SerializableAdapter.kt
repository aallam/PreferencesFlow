package com.aallam.preferencesflow.internal.adapter

import android.content.SharedPreferences
import com.aallam.preferencesflow.internal.PreferenceAdapter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

/**
 * Implementation of [PreferenceAdapter] for serializable objects.
 *
 * @param serializer serializer for encoding and decoding objects.
 */
internal class SerializableAdapter<T>(private val serializer: KSerializer<T>) : PreferenceAdapter<T> {

    override fun get(key: String, preferences: SharedPreferences, defaultValue: T): T? {
        val raw = preferences.getString(key, null) ?: return defaultValue
        return Json.decodeFromString(serializer, raw)
    }

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        val serialized = Json.encodeToString(serializer, value)
        editor.putString(key, serialized)
    }
}
