package com.vovka.egov66client.ui.schedule.day.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vovka.egov66client.databinding.ItemLessonBinding
import com.vovka.egov66client.domain.schedule.entity.LessonEntity

class LessonAdapter : ListAdapter<LessonEntity, LessonAdapter.LessonViewHolder>(LessonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ItemLessonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LessonViewHolder(
        private val binding: ItemLessonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(lesson: LessonEntity) {
            binding.numberTextView.text = (adapterPosition+1).toString() + "."
            binding.timeTextView.text = lesson.time
            binding.lessonTextView.text = lesson.lesson
            binding.teacherTextView.text= lesson.room
        }
    }

    private class LessonDiffCallback : DiffUtil.ItemCallback<LessonEntity>() {
        override fun areItemsTheSame(oldItem: LessonEntity, newItem: LessonEntity): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: LessonEntity, newItem: LessonEntity): Boolean {
            return oldItem == newItem
        }
    }
} 