package com.carterhudson.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.carterhudson.example.databinding.FragmentALayoutBinding
import com.carterhudson.example.injection.module.FragmentModule.Companion.FOUR
import com.carterhudson.example.injection.module.FragmentModule.Companion.THREE
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import javax.inject.Named


class FragmentA @Inject constructor(
  @Named(THREE) private val dependencyThree: String,
  @Named(FOUR) private val dependencyFour: Int
) : Fragment() {

  companion object {
    const val A_STRING_KEY = "key"
    fun makeArgs(aString: String) = bundleOf(A_STRING_KEY to aString)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = FragmentALayoutBinding.inflate(inflater, container, false).root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Toast
      .makeText(
        requireContext(),
        "Dependencies: $dependencyThree $dependencyFour",
        Toast.LENGTH_SHORT
      )
      .show()

    Snackbar
      .make(
        view,
        requireArguments().getString(A_STRING_KEY)!!,
        Snackbar.LENGTH_SHORT
      )
      .show()
  }
}