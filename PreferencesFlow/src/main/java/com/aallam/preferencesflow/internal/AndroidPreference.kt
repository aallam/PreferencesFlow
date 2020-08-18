package com.aallam.preferencesflow.internal

import android.content.SharedPreferences
import com.aallam.preferencesflow.Preference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Implementation of [Preference]
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal class AndroidPreference<T>(
    override val key: String,
    override val defaultValue: T,
    private val preferences: SharedPreferences,
    private val adapter: PreferenceAdapter<T>,
) : Preference<T> {

    // initial value is the current value or defaultValue
    private val flow: MutableStateFlow<T> = MutableStateFlow(get() ?: defaultValue)

    override fun get(): T? {
        return adapter.get(key, preferences, defaultValue);
    }

    override fun set(value: T) {
        val editor = preferences.edit()
        adapter.set(key, value, editor)
        editor.apply()
    }

    override fun isSet(): Boolean {
        return preferences.contains(key)
    }

    override fun delete() {
        preferences.edit().remove(key).apply();
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun asFlow(): Flow<T> {
        return flow
    }

    override fun asCollector(): FlowCollector<T> {
        return object : FlowCollector<T> {
            override suspend fun emit(value: T) {
                flow.value = value
            }
        }
    }
}