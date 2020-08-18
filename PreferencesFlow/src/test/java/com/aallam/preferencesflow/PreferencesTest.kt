package com.aallam.preferencesflow

import android.content.SharedPreferences
import android.os.Build
import androidx.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aallam.preferencesflow.extension.MainCoroutineRule
import com.aallam.preferencesflow.extension.runBlocking
import com.aallam.preferencesflow.extension.test
import com.aallam.preferencesflow.internal.Point
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.M])
public class PreferencesTest {

    @get:Rule
    private val coroutineRule = MainCoroutineRule()

    private lateinit var preferences: SharedPreferences
    private lateinit var preferencesFlow: PreferencesFlow

    @Before
    public fun setUp() {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext())
        this.preferencesFlow = PreferencesFlow(preferences)
    }

    @Test
    public fun testKey() {
        val key = "KEY"
        assertEquals(key, preferencesFlow.string(key).key)
    }

    @Test
    public fun testDefault() {
        assertEquals(preferencesFlow.boolean("boolean").defaultValue, false)
        assertEquals(preferencesFlow.float("float").defaultValue, 0f)
        assertEquals(preferencesFlow.int("int").defaultValue, 0)
        assertEquals(preferencesFlow.long("long").defaultValue, 0L)
        assertEquals(preferencesFlow.string("string").defaultValue, "")
        assertEquals(preferencesFlow.stringSet("stringSet").defaultValue, emptySet())
        assertEquals(preferencesFlow.serializable("serializable", Point.serializer()).defaultValue, null)
    }

    @Test
    public fun testDefaultWithValue() {
        assertEquals(preferencesFlow.boolean("boolean", true).defaultValue, true)
        assertEquals(preferencesFlow.float("float", 42f).defaultValue, 42f)
        assertEquals(preferencesFlow.int("int", 42).defaultValue, 42)
        assertEquals(preferencesFlow.long("long", 42L).defaultValue, 42L)
        assertEquals(preferencesFlow.string("string", "default").defaultValue, "default")
        assertEquals(preferencesFlow.stringSet("stringSet", setOf("default")).defaultValue, setOf("default"))
        assertEquals(
            preferencesFlow.serializable("serializable", Point(0, 0), Point.serializer()).defaultValue,
            Point(0, 0)
        )
    }

    @Test
    public fun testGetWithValue() {
        preferences.edit().putBoolean("boolean", false).commit()
        assertEquals(preferencesFlow.boolean("boolean").get(), false)

        preferences.edit().putFloat("float", 42f).commit()
        assertEquals(preferencesFlow.float("float").get(), 42f)

        preferences.edit().putInt("int", 42).commit()
        assertEquals(preferencesFlow.int("int").get(), 42)

        preferences.edit().putLong("long", 42L).commit()
        assertEquals(preferencesFlow.long("long").get(), 42L)

        preferences.edit().putString("string", "value").commit()
        assertEquals(preferencesFlow.string("string").get(), "value")

        preferences.edit().putStringSet("stringSet", setOf("default")).commit()
        assertEquals(preferencesFlow.stringSet("stringSet").get(), setOf("default"))

        val point = Point(0, 0)
        preferences.edit().putString("serializable", Json.encodeToString(Point.serializer(), point)).commit()
        assertEquals(preferencesFlow.serializable("serializable", Point.serializer()).get(), point)
    }

    @Test
    public fun testSet() {
        preferencesFlow.boolean("boolean").set(false)
        assertEquals(preferencesFlow.boolean("boolean", true).get(), false)

        preferencesFlow.float("float").set(1f)
        assertEquals(preferencesFlow.float("float", 0f).get(), 1f)

        preferencesFlow.int("integer").set(1)
        assertEquals(preferencesFlow.int("integer", 0).get(), 1)

        preferencesFlow.long("long").set(1L)
        assertEquals(preferencesFlow.long("long", 0L).get(), 1L)

        preferencesFlow.string("string").set("value")
        assertEquals(preferencesFlow.string("string", null).get(), "value")

        preferencesFlow.stringSet("stringSet").set(setOf("value"))
        assertEquals(preferencesFlow.stringSet("stringSet", null).get(), setOf("value"))

        preferencesFlow.serializable("serializable", Point(2, 3), Point.serializer()).set(Point(1, 2))
        assertEquals(preferencesFlow.serializable("serializable", null, Point.serializer()).get(), Point(1, 2))
    }

    @Test
    public fun isSet() {
        val preference: Preference<String?> = preferencesFlow.string("string")

        assertEquals(preferences.contains("string"), false)
        assertEquals(preference.isSet(), false)

        preferences.edit().putString("string", "2,3").commit()
        assertEquals(preference.isSet(), true)

        preferences.edit().remove("string").commit()
        assertEquals(preference.isSet(), false)
    }

    @Test
    public fun delete() {
        val preference: Preference<String?> = preferencesFlow.string("string")
        preferences.edit().putBoolean("string", true).commit()
        assertEquals(preferences.contains("string"), true)
        preference.delete()
        assertEquals(preferences.contains("string"), false)
    }

    @Test
    public fun testAsFlow(): Unit = coroutineRule.runBlocking {
        val preference = preferencesFlow.string("string", "default")

        preference.asFlow()
            .test()
            .assertValues("default")

        val value = "current"
        preferences.edit().putString("string", value).commit()
        preference.asFlow()
            .test()
            .assertValues(value)

        preferences.edit().clear().commit()
        preference.asFlow().test().assertValues("default")
    }

    @Test
    public fun testAsFlowCollector(): Unit = coroutineRule.runBlocking {
        val preference = preferencesFlow.string("string", "default")

        val value = "current"
        preference.asFlowCollector().emit(value)
        preference.asFlow()
            .test()
            .assertValues(value)

        val value2 = "current2"
        preference.asFlowCollector().emit(value2)
        preference.asFlow()
            .test()
            .assertValues(value2)
    }

    @After
    public fun after() {
        preferences.edit().clear().commit()
    }
}