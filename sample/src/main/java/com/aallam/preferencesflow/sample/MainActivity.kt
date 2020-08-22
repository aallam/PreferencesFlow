package com.aallam.preferencesflow.sample

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aallam.preferencesflow.Preference
import com.aallam.preferencesflow.PreferencesFlow
import com.aallam.preferencesflow.sample.extension.checks
import com.aallam.preferencesflow.sample.extension.inputs
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create a PreferencesFlow instance.
        val preferencesFlow = PreferencesFlow(this)

        // Create Boolean Preference
        val checkedPref = preferencesFlow.boolean("checked")
        checkbox1.bindPreference(checkedPref)
        checkbox2.bindPreference(checkedPref)

        // Create String Preference
        val textPref = preferencesFlow.string("input")
        textInput1.bindPreference(textPref)
        textInput2.bindPreference(textPref)
    }

    private fun CheckBox.bindPreference(preference: Preference<Boolean>) {
        preference.asFlow()
            .onEach { isChecked = it }
            .launchIn(lifecycleScope)

        lifecycleScope.launch {
            checks()
                .onEach { preference.set(it) }
                .launchIn(lifecycleScope)
        }
    }

    private fun EditText.bindPreference(preference: Preference<String?>) {
        preference.asFlow()
            .filter { !isFocused }
            .onEach { setText(it) }
            .launchIn(lifecycleScope)

        lifecycleScope.launch {
            inputs()
                .onEach { preference.set(it) }
                .launchIn(lifecycleScope)
        }
    }
}
