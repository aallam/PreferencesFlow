package com.aallam.preferencesflow.internal.adapter

import android.content.SharedPreferences
import com.aallam.preferencesflow.internal.PreferenceAdapter

/**
 * Implementation of [PreferenceAdapter] for [Long].
 */
internal object LongAdapter : PreferenceAdapter<Long> {

    override fun get(key: String, preferences: SharedPreferences, defaultValue: Long): Long? {
        return preferences.getLong(key, defaultValue)
    }

    override fun set(key: String, value: Long, editor: SharedPreferences.Editor) {
        editor.putLong(key, value)
    }
}
