package com.clearscore.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.clearscore.test.databinding.FragmentCreditScoreDetailsBinding
import com.clearscore.test.ui.ScoreViewModel
import com.clearscore.test.ui.UIState

class CreditScoreDetailsFragment : Fragment() {

    private var _binding: FragmentCreditScoreDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ScoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreditScoreDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.textviewSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        // handle system back press closes app
    }

    private fun observeState() {

        val observer = Observer<UIState> { state ->
            Log.e("----->", "Frag 2 $state")
            when (state) {
                is UIState.Success -> {
                    //setup views here
                }
                else -> {
                    // Since VM is shared and this screen is navigated on Success state we should not have any other state here.
                }
            }
        }

        viewModel.state.observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}