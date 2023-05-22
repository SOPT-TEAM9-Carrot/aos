package com.sopt.carrot.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.carrot.databinding.ItemHomeListBinding

class JobAdapter() : ListAdapter<Card, JobAdapter.JobAdapterViewHolder>(diffUtil) {

    class JobAdapterViewHolder(private val binding: ItemHomeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Card) {
            with(binding) {
                ivItemHomeListImg.setImageDrawable(root.context.getDrawable(data.image))
                tvItemHomeListTitle.text = data.title
                tvItemHomeListTitle.text = data.salary
            }

        }

    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobAdapterViewHolder {
        val binding = ItemHomeListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JobAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobAdapterViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}