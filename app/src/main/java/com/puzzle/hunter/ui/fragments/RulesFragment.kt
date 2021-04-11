package com.puzzle.hunter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puzzle.hunter.R
import com.puzzle.hunter.databinding.FragmentRulesBinding

class RulesFragment : Fragment(R.layout.fragment_rules) {
    private var _binding: FragmentRulesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

}