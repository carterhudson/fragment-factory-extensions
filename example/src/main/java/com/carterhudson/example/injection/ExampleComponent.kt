package com.carterhudson.example.injection

import com.carterhudson.example.MainActivity
import com.carterhudson.example.injection.module.FragmentModule
import dagger.Component


@Component(modules = [FragmentModule::class])
interface ExampleComponent {
  fun inject(activity: MainActivity)
}