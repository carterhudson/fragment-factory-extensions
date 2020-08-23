package com.carterhudson.example.injection.module

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.carterhudson.nameOf
import com.carterhudson.example.FragmentA
import com.carterhudson.example.FragmentB
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Provider

@Module
class FragmentModule {
  companion object {
    const val ONE = "one"
    const val TWO = "two"
    const val THREE = "three"
    const val FOUR = "three"
  }

  @Provides
  @Named(ONE)
  fun provideDependencyOne() = ONE

  @Provides
  @Named(TWO)
  fun provideDependencyTwo() = 2

  @Provides
  @Named(THREE)
  fun provideDependencyThree() = THREE

  @Provides
  @Named(FOUR)
  fun provideDependencyFour() = 4

  @Provides
  fun provideFragmentFactory(
    provideFragA: Provider<FragmentA>,
    provideFragB: Provider<FragmentB>
  ) = object : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
      when (className) {
        nameOf<FragmentA>() -> provideFragA.get()
        nameOf<FragmentB>() -> provideFragB.get()
        else -> super.instantiate(classLoader, className)
      }
  }
}