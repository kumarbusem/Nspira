package com.busem.nspira.features.splash

import androidx.lifecycle.ViewModelProvider
import com.busem.nspira.common.BaseAbstractFragment
import com.busem.nspira.common.ViewModelFactory
import com.busem.nspira.databinding.FragmentSplashBinding

class SplashFragment : BaseAbstractFragment<SplashViewModel, FragmentSplashBinding>(
    FragmentSplashBinding::inflate,
) {

    override fun setViewModel(): SplashViewModel =
        ViewModelProvider(this@SplashFragment, ViewModelFactory {
            SplashViewModel()
        }).get(SplashViewModel::class.java)

    override fun setupViews(): FragmentSplashBinding.() -> Unit = {

    }

    override fun setupObservers(): SplashViewModel.() -> Unit = {

    }


}
