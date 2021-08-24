package com.busem.nspira.features.splash

import androidx.lifecycle.ViewModelProvider
import com.busem.nspira.R
import com.busem.nspira.common.BaseAbstractFragment
import com.busem.nspira.common.ViewModelFactory
import com.busem.nspira.common.toast
import com.busem.nspira.databinding.FragmentSplashBinding

class SplashFragment : BaseAbstractFragment<SplashViewModel, FragmentSplashBinding>(
    FragmentSplashBinding::inflate,
) {

    override fun setViewModel(): SplashViewModel =
        ViewModelProvider(this@SplashFragment, ViewModelFactory {
            SplashViewModel()
        }).get(SplashViewModel::class.java)

    override fun setupViews(): FragmentSplashBinding.() -> Unit = {
        viewModel.checkAndNavigate()
    }

    override fun setupObservers(): SplashViewModel.() -> Unit = {
        navigateTo.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                NavDestination.LOGIN -> navigateToAccessScreen()
                NavDestination.HOME -> navigateToHomeScreen()
                else -> toast(getString(R.string.failed_to_navigate))
            }
        }
    }

    private fun navigateToAccessScreen() {
        navigateById(R.id.action_splashFragment_to_loginFragment)
    }

    private fun navigateToHomeScreen() {
        navigateById(R.id.action_splashFragment_to_homeFragment)
    }
}
