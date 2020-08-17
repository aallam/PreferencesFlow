package com.aallam.preferencesflow.internal.adapter

import android.content.SharedPreferences
import com.aallam.preferencesflow.internal.PreferenceAdapter

/**
 * Implementation of [PreferenceAdapter] for [String]?.
 */
internal object StringAdapter : PreferenceAdapter<String?> {

    override fun get(key: String, preferences: SharedPreferences, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    override fun set(key: String, value: String?, editor: SharedPreferences.Editor) {
        editor.putString(key, value)
    }
}
