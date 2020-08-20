# Preferences Flow

[![Maven Central](https://img.shields.io/maven-central/v/com.aallam.preferencesflow/preferencesflow?color=blue)](https://search.maven.org/artifact/com.aallam.preferencesflow/preferencesflow)


Android [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences) powered by [Kotlin flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) and [Kotlin serialization](https://github.com/Kotlin/kotlinx.serialization).

## Download

The latest release is available on [Maven Central](https://search.maven.org/artifact/com.aallam.preferencesflow/preferencesflow).

```groovy
implementation 'com.aallam.preferencesflow:preferencesflow:0.1.0'
```

## Usage
Create a `PreferencesFlow` by passing a `SharedPreferences` instance:
```kotlin
val preferencesFlow = PreferencesFlow(sharedPreferences)
```

Or, an Android Context for default `SharedPreferences`:
```kotlin
val preferencesFlow = PreferencesFlow(context)
```

### Preference
Create a `Preference` object:
```kotlin
val preference = preferencesFlow.boolean("key", defaultValue = true)
```

Observe changes to the individual preference:
```kotlin
preference.asFlow()
            .onEach { println(it) }
            .launchIn(scope)
```

#### Serializable objects
It's possible use [Serialization](https://kotlinlang.org/docs/reference/serialization.html) to store, operate and 
observe objects in the `SharedPreference`.

First, create an `Serializable` class:
```Kotlin
@Serializable
internal data class Point(val x: Int, val y: Int)
```
Then create a `Preference` like usual:
```kotlin
preferencesFlow.serializable("point", Point.serializer())
```
_Annotating a Kotlin class with `@Serializable` instructs the serialization plugin to automatically generate 
implementation of `KSerializer` that can be accessed with `T.serializer()` extension function on the class companion._

## Credit
This library inspired by [rx-preferences](https://github.com/f2prateek/rx-preferences)

## License
PreferenceFlow is distributed under the terms of the Apache License (Version 2.0). See [LICENSE](LICENSE) for details.
