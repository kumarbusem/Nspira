package com.busem.nspira.features.home

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.busem.data.models.Repository
import com.busem.nspira.R
import com.busem.nspira.common.BaseAbstractFragment
import com.busem.nspira.common.ViewModelFactory
import com.busem.nspira.common.toast
import com.busem.nspira.databinding.FragmentHomeBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseAbstractFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val repositoriesAdapter by lazy {
        RepositoriesAdapter(::selectedRepo)
    }

//
//    lateinit var mainListAdapter: MainListAdapter

    override fun setViewModel(): HomeViewModel =
        ViewModelProvider(this@HomeFragment, ViewModelFactory {
            HomeViewModel()
        }).get(HomeViewModel::class.java)

    @InternalCoroutinesApi
    override fun setupViews(): FragmentHomeBinding.() -> Unit = {

        setupList()
        setupView()

//        fun setupRepoList() {
//            rvRepositories.apply { adapter = repositoriesAdapter }
//        }

        fun setupSearch() {
            etSearchRepo.requestFocus()
            etSearchRepo.setOnEditorActionListener { _, actionId, _ ->

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    val searchText = etSearchRepo.text.toString().trim()
                        .takeIf { it.isNotBlank() } ?: run {
                        toast(getString(R.string.please_search_a_repository))
                        return@setOnEditorActionListener false
                    }

                    viewModel.searchResults(searchText)

                    val imm: InputMethodManager =
                        requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(etSearchRepo.windowToken, 0)

                    return@setOnEditorActionListener true
                }

                false
            }
        }

//        setupRepoList()
        setupSearch()

    }

    @InternalCoroutinesApi
    private fun setupView() {
//        lifecycleScope.launch {
//            viewModel.listData.collect {it ->
//                repositoriesAdapter.submitData(it)
//            }
//        }
//
        viewModel.obsPaging.observe(viewLifecycleOwner,{
            lifecycleScope.launch {
                repositoriesAdapter.submitData(it)
            }
        })
    }

    private fun setupList() {
        binding.rvRepositories.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = repositoriesAdapter
        }
    }

    override fun setupObservers(): HomeViewModel.() -> Unit = {

//        repos.observe(viewLifecycleOwner) { repoList ->
//            binding.tvNoResults.isVisible = repoList.isNullOrEmpty()
//            repositoriesAdapter.submitList(repoList)
//        }

    }

    private fun selectedRepo(repo: Repository) {
        viewModel.saveSelectedRepo(repo)
        navigateById(R.id.action_homeFragment_to_repoDetailsFragment)
    }

}
