# Fragment Factory Extensions

Some useful extension functions for working with Android's Fragment
Factory. Helps me cut down on tedious repetition. This is mostly so I
don't have to re-write this for other projects in the future.

## Example

There's an attached example application that uses this stuff in all the
ways I could think of offhand.

It's super simple and mainly consists of the following two functions:

```kotlin
inline fun <reified FragmentT : Fragment> FragmentFactory.instantiate(arguments: Bundle): FragmentT =
  instantiate(ClassLoader.getSystemClassLoader(), nameOf<FragmentT>())
    .cast<FragmentT>()
    .apply { setArguments(arguments) }

inline fun <reified FragmentT : Fragment> FragmentFactory.instantiate(vararg args: Pair<String, Any?>): FragmentT =
  instantiate(ClassLoader.getSystemClassLoader(), nameOf<FragmentT>())
    .cast<FragmentT>()
    .apply { arguments = bundleOf(*args) }
```

## Usage

```kotlin
val someArgs = arrayOf(SOME_KEY to "some value")
fragmentFactory.instantiate<SomeFragment>(*someArgs)
```

_OR_

```kotlin
val someBundle = Bundle()
fragmentFactory.instantiate<SomeFragment>(someBundle)
```

## But what about my static factories?

Attached to your static factory methods on your Fragments? Like to keep
your bundle keys internal to your Fragment? You can do something like:

```kotlin
class SomeFragment constructor() : Fragment() {

  companion object {
    const val A_STRING_KEY = "key"
    fun makeArgs(aString: String) = bundleOf(A_STRING_KEY to aString)
  }
  
  ...
}
```


## License

```
   Copyright 2020 Carter Hudson

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

