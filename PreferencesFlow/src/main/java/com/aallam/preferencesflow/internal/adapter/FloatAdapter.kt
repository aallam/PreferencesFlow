package com.aallam.preferencesflow.internal.adapter

import android.content.SharedPreferences
import com.aallam.preferencesflow.internal.PreferenceAdapter

/**
 * Implementation of [PreferenceAdapter] for [Float].
 */
internal object FloatAdapter : PreferenceAdapter<Float> {

    override fun get(key: String, preferences: SharedPreferences, defaultValue: Float): Float? {
        return preferences.getFloat(key, defaultValue)
    }

    override fun set(key: String, value: Float, editor: SharedPreferences.Editor) {
        editor.putFloat(key, value)
    }
}
