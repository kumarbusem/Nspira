package com.busem.nspira.features.home

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.busem.data.models.Repository
import com.busem.nspira.R
import com.busem.nspira.common.BaseAbstractFragment
import com.busem.nspira.common.ViewModelFactory
import com.busem.nspira.common.toast
import com.busem.nspira.databinding.FragmentHomeBinding
import com.busem.nspira.features.dialogs.DialogInfo
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class HomeFragment : BaseAbstractFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val repositoriesAdapter by lazy { RepositoriesAdapter(::selectedRepo) }

    override fun setViewModel(): HomeViewModel =
        ViewModelProvider(this@HomeFragment,
            ViewModelFactory { HomeViewModel() }
        ).get(HomeViewModel::class.java)

    @InternalCoroutinesApi
    override fun setupViews(): FragmentHomeBinding.() -> Unit = {

        fun setupList() {

            binding.rvRepositories.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = repositoriesAdapter.withLoadStateHeaderAndFooter(
                    header = GitLoadStateAdapter { repositoriesAdapter.retry() },
                    footer = GitLoadStateAdapter { repositoriesAdapter.retry() }
                )
            }
        }

        fun setupSearch() {

            tilSearch.requestFocus()
            tilSearch.setOnEditorActionListener { _, actionId, _ ->

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    val searchText = tilSearch.text.toString().trim()
                        .takeIf { it.isNotBlank() } ?: run {
                        toast(getString(R.string.please_search_a_repository))
                        return@setOnEditorActionListener false
                    }

                    viewModel.searchResults(searchText)

                    val imm: InputMethodManager =
                        requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(tilSearch.windowToken, 0)

                    return@setOnEditorActionListener true
                }

                false
            }
        }

        fun setupButtons() {
            mcvLogout.setOnClickListener { showConfirmationDialogueForLogout { viewModel.logoutUser() } }
            mcvProfilePic.setOnClickListener { showToast("Profile - Not implemented") }
        }

        setupSearch()
        setupList()
        setupButtons()
    }

    override fun setupObservers(): HomeViewModel.() -> Unit = {

        viewModel.obsPagingData.observe(viewLifecycleOwner, {
            lifecycleScope.launch { repositoriesAdapter.submitData(it) }
        })

    }

    private fun selectedRepo(repo: Repository) {
        viewModel.saveSelectedRepo(repo)
        navigateById(R.id.action_homeFragment_to_repoDetailsFragment)
    }

    private fun showConfirmationDialogueForLogout(onConfirmation: () -> Unit) {
        DialogInfo.Builder()
            .setMessage("Are you sure you want Logout")
            .onPrimaryAction(onConfirmation)
            .setSecondaryButtonVisibility(false)
            .dismissOnClick()
            .build()
            .show(
                this@HomeFragment.childFragmentManager,
                DialogInfo::class.java.simpleName
            )
    }
}
