package com.clearscore.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.clearscore.test.databinding.FragmentCreditScoreBinding
import com.clearscore.test.ui.CreditScoreIntent
import com.clearscore.test.ui.ScoreViewModel
import com.clearscore.test.ui.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditScoreFragment : Fragment() {

    private lateinit var binding: FragmentCreditScoreBinding

    private val viewModel: ScoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreditScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.textCreditScoreLine2.setOnClickListener {
            findNavController().navigate(R.id.action_CreditScoreFragment_to_CreditScoreDetailsFragment)
        }
        binding.retryButton.setOnClickListener {
            viewModel.onAction(CreditScoreIntent.RefreshCreditScore)
        }
    }

    private fun observeState() {

        val observer = Observer<UIState> { state ->
            //reset state
            resetViewsVisibility()
            when (state) {
                UIState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is UIState.Success -> {
                    binding.textCreditScoreLine1.isVisible = true
                    with(binding.textCreditScoreLine2) {
                        isVisible = true
                        text = state.score.toString()
                    }
                    with(binding.textCreditScoreLine3) {
                        isVisible = true
                        text = getString(R.string.text_credit_score_line_2, state.maxScore)
                    }
                    with(binding.creditScoreRing) {
                        isVisible = true
                        setProgress(state.creditRingProgress, true)
                    }
                }
                UIState.Error -> {
                    binding.retryButton.isVisible = true
                    binding.retryButton.text = getString(R.string.text_retry_button_server_error)
                }
                UIState.NoInternet -> {
                    binding.retryButton.isVisible = true
                    binding.retryButton.text = getString(R.string.text_retry_button_no_internet)
                }
            }
        }

        viewModel.state.observe(viewLifecycleOwner, observer)
    }

    private fun resetViewsVisibility() {
        binding.progressBar.isVisible = false
        binding.textCreditScoreLine1.isVisible = false
        binding.textCreditScoreLine2.isVisible = false
        binding.textCreditScoreLine3.isVisible = false
        binding.creditScoreRing.isVisible = false
        binding.retryButton.isVisible = false
    }
}