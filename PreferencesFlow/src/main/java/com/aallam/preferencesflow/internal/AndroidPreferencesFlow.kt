package com.aallam.preferencesflow.internal

import android.content.SharedPreferences
import com.aallam.preferencesflow.Preference
import com.aallam.preferencesflow.PreferencesFlow
import com.aallam.preferencesflow.internal.adapter.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.KSerializer

/**
 * Implementation of [PreferencesFlow].
 *
 * @param preferences Android SharedPreferences
 */
internal class AndroidPreferencesFlow(
    private val preferences: SharedPreferences
) : PreferencesFlow {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val prefsKeyFlow: Flow<String?> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key -> offer(key) }
        preferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { preferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    override fun boolean(key: String): Preference<Boolean> {
        return boolean(key, DEFAULT_BOOLEAN)
    }

    override fun boolean(key: String, defaultValue: Boolean): Preference<Boolean> {
        return AndroidPreference(key, defaultValue, preferences, BooleanAdapter, prefsKeyFlow)
    }

    override fun float(key: String): Preference<Float> {
        return float(key, DEFAULT_FLOAT)
    }

    override fun float(key: String, defaultValue: Float): Preference<Float> {
        return AndroidPreference(key, defaultValue, preferences, FloatAdapter, prefsKeyFlow)
    }

    override fun int(key: String): Preference<Int> {
        return int(key, DEFAULT_INTEGER)
    }

    override fun int(key: String, defaultValue: Int): Preference<Int> {
        return AndroidPreference(key, defaultValue, preferences, IntegerAdapter, prefsKeyFlow)
    }

    override fun long(key: String): Preference<Long> {
        return long(key, DEFAULT_LONG)
    }

    override fun long(key: String, defaultValue: Long): Preference<Long> {
        return AndroidPreference(key, defaultValue, preferences, LongAdapter, prefsKeyFlow)
    }

    override fun <T> serializable(key: String, serializable: KSerializer<T>): Preference<T?> {
        return serializable(key, null, serializable)
    }

    override fun <T> serializable(key: String, defaultValue: T?, serializable: KSerializer<T>): Preference<T?> {
        return AndroidPreference(key, defaultValue, preferences, SerializableAdapter(serializable), prefsKeyFlow)
    }

    /**
     * Create a float preference for [key]. Default is empty string.
     */
    override fun string(key: String): Preference<String?> {
        return string(key, DEFAULT_STRING)
    }

    /**
     * Create a [String] preference for [key] with a default of [defaultValue].
     */
    override fun string(key: String, defaultValue: String?): Preference<String?> {
        return AndroidPreference(key, defaultValue, preferences, StringAdapter, prefsKeyFlow)
    }

    /**
     * Create a string set preference for {@code key}. Default is [EmptySet].
     */
    override fun stringSet(key: String): Preference<Set<String>?> {
        return stringSet(key, emptySet())
    }

    /**
     * Create a [String] set preference for [key] with a default of [defaultValue].
     */
    override fun stringSet(key: String, defaultValue: Set<String>?): Preference<Set<String>?> {
        return AndroidPreference(key, defaultValue, preferences, StringSetAdapter, prefsKeyFlow)
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
