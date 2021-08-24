package com.busem.nspira.features.repoDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.busem.data.models.Contributor
import com.busem.data.models.Repository
import com.busem.nspira.common.setUrlSource
import com.busem.nspira.databinding.ItemContributorBinding
import com.busem.nspira.databinding.ItemRepositoryBinding

class ContributorAdapter(
    private val onClick: (data: Contributor) -> Unit
) : ListAdapter<Contributor, ContributorAdapter.RepoViewHolder>(DIFF) {

    override fun onCreateViewHolder(viewHolder: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemContributorBinding.inflate(
                LayoutInflater.from(viewHolder.context),
                viewHolder,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: RepoViewHolder, position: Int) {
        getItem(position)?.let { viewHolder.bind(it) }
    }

    inner class RepoViewHolder(private val item: ItemContributorBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(contributor: Contributor) {

            item.apply {

                data = contributor
                ivOwnerImage.setUrlSource(contributor.profilePicUrl)

                clRoot.setOnClickListener {
                    onClick(contributor)
                }
            }
        }
    }

    companion object {

        val DIFF = object : DiffUtil.ItemCallback<Contributor>() {

            override fun areItemsTheSame(old: Contributor, new: Contributor) = old.id == new.id

            override fun areContentsTheSame(old: Contributor, new: Contributor) = old == new
        }
    }
}