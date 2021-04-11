package com.puzzle.hunter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.puzzle.hunter.R
import com.puzzle.hunter.databinding.FragmentMainMenuBinding

class MainFragment : Fragment(R.layout.fragment_main_menu) {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.playButton.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SelectFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }

        binding.settingsButton.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SettingsFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }

        binding.infoButton.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<RulesFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }

        binding.menuButton.setOnClickListener {  }
    }

}