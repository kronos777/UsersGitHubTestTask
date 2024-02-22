package com.example.usersgithubtesttask.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.usersgithubtesttask.R
import com.example.usersgithubtesttask.data.RepositoryData
import com.example.usersgithubtesttask.databinding.RepositoryItemBinding

class RepositoryListAdapter: ListAdapter<RepositoryData, RepoItemViewHolder>(RepoItemDiffCallback()) {
    var onAutoItemClickListener: ((RepositoryData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val layout = R.layout.repository_item
        val binding = DataBindingUtil.inflate<RepositoryItemBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )

        return RepoItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: RepoItemViewHolder, position: Int) {
        val repoItem = getItem(position)
        val binding = viewHolder.binding
        binding.root.setOnClickListener {
            onAutoItemClickListener?.invoke(repoItem)
        }
        Log.d("dataInAdapter", repoItem.toString())

       /* Picasso.get()
            .load(userItem.avatar_url)
            .placeholder(R.drawable.baseline_account_circle_24)
            .error(R.drawable.baseline_airline_seat_legroom_normal_24)
            .into(binding.image)
*/



        binding.repoItem = repoItem
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }
}