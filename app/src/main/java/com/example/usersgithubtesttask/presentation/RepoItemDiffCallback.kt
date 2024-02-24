package com.example.usersgithubtesttask.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.usersgithubtesttask.data.RepositoryData

class RepoItemDiffCallback: DiffUtil.ItemCallback<RepositoryData>() {

    override fun areItemsTheSame(p0: RepositoryData, p1: RepositoryData): Boolean {
        return  p0.id == p1.id
    }

    override fun areContentsTheSame(p0: RepositoryData, p1: RepositoryData): Boolean {
        return  p0 == p1
    }

}
