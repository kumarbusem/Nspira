package com.busem.nspira.features.webView

import androidx.lifecycle.ViewModelProvider
import com.busem.nspira.R
import com.busem.nspira.common.BaseAbstractFragment
import com.busem.nspira.common.ViewModelFactory
import com.busem.nspira.common.toast
import com.busem.nspira.databinding.FragmentSplashBinding
import com.busem.nspira.databinding.FragmentWebViewBinding

class WebViewFragment : BaseAbstractFragment<WebViewViewModel, FragmentWebViewBinding>(
    FragmentWebViewBinding::inflate,
) {

    override fun setViewModel(): WebViewViewModel =
        ViewModelProvider(this@WebViewFragment, ViewModelFactory {
            WebViewViewModel()
        }).get(WebViewViewModel::class.java)

    override fun setupViews(): FragmentWebViewBinding.() -> Unit = {

        fun setupButtons(){
            ivBack.setOnClickListener { navigateBack() }
        }

        setupButtons()
    }

    override fun setupObservers(): WebViewViewModel.() -> Unit = {

        obsURL.observe(viewLifecycleOwner,{

            binding.webView.loadUrl(it)

        })


    }
}
