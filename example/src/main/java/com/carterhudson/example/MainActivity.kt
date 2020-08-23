package com.carterhudson.example

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.carterhudson.instantiate
import com.carterhudson.nameOf
import com.carterhudson.example.databinding.ActivityMainBinding
import com.carterhudson.example.injection.DaggerExampleComponent
import com.carterhudson.example.injection.module.FragmentModule
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var fragmentFactory: FragmentFactory

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    DaggerExampleComponent
      .builder()
      .fragmentModule(FragmentModule())
      .build()
      .inject(this)

    supportFragmentManager.fragmentFactory = fragmentFactory

    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)

    setContentView(binding.root)

    binding.apply {
      fragAButton.setOnClickListener {
        loadFragmentInto<FragmentA>(
          binding.fragmentContainer.id,
          FragmentA.makeArgs("look a string")
        )
      }
    }

    binding.apply {
      fragBButton.setOnClickListener {
        loadFragmentInto<FragmentB>(binding.fragmentContainer.id, FragmentB.ARG_KEY to 1010)
      }
    }
  }

  private inline fun <reified FragmentT : Fragment> loadFragmentInto(
    @IdRes containerId: Int,
    vararg args: Pair<String, Any?>
  ) {
    replaceFragment(containerId, fragmentFactory.instantiate<FragmentT>(*args))
  }

  private inline fun <reified FragmentT : Fragment> loadFragmentInto(
    @IdRes containerId: Int,
    bundle: Bundle
  ) {
    replaceFragment(containerId, fragmentFactory.instantiate<FragmentT>(bundle))
  }

  private inline fun <reified FragmentT : Fragment> replaceFragment(
    containerId: Int,
    frag: FragmentT
  ) {
    supportFragmentManager
      .beginTransaction()
      .replace(containerId, frag)
      .addToBackStack(nameOf<FragmentT>())
      .commit()
  }
}
