package com.aallam.preferencesflow.internal

import android.content.SharedPreferences

/**
 * Stores and retrieves instances of [T] in SharedPreferences.
 **/
internal interface PreferenceAdapter<T> {

    /**
     * Retrieve the value for [key] from [preferences], or [defaultValue] if the preference is unset.
     */
    fun get(key: String, preferences: SharedPreferences, defaultValue: T): T?

    /**
     * Store [value] for [key] in `SharedPreferences.Editor`.
     */
    fun set(key: String, value: T, editor: SharedPreferences.Editor)
}
