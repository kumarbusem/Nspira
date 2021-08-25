package com.busem.nspira.features.repoDetails

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.busem.data.models.Contributor
import com.busem.nspira.R
import com.busem.nspira.common.BaseAbstractFragment
import com.busem.nspira.common.ViewModelFactory
import com.busem.nspira.common.setUrlSource
import com.busem.nspira.databinding.FragmentRepoDetailsBinding

class RepoDetailsFragment : BaseAbstractFragment<RepoDetailsViewModel, FragmentRepoDetailsBinding>(
    FragmentRepoDetailsBinding::inflate,
) {

    private val contributorAdapter by lazy {
        ContributorAdapter(::contributorSelected)
    }

    override fun setViewModel(): RepoDetailsViewModel =
        ViewModelProvider(this@RepoDetailsFragment, ViewModelFactory {
            RepoDetailsViewModel()
        }).get(RepoDetailsViewModel::class.java)

    override fun setupViews(): FragmentRepoDetailsBinding.() -> Unit = {

        fun setupContributorList() {
            rvContributors.apply { adapter = contributorAdapter }
        }

        fun setupButtons() {
            ivBack.setOnClickListener { navigateBack() }
            cvLink.setOnClickListener { navigateToRepoWebView() }
        }

        setupContributorList()
        setupButtons()

    }

    override fun setupObservers(): RepoDetailsViewModel.() -> Unit = {

        selectedRepo.observe(viewLifecycleOwner, {
            binding.ivOwnerImage.setUrlSource(it.ownerImage)
            Log.e("REPO::", it.toString())
        })

        contributors.observe(viewLifecycleOwner, { list ->
            Log.e("CONTRI::", list.toString())
            binding.tvNoResults.isVisible = list.isNullOrEmpty()
            contributorAdapter.submitList(list)
        })

    }

    private fun navigateToRepoWebView() {
        viewModel.saveRepoURL()
        navigateById(R.id.action_repoDetailsFragment_to_webViewFragment)
    }

    private fun contributorSelected(contributor: Contributor) {
        viewModel.saveURL(contributor.link)
        navigateById(R.id.action_repoDetailsFragment_to_webViewFragment)
    }
}
