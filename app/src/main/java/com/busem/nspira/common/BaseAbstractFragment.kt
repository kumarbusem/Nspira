package com.busem.nspira.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.busem.nspira.BR
import com.busem.nspira.R


abstract class BaseAbstractFragment<V : BaseViewModel, B : ViewDataBinding>(
    private val inflate: Inflate<B>
) : BaseFragment() {

    protected val viewModel: V by lazy { setViewModel() }

    @Suppress("MemberVisibilityCanBePrivate")
    protected var nullableBinding: B? = null

    @Suppress("MemberVisibilityCanBePrivate")
    protected val binding: B
        get() = nullableBinding
            ?: throw IllegalStateException("View either has not been initialized or has been destroyed")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        nullableBinding = inflate.invoke(layoutInflater)
        setupViews().invoke(binding)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.mViewModel, viewModel)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers().invoke(viewModel)
        setupDefaultObservers()
    }

    private fun setupDefaultObservers() {
        viewModel.apply {
            obsToastMessage.observe(viewLifecycleOwner, { showToast(it) })
            isUserLogout.observe(viewLifecycleOwner, { navigateById(R.id.loginFragment) })
        }
    }

    abstract fun setViewModel(): V
    open fun setupViews(): B.() -> Unit = {}
    open fun setupObservers(): V.() -> Unit = {}

    override fun onDestroyView() {
        super.onDestroyView()
        nullableBinding = null
    }
}
