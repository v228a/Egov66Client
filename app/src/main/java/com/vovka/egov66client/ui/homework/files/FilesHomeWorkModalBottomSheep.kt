package com.vovka.egov66client.ui.homework.files

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentHomeWorkDayBinding
import com.vovka.egov66client.databinding.FragmentHomeworkFilesBinding
import com.vovka.egov66client.domain.homework.entity.HomeWorkFilesEntity
import com.vovka.egov66client.ui.homework.day.HomeWorkDayFragment
import java.time.LocalDateTime

class FilesHomeWorkModalBottomSheep : BottomSheetDialogFragment(R.layout.fragment_homework_files) {

    private var files: List<HomeWorkFilesEntity>? = null

    private var _binding: FragmentHomeworkFilesBinding? = null
    private val binding: FragmentHomeworkFilesBinding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeworkFilesBinding.bind(view)
        initCallback()
//        subscribe()
    }
    private fun initCallback(){
        binding.homeworkFilesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.homeworkFilesRecyclerView.adapter = FilesHomeWorkRecyclerAdapter(files!!)
    }

    companion object {
        const val TAG = "FilesHomeWorkModalBottomSheep"

        fun newInstance(files: List<HomeWorkFilesEntity>): FilesHomeWorkModalBottomSheep  {
            val fragment = FilesHomeWorkModalBottomSheep()
            fragment.files = files
            return fragment
        }
    }
}