package com.puzzle.hunter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puzzle.hunter.R
import com.puzzle.hunter.databinding.FragmentSettingsBinding
import com.puzzle.hunter.ui.MainActivity

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        if (MainActivity.isSoundON) {
            binding.soundButton.setImageResource(R.drawable.sound_on_button)
        } else {
            binding.soundButton.setImageResource(R.drawable.sound_off_button)
        }

        if (MainActivity.isVibroON) {
            binding.vibroButton.setImageResource(R.drawable.vibro_on_button)
        } else {
            binding.vibroButton.setImageResource(R.drawable.vibro_off_button)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.soundButton.setOnClickListener {
            MainActivity.isSoundON = !MainActivity.isSoundON
            if (MainActivity.isSoundON) {
                binding.soundButton.setImageResource(R.drawable.sound_on_button)
            } else {
                binding.soundButton.setImageResource(R.drawable.sound_off_button)
            }
        }

        binding.vibroButton.setOnClickListener {
            MainActivity.isVibroON = !MainActivity.isVibroON
            if (MainActivity.isVibroON) {
                binding.vibroButton.setImageResource(R.drawable.vibro_on_button)
            } else {
                binding.vibroButton.setImageResource(R.drawable.vibro_off_button)
            }
        }
    }


}