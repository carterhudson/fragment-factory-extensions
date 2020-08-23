package com.carterhudson.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.carterhudson.example.databinding.FragmentBLayoutBinding
import com.carterhudson.example.injection.module.FragmentModule.Companion.ONE
import com.carterhudson.example.injection.module.FragmentModule.Companion.TWO
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import javax.inject.Named


class FragmentB @Inject constructor(
  @Named(ONE) private val dependencyOne: String,
  @Named(TWO) private val dependencyTwo: Int
) : Fragment() {

  companion object {
    const val ARG_KEY = "key"
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = FragmentBLayoutBinding.inflate(inflater, container, false).root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Toast
      .makeText(requireContext(), "Dependencies: $dependencyOne $dependencyTwo", Toast.LENGTH_SHORT)
      .show()

    Snackbar
      .make(
        view,
        "${requireArguments().getInt(ARG_KEY)}",
        Snackbar.LENGTH_SHORT
      )
      .show()
  }
}