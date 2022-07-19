package com.clearscore.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.clearscore.test.databinding.FragmentCreditScoreDetailsBinding
import com.clearscore.test.ui.ScoreViewModel
import com.clearscore.test.ui.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditScoreDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCreditScoreDetailsBinding

    private val viewModel: ScoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreditScoreDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
    }

    private fun observeState() {

        val observer = Observer<UIState> { state ->
            when (state) {
                is UIState.Success -> {
                    binding.accountStatus.text =
                        getString(R.string.text_account_status, state.accountIDVStatus)
                    binding.dashboardStatus.text =
                        getString(R.string.text_dashboard_status, state.dashboardStatus)
                }
                else -> {
                    // Since VM is shared and this screen is navigated on Success state we should not have any other state here.
                }
            }
        }

        viewModel.state.observe(viewLifecycleOwner, observer)
    }

}