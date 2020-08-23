package com.carterhudson

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

inline fun <reified TypeT> Any.cast(): TypeT = this as TypeT

inline fun <reified TypeT> nameOf(): String = TypeT::class.java.name

inline fun <reified FragmentT : Fragment> FragmentFactory.instantiate(args: Bundle): FragmentT =
  instantiate(ClassLoader.getSystemClassLoader(), nameOf<FragmentT>())
    .cast<FragmentT>()
    .apply { arguments = args }

inline fun <reified FragmentT : Fragment> FragmentFactory.instantiate(vararg args: Pair<String, Any?>): FragmentT =
  instantiate(bundleOf(*args))

inline fun <reified FragmentT : Fragment> FragmentFactory.instantiate(args: List<Pair<String, Any?>>): FragmentT =
  args
    .toTypedArray()
    .let { instantiate(*it) }

inline fun <reified FragmentT : Fragment> FragmentFactory.instantiate(args: Map<String, Any?>): FragmentT =
  args
    .toList()
    .let(::instantiate)
