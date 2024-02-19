package com.example.usersgithubtesttask.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.usersgithubtesttask.R
import com.example.usersgithubtesttask.data.ListItemData
import com.example.usersgithubtesttask.databinding.UserItemBinding
import com.squareup.picasso.Picasso
import java.security.AccessController.getContext

class UsersListAdapter: ListAdapter<ListItemData, UserItemViewHolder>(UserItemDiffCallback()) {
    var onAutoItemClickListener: ((ListItemData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val layout = R.layout.user_item
        val binding = DataBindingUtil.inflate<UserItemBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )

        return UserItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: UserItemViewHolder, position: Int) {
        val userItem = getItem(position)
        val binding = viewHolder.binding
        binding.root.setOnClickListener {
            onAutoItemClickListener?.invoke(userItem)
        }
        Log.d("dataInAdapter", userItem.toString())

        Picasso.get()
            .load(userItem.avatar_url)
            .placeholder(R.drawable.baseline_account_circle_24)
            .error(R.drawable.baseline_airline_seat_legroom_normal_24)
            .into(binding.image)




        binding.userItem = userItem
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }
}