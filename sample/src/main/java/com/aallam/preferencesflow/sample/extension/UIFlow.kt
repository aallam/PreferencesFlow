package com.aallam.preferencesflow.sample.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

@OptIn(ExperimentalCoroutinesApi::class)
fun CheckBox.checks(): Flow<Boolean> {
    return callbackFlow {
        val listener = CompoundButton.OnCheckedChangeListener { _, checked ->
            safeOffer(checked)
        }
        setOnCheckedChangeListener(listener)
        awaitClose { setOnClickListener(null) }
    }.conflate()
}

@OptIn(ExperimentalCoroutinesApi::class)
fun EditText.inputs(): Flow<String> {
    return callbackFlow<String> {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing.
            }

            override fun afterTextChanged(editable: Editable?) {
                safeOffer(editable.toString())
            }
        }
        addTextChangedListener(textWatcher)
        awaitClose { addTextChangedListener(null) }
    }.conflate()
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <E> SendChannel<E>.safeOffer(value: E): Boolean = !isClosedForSend && try {
    offer(value)
} catch (e: CancellationException) {
    false
}
