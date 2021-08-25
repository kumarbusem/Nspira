package com.busem.nspira.features.login

import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.busem.nspira.R
import com.busem.nspira.common.BaseAbstractFragment
import com.busem.nspira.common.ViewModelFactory
import com.busem.nspira.common.toast
import com.busem.nspira.databinding.FragmentLoginBinding

class LoginFragment : BaseAbstractFragment<LoginViewModel, FragmentLoginBinding>(
    FragmentLoginBinding::inflate,
) {

    override fun setViewModel(): LoginViewModel =
        ViewModelProvider(this@LoginFragment, ViewModelFactory {
            LoginViewModel()
        }).get(LoginViewModel::class.java)


    override fun setupViews(): FragmentLoginBinding.() -> Unit = {
        fun setupFields() {
            etUserName.doAfterTextChanged { etUserName.error = null }
            etPassword.doAfterTextChanged { etPassword.error = null }
        }


        fun setupGitIn() {

            fun userNameError() {
                etUserName.error = getString(R.string.enter_proper_username)
            }

            fun passwordError() {
                etPassword.error = getString(R.string.enter_proper_password)
            }

            btnGitIn.setOnClickListener {
                val username = etUserName.text.toString().trim().takeIf { it.isNotBlank() } ?: run {
                    userNameError()
                    return@setOnClickListener
                }

                val password = etPassword.text.toString().trim().takeIf { it.isNotBlank() } ?: run {
                    passwordError()
                    return@setOnClickListener
                }

                viewModel.loginUser(username, password)
            }
        }

        setupFields()
        setupGitIn()
    }

    override fun setupObservers(): LoginViewModel.() -> Unit = {

        userType.observe(viewLifecycleOwner) { userType ->
            when (userType) {
                UserType.NEW -> {
                    toast(getString(R.string.welcome), true)
                    navigateToHomeScreen()
                }
                UserType.EXISTING -> {
                    toast(getString(R.string.welcome_back), true)
                    navigateToHomeScreen()
                }
                else -> {
                    toast(getString(R.string.failed_to_auth), true)
                }
            }
        }

    }

    private fun navigateToHomeScreen() {
        navigateById(R.id.action_loginFragment_to_homeFragment)
    }

}
