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
    public val key: String

    /**
     * The value used if none is stored.
     **/
    public val defaultValue: T

    /**
     * Retrieve the current value for this preference.
     */
    public fun get(): T?

    /**
     * Change this preference's stored value to [value].
     */
    public fun set(value: T)

    /**
     * Returns true if this preference has a stored value.
     **/
    public fun isSet(): Boolean

    /**
     * Delete the stored value for this preference, if any.
     **/
    public fun delete()

    /**
     * Observe changes to this preference.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    public fun asFlow(): Flow<T>

    /**
     * An action which stores a new value for this preference.
     */
    public fun asCollector(): FlowCollector<T>
}
