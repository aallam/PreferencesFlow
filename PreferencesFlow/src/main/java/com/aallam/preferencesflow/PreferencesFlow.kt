@file:Suppress("FunctionName")

package com.aallam.preferencesflow

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.aallam.preferencesflow.internal.AndroidPreferencesFlow
import kotlinx.serialization.KSerializer

public interface PreferencesFlow {
    /**
     * Create a boolean preference for [key]. Default is false.
     */
    public fun boolean(key: String): Preference<Boolean>

    /**
     * Create a boolean preference for [key] with a default of [defaultValue].
     */
    public fun boolean(key: String, defaultValue: Boolean): Preference<Boolean>

    /**
     * Create a float preference for [key]. Default is 0.
     */
    public fun float(key: String): Preference<Float>

    /**
     * Create a [Float] preference for [key] with a default of [defaultValue].
     */
    public fun float(key: String, defaultValue: Float): Preference<Float>

    /**
     * Create a float preference for [key]. Default is 0.
     */
    public fun int(key: String): Preference<Int>

    /**
     * Create a [Float] preference for [key] with a default of [defaultValue].
     */
    public fun int(key: String, defaultValue: Int): Preference<Int>

    /**
     * Create a float preference for [key]. Default is 0L.
     */
    public fun long(key: String): Preference<Long>

    /**
     * Create a [Long] preference for [key] with a default of [defaultValue].
     */
    public fun long(key: String, defaultValue: Long): Preference<Long>

    /**
     * Create a preference for type [T] for [key] with a default of [defaultValue].
     */
    public fun <T> serializable(key: String, defaultValue: T?, serializable: KSerializer<T>): Preference<T?>

    /**
     * Create a preference for type [T] for [key] with a default null.
     */
    public fun <T> serializable(key: String, serializable: KSerializer<T>): Preference<T?>

    /**
     * Create a float preference for [key]. Default is empty string.
     */
    public fun string(key: String): Preference<String?>

    /**
     * Create a [String] preference for [key] with a default of [defaultValue].
     */
    public fun string(key: String, defaultValue: String?): Preference<String?>

    /**
     * Create a string set preference for {@code key}. Default is [EmptySet].
     */
    public fun stringSet(key: String): Preference<Set<String>?>

    /**
     * Create a [String] set preference for [key] with a default of [defaultValue].
     */
    public fun stringSet(key: String, defaultValue: Set<String>?): Preference<Set<String>?>

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

/**
 * Create a [PreferencesFlow] instance.
 *
 * @param context Android context
 */
public fun PreferencesFlow(context: Context): PreferencesFlow = PreferencesFlow(getDefaultSharedPreferences(context))
