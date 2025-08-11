package com.vovka.egov66client.ui.homework.day
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.vovka.egov66client.databinding.ItemHomeworkBinding
import com.vovka.egov66client.domain.homework.GetHomeWorkUseCase
import com.vovka.egov66client.domain.homework.SetHomeWorkDoneUseCase
import com.vovka.egov66client.domain.homework.entity.HomeWorkEntity
import com.vovka.egov66client.ui.homework.files.FilesHomeWorkModalBottomSheep
import com.vovka.egov66client.utils.visibleOrGone

class HomeWorkDayRecyclerAdapter(
    private val homeworks: List<HomeWorkEntity>,
    private val viewModel: HomeWorkDayViewModel,
    private val activity: FragmentActivity
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
//            subjectTextView.text = item.lessonName
            AccordionHomeWork.setHeadingString(item.lessonName)
            AccordionHomeWork.setMessageString(item.description)
            checkBox.isChecked = item.isDone
            AccordionHomeWork.isMessageStrikethrough = item.isDone
            AccordionHomeWork.isHeadingStrikethrough = item.isDone
            checkBox.setOnCheckedChangeListener{buttonView, isChecked ->
                viewModel.updateDoneHomeWork(isChecked,item.id)
                AccordionHomeWork.isMessageStrikethrough = isChecked
                AccordionHomeWork.isHeadingStrikethrough = isChecked
            }
            fileButton.visibleOrGone(item.homeWorkFiles.isNotEmpty())
            fileButton.setOnClickListener {
                val modalBottomSheet = FilesHomeWorkModalBottomSheep.newInstance(item.homeWorkFiles)
                modalBottomSheet.show(activity.supportFragmentManager, FilesHomeWorkModalBottomSheep.TAG)
            }
            form.text = if (item.isHomeworkElectronicForm) "Электронная форма" else "Бумажная форма"
        }
    }

    override fun getItemCount(): Int = homeworks.size
}