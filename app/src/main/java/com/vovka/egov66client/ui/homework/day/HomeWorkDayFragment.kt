package com.vovka.egov66client.ui.homework.day

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentHomeWorkDayBinding
import com.vovka.egov66client.databinding.FragmentScheduleBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeWorkDayFragment : Fragment() {
    private var date: Date? = null
    private lateinit var dateTextView: TextView
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    private var _binding: FragmentHomeWorkDayBinding? = null
    private val binding: FragmentHomeWorkDayBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getSerializable(ARG_DATE) as Date
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_work_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateTextView = view.findViewById(R.id.dateTextView)
        date?.let { dateTextView.text = dateFormat.format(it) }

    }

    companion object {
        private const val ARG_DATE = "arg_date"

        fun newInstance(date: Date): HomeWorkDayFragment {
            return HomeWorkDayFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_DATE, date)
                }
            }
        }
    }
}