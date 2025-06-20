package com.vovka.egov66client.ui.homework.day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vovka.egov66client.databinding.ItemHomeworkBinding
import com.vovka.egov66client.domain.homework.entity.HomeWorkEntity

class HomeWorkDayRecyclerAdapter(
    private val homeworks: List<HomeWorkEntity>
) : RecyclerView.Adapter<HomeWorkDayRecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: ItemHomeworkBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeworkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = homeworks[position]
        with(holder.binding) {
            subjectTextView.text = item.lessonName
            taskTextView.text = item.description
        }
    }

    override fun getItemCount(): Int = homeworks.size
}