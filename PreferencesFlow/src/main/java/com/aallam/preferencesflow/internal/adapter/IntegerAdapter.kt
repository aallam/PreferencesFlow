package com.aallam.preferencesflow.internal.adapter

import android.content.SharedPreferences
import com.aallam.preferencesflow.internal.PreferenceAdapter

/**
 * Implementation of [PreferenceAdapter] for [Int].
 */
internal object IntegerAdapter : PreferenceAdapter<Int> {

    override fun get(key: String, preferences: SharedPreferences, defaultValue: Int): Int? {
        return preferences.getInt(key, defaultValue)
    }

    override fun set(key: String, value: Int, editor: SharedPreferences.Editor) {
        editor.putInt(key, value)
    }
}
