@file:Suppress("FunctionName")

package com.aallam.preferencesflow

import android.content.Context
import androidx.preference.Preference

/**
 * Create a [PreferencesFlow] instance.
 *
 * @param context Android context
 */
public fun PreferencesFlow(context: Context): PreferencesFlow = PreferencesFlow(Preference(context).sharedPreferences)
