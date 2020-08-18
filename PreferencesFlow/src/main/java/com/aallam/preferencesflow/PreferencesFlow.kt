@file:Suppress("FunctionName")

package com.aallam.preferencesflow

import android.content.SharedPreferences
import com.aallam.preferencesflow.internal.AndroidPreferencesFlow
import kotlinx.serialization.KSerializer

public interface PreferencesFlow {
    /**
     * Create a boolean preference for [key]. Default is false.
     */
    public fun getBoolean(key: String): Preference<Boolean>

    /**
     * Create a boolean preference for [key] with a default of [defaultValue].
     */
    public fun getBoolean(key: String, defaultValue: Boolean): Preference<Boolean>

    /**
     * Create a float preference for [key]. Default is 0.
     */
    public fun getFloat(key: String): Preference<Float>

    /**
     * Create a [Float] preference for [key] with a default of [defaultValue].
     */
    public fun getFloat(key: String, defaultValue: Float): Preference<Float>

    /**
     * Create a float preference for [key]. Default is 0.
     */
    public fun getInteger(key: String): Preference<Int>

    /**
     * Create a [Float] preference for [key] with a default of [defaultValue].
     */
    public fun getInteger(key: String, defaultValue: Int): Preference<Int>

    /**
     * Create a float preference for [key]. Default is 0L.
     */
    public fun getLong(key: String): Preference<Long>

    /**
     * Create a [Long] preference for [key] with a default of [defaultValue].
     */
    public fun getLong(key: String, defaultValue: Long): Preference<Long>

    /**
     * Create a preference for type [T] for [key] with a default of [defaultValue].
     */
    public fun <T> getSerializable(key: String, defaultValue: T, serializable: KSerializer<T>): Preference<T>

    /**
     * Create a float preference for [key]. Default is empty string.
     */
    public fun getString(key: String): Preference<String?>

    /**
     * Create a [String] preference for [key] with a default of [defaultValue].
     */
    public fun getString(key: String, defaultValue: String): Preference<String?>

    /**
     * Create a string set preference for {@code key}. Default is [EmptySet].
     */
    public fun getStringSet(key: String): Preference<Set<String>?>

    /**
     * Create a [String] set preference for [key] with a default of [defaultValue].
     */
    public fun getStringSet(key: String, defaultValue: Set<String>?): Preference<Set<String>?>

    /**
     * Remove all values from the preferences
     */
    public fun clear()
}

/**
 * Create a [PreferencesFlow] instance.
 *
 * @param preferences Android SharedPreferences
 */
public fun PreferencesFlow(preferences: SharedPreferences): PreferencesFlow = AndroidPreferencesFlow(preferences)
