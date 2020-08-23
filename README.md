# fragment-factory-extensions
### Some useful extension functions for working with Android's Fragment Factory. 
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

## Usage:
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
Attached to your static factory methods on your Fragments? Like to keep your bundle keys internal to your Fragment? You can do something like:
```kotlin
class SomeFragment constructor() : Fragment() {

  companion object {
    const val A_STRING_KEY = "key"
    fun makeArgs(aString: String) = bundleOf(A_STRING_KEY to aString)
  }
  
  ...
}
```

## Why use a Fragment Factory?
Personally, I like having my dependencies advertised up-front. It's really up to you, though. I'm not here to sway you one way or the other. If you do use this, be aware: there are just some runtime depdendencies that aren't feasible to try to incorporate into a dependency graph, so you'll probably still need bundles and such. Having Fragments with non-default constructors just lets you simplify and move all of those `@Inject lateinit var junk: SomeJunk` declarations to the constructor.

## Example
There's an attached example application that uses this stuff in all the ways I could think of offhand.
