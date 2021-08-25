package com.busem.nspira

import androidx.lifecycle.ViewModelProvider
import com.busem.nspira.common.BaseActivity
import com.busem.nspira.common.ViewModelFactory
import com.busem.nspira.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun setViewModel(): MainViewModel =
        ViewModelProvider(this@MainActivity, ViewModelFactory {
            MainViewModel()
        }).get(MainViewModel::class.java)

    override fun setupViews(): ActivityMainBinding.() -> Unit = {

    }

    override fun setupObservers(): MainViewModel.() -> Unit = {

    }
}