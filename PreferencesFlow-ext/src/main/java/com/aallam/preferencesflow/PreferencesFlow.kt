@file:Suppress("FunctionName")

package com.aallam.preferencesflow

import android.content.Context
import androidx.preference.PreferenceManager.getDefaultSharedPreferences

/**
 * Create a [PreferencesFlow] instance.
 *
 * @param context Android context
 */
public fun PreferencesFlow(context: Context): PreferencesFlow = PreferencesFlow(getDefaultSharedPreferences(context))
