package com.vovka.egov66client.ui.schedule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentScheduleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.div

@AndroidEntryPoint
class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private lateinit var pageAdapter: PageAdapter
    private lateinit var updateCounterUseCase: UpdateCounterUseCase
    private var counterState = CounterState()

    private var _binding: FragmentScheduleBinding? = null
    private val binding: FragmentScheduleBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScheduleBinding.bind(view)

        setupViewPager()
        setupCounters()

    }

    private fun setupViewPager() {
        pageAdapter = PageAdapter()
        binding.viewPager.adapter = pageAdapter

        // Устанавливаем начальную позицию с учетом INITIAL_PAGE_POSITION
        val startPosition = (pageAdapter.itemCount / 2) + Constants.INITIAL_PAGE_POSITION
        binding.viewPager.setCurrentItem(startPosition, false)

        // Слушатель изменений страниц
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val pageData = pageAdapter.getPageData(position)
                updateCounters(pageData)
            }
        })


    }

    private fun setupCounters() {
        updateCounterUseCase = UpdateCounterUseCase()
        updateUI()
    }

    private fun updateCounters(pageData: PageData) {
        val oldState = counterState
        counterState = updateCounterUseCase.updateCounters(counterState, pageData)

        // Логирование для отладки
        println("DEBUG: Старая позиция: ${oldState.currentPagePosition}, Новая позиция: ${pageData.position}")
        println("DEBUG: Страница: ${pageData.title}, ID: ${pageData.id}")
        println("DEBUG: Числовой счетчик: ${oldState.numericCounter} -> ${counterState.numericCounter}")

        updateUI()
    }

    private fun updateUI() {
        binding.tvFractionalCounter.text = updateCounterUseCase.getFractionalCounterText(counterState.currentPagePosition)
        binding.tvNumericCounter.text = updateCounterUseCase.getNumericCounterText(counterState.numericCounter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}