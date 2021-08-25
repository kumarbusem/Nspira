package com.busem.nspira.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.busem.data.models.Repository
import com.busem.nspira.common.setUrlSource
import com.busem.nspira.databinding.ItemRepositoryBinding

class RepositoriesAdapter(
    private val onClick: (data: Repository) -> Unit
) : PagingDataAdapter<Repository, RepositoriesAdapter.RepoViewHolder>(DIFF) {

    override fun onCreateViewHolder(viewHolder: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(viewHolder.context),
                viewHolder,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: RepoViewHolder, position: Int) {
        getItem(position)?.let { viewHolder.bind(it) }
    }

    inner class RepoViewHolder(private val item: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(repo: Repository) {

            item.apply {

                data = repo
                ivOwnerImage.setUrlSource(repo.ownerImage)

                clRoot.setOnClickListener {
                    onClick(repo)
                }
            }
        }
    }

    companion object {

        val DIFF = object : DiffUtil.ItemCallback<Repository>() {

            override fun areItemsTheSame(old: Repository, new: Repository) = old.id == new.id

            override fun areContentsTheSame(old: Repository, new: Repository) = old == new
        }
    }
}