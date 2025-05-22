package com.vovka.egov66client.ui.profile

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vovka.egov66client.R
import com.vovka.egov66client.databinding.FragmentProfileBinding
import com.vovka.egov66client.databinding.FragmentScheduleBinding
import com.vovka.egov66client.ui.schedule.ScheduleViewModel
import com.vovka.egov66client.ui.schedule.ScheduleViewModel.Companion.initialState
import com.vovka.egov66client.ui.schedule.ScheduleViewModel.State
import com.vovka.egov66client.ui.schedule.day.adapter.DayAdapter
import com.vovka.egov66client.utils.collectWhenStarted
import com.vovka.egov66client.utils.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.getValue


@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        initCallback()
        subscribe()
    }

    private fun initCallback() {
        binding.exitButton.setOnClickListener { viewModel.clickExit() }
    }

    private fun subscribe() {
        viewModel.state.collectWhenStarted(this) { state ->
            binding.exitButton.visibleOrGone(state !is ProfileViewModel.State.Loading)
            binding.progressBar.visibleOrGone(state is ProfileViewModel.State.Loading)
            when(state){
                is ProfileViewModel.State.Loading -> {

                }
                is ProfileViewModel.State.Show -> {
                    binding.name.text = state.lastName+" " + state.firstName +" " + state.surName
                    binding.orgname.text = state.orgName
                    binding.classname.text = state.className
                    if (!state.avatarId.isNullOrBlank()){
                        //TODO Load photo
                    }else{
                        binding.profileImage.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_person))
                    }
                }
            }
        }

        viewModel.action.collectWhenStarted(this) { action ->
            when(action) {
                ProfileViewModel.Action.OpenExit -> {
                    AlertDialog.Builder(requireActivity())
                        .setTitle("Выход")
                        .setMessage("Вы действительно хотите выйти из аккаунта")
                        .setPositiveButton("Да") { dialog, which ->
                            viewModel.logout()
                            findNavController().navigate(R.id.navigation_login,null)
                        }
                        .setNeutralButton("Нет") { dialog, which ->
                            // Действие при нажатии "Нет"
                            println("Нажата кнопка Нет")
                        }
                        .create()
                        .show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}