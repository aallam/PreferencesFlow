package com.aallam.preferencesflow

import android.content.SharedPreferences
import android.os.Build
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aallam.preferencesflow.extension.MainCoroutineRule
import com.aallam.preferencesflow.extension.runBlocking
import com.aallam.preferencesflow.extension.test
import com.aallam.preferencesflow.internal.Point
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.M])
public class PreferencesFlowTest {

    @get:Rule
    private val coroutineRule = MainCoroutineRule()

    private lateinit var preferences: SharedPreferences
    private lateinit var preferencesFlow: PreferencesFlow

    @Before
    public fun setUp() {
        this.preferences = getDefaultSharedPreferences(ApplicationProvider.getApplicationContext())
        this.preferencesFlow = PreferencesFlow(preferences)
    }

    @Test
    public fun testBoolean(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.boolean("Boolean")
        preferencesFlow.asFlow().test().assertValues(false)
        preferencesFlow.asFlowCollector().emit(true)
        preferencesFlow.asFlow().test().assertValues(true)
    }

    @Test
    public fun testBooleanDefault(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.boolean("Boolean", true)
        preferencesFlow.asFlow().test().assertValues(true)
    }

    @Test
    public fun testFloat(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.float("Float")
        preferencesFlow.asFlow().test().assertValues(0f)
        preferencesFlow.asFlowCollector().emit(42f)
        preferencesFlow.asFlow().test().assertValues(42f)
    }

    @Test
    public fun testFloatDefault(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.float("Float", 42f)
        preferencesFlow.asFlow().test().assertValues(42f)
    }

    @Test
    public fun testInt(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.int("Int")
        preferencesFlow.asFlow().test().assertValues(0)
        preferencesFlow.asFlowCollector().emit(42)
        preferencesFlow.asFlow().test().assertValues(42)
    }

    @Test
    public fun testIntDefault(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.int("Int", 42)
        preferencesFlow.asFlow().test().assertValues(42)
    }

    @Test
    public fun testLong(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.long("Long")
        preferencesFlow.asFlow().test().assertValues(0L)
        preferencesFlow.asFlowCollector().emit(42L)
        preferencesFlow.asFlow().test().assertValues(42L)
    }

    @Test
    public fun testLongDefault(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.long("Long", 42L)
        preferencesFlow.asFlow().test().assertValues(42L)
    }

    @Test
    public fun testSerializable(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.serializable("serializable", Point.serializer())
        preferencesFlow.asFlow().test().assertValues(null)
        val point = Point(42, 42)
        preferencesFlow.asFlowCollector().emit(point)
        preferencesFlow.asFlow().test().assertValues(point)
    }

    @Test
    public fun testSerializableDefault(): Unit = coroutineRule.runBlocking {
        val center = Point(0, 0)
        val preferencesFlow = preferencesFlow.serializable("serializable", center, Point.serializer())
        preferencesFlow.asFlow().test().assertValues(center)
        val point = Point(42, 42)
        preferencesFlow.asFlowCollector().emit(point)
        preferencesFlow.asFlow().test().assertValues(point)
    }

    @Test
    public fun testString(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.string("String")
        preferencesFlow.asFlow().test().assertValues("")
        preferencesFlow.asFlowCollector().emit("42")
        preferencesFlow.asFlow().test().assertValues("42")
    }

    @Test
    public fun testStringDefault(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.string("String", "default")
        preferencesFlow.asFlow().test().assertValues("default")
    }

    @Test
    public fun testStringSet(): Unit = coroutineRule.runBlocking {
        val preferencesFlow = preferencesFlow.stringSet("StringSet")
        preferencesFlow.asFlow().test().assertValues(emptySet())
        val set = setOf("42")
        preferencesFlow.asFlowCollector().emit(set)
        preferencesFlow.asFlow().test().assertValues(set)
    }

    @Test
    public fun testStringSetDefault(): Unit = coroutineRule.runBlocking {
        val set = setOf("42")
        val preferencesFlow = preferencesFlow.stringSet("StringSet", set)
        preferencesFlow.asFlow().test().assertValues(set)
    }

    @After
    public fun after() {
        preferences.edit().clear().commit()
    }
}
