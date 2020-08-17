package com.aallam.preferencesflow.internal.adapter

import android.content.SharedPreferences
import com.aallam.preferencesflow.internal.PreferenceAdapter

/**
 * Implementation of [PreferenceAdapter] for [Boolean].
 */
internal object BooleanAdapter : PreferenceAdapter<Boolean> {

    override fun get(key: String, preferences: SharedPreferences, defaultValue: Boolean): Boolean? {
        return preferences.getBoolean(key, defaultValue)
    }

    override fun set(key: String, value: Boolean, editor: SharedPreferences.Editor) {
        editor.putBoolean(key, value)
    }
}
