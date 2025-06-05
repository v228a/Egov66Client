package com.vovka.egov66client.ui.homework.files

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.vovka.egov66client.databinding.FragmentHomeworkFilesBinding
import com.vovka.egov66client.databinding.ItemHomeworkBinding
import com.vovka.egov66client.databinding.ItemHomeworkFileBinding
import com.vovka.egov66client.domain.homework.entity.HomeWorkEntity
import com.vovka.egov66client.domain.homework.entity.HomeWorkFilesEntity
import com.vovka.egov66client.ui.homework.day.HomeWorkDayViewModel
import com.vovka.egov66client.utils.visibleOrGone

class FilesHomeWorkRecyclerAdapter(
    private val homeworks: List<HomeWorkFilesEntity>,
) : RecyclerView.Adapter<FilesHomeWorkRecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: ItemHomeworkFileBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeworkFileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = homeworks[position]
        with(holder.binding) {
            fileNameTextView.text = item.name
            sizeTextView.text = item.size.toString()
            typeTextView.text = item.type
        }
    }

    override fun getItemCount(): Int = homeworks.size
}