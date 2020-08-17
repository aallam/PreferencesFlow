package com.aallam.preferencesflow.internal.adapter

import android.content.SharedPreferences
import com.aallam.preferencesflow.internal.PreferenceAdapter

/**
 * Implementation of [PreferenceAdapter] for [Set] of [String]s.
 */
internal object StringSetAdapter : PreferenceAdapter<Set<String>?> {

    override fun get(key: String, preferences: SharedPreferences, defaultValue: Set<String>?): Set<String>? {
        return preferences.getStringSet(key, defaultValue)
    }

    override fun set(key: String, value: Set<String>?, editor: SharedPreferences.Editor) {
        editor.putStringSet(key, value)
    }
}
