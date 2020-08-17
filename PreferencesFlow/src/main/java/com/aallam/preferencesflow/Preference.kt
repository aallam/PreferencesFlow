package com.aallam.preferencesflow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector


/**
 * A preference of type [T].
 **/
public interface Preference<T> {

    /**
     * The key for which this preference will store and retrieve values.
     **/
    val key: String

    /**
     * The value used if none is stored.
     **/
    val defaultValue: T

    /**
     * Retrieve the current value for this preference.
     */
    fun get(): T

    /**
     * Change this preference's stored value to [value].
     */
    fun set(value: T)

    /**
     * Returns true if this preference has a stored value.
     **/
    fun isSet(): Boolean

    /**
     * Delete the stored value for this preference, if any.
     **/
    fun delete()

    /**
     * Observe changes to this preference.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun asFlow(): Flow<T>

    /**
     * An action which stores a new value for this preference.
     */
    fun asCollector(): FlowCollector<T>

    /**
     * Converts instances of [T] to be stored and retrieved as Strings SharedPreferences.
     */
    interface Converter<T> {

        /**
         * Deserialize to an instance of [T].
         */
        fun deserialize(serialized: String): T

        /**
         * Serialize the [value] to a String.
         **/
        fun serialize(value: T): String
    }
}
