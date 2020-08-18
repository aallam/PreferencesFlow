package com.aallam.preferencesflow.internal

import android.content.SharedPreferences
import com.aallam.preferencesflow.Preference
import com.aallam.preferencesflow.PreferencesFlow
import com.aallam.preferencesflow.internal.adapter.*
import kotlinx.serialization.KSerializer

/**
 * Implementation of [PreferencesFlow].
 *
 * @param preferences Android SharedPreferences
 */
internal class AndroidPreferencesFlow(
    private val preferences: SharedPreferences
) : PreferencesFlow {

    /**
     * Create a boolean preference for [key]. Default is false.
     */
    override fun getBoolean(key: String): Preference<Boolean> {
        return getBoolean(key, DEFAULT_BOOLEAN)
    }

    /**
     * Create a boolean preference for [key] with a default of [defaultValue].
     */
    override fun getBoolean(key: String, defaultValue: Boolean): Preference<Boolean> {
        return AndroidPreference(key, defaultValue, preferences, BooleanAdapter)
    }


    /**
     * Create a float preference for [key]. Default is 0.
     */
    override fun getFloat(key: String): Preference<Float> {
        return getFloat(key, DEFAULT_FLOAT)
    }

    /**
     * Create a [Float] preference for [key] with a default of [defaultValue].
     */
    override fun getFloat(key: String, defaultValue: Float): Preference<Float> {
        return AndroidPreference(key, defaultValue, preferences, FloatAdapter)
    }

    /**
     * Create a float preference for [key]. Default is 0.
     */
    override fun getInteger(key: String): Preference<Int> {
        return getInteger(key, DEFAULT_INTEGER)
    }

    /**
     * Create a [Float] preference for [key] with a default of [defaultValue].
     */
    override fun getInteger(key: String, defaultValue: Int): Preference<Int> {
        return AndroidPreference(key, defaultValue, preferences, IntegerAdapter)
    }

    /**
     * Create a float preference for [key]. Default is 0L.
     */
    override fun getLong(key: String): Preference<Long> {
        return getLong(key, DEFAULT_LONG)
    }

    /**
     * Create a [Long] preference for [key] with a default of [defaultValue].
     */
    override fun getLong(key: String, defaultValue: Long): Preference<Long> {
        return AndroidPreference(key, defaultValue, preferences, LongAdapter)
    }

    /**
     * Create a preference for type [T] for [key] with a default of [defaultValue].
     */
    override fun <T> getSerializable(key: String, defaultValue: T, serializable: KSerializer<T>): Preference<T> {
        return AndroidPreference(key, defaultValue, preferences, SerializableAdapter(serializable))
    }

    /**
     * Create a float preference for [key]. Default is empty string.
     */
    override fun getString(key: String): Preference<String?> {
        return getString(key, DEFAULT_STRING)
    }

    /**
     * Create a [String] preference for [key] with a default of [defaultValue].
     */
    override fun getString(key: String, defaultValue: String): Preference<String?> {
        return AndroidPreference(key, defaultValue, preferences, StringAdapter)
    }

    /**
     * Create a string set preference for {@code key}. Default is [EmptySet].
     */
    override fun getStringSet(key: String): Preference<Set<String>?> {
        return getStringSet(key, emptySet())
    }

    /**
     * Create a [String] set preference for [key] with a default of [defaultValue].
     */
    override fun getStringSet(key: String, defaultValue: Set<String>?): Preference<Set<String>?> {
        return AndroidPreference(key, defaultValue, preferences, StringSetAdapter)
    }

    /**
     * Remove all values from the preferences
     */
    override fun clear() {
        preferences.edit().clear().apply()
    }

    companion object {
        private const val DEFAULT_LONG: Long = 0L
        private const val DEFAULT_INTEGER: Int = 0
        private const val DEFAULT_FLOAT: Float = 0f
        private const val DEFAULT_STRING: String = ""
        private const val DEFAULT_BOOLEAN: Boolean = false
    }
}
