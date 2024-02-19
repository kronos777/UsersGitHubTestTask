package com.example.usersgithubtesttask.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.usersgithubtesttask.data.ListItemData

class UserItemDiffCallback: DiffUtil.ItemCallback<ListItemData>() {

    override fun areItemsTheSame(p0: ListItemData, p1: ListItemData): Boolean {
        return  p0.id == p1.id
    }

    override fun areContentsTheSame(p0: ListItemData, p1: ListItemData): Boolean {
        return  p0 == p1
    }

}
