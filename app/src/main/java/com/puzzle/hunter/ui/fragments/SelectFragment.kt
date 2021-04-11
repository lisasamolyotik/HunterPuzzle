package com.puzzle.hunter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.puzzle.hunter.R
import com.puzzle.hunter.databinding.FragmentSelectBinding

class SelectFragment : Fragment(R.layout.fragment_select) {
    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.img1.setOnClickListener { openLevel(1) }
        binding.img2.setOnClickListener { openLevel(2) }
        binding.img3.setOnClickListener { openLevel(3) }
        binding.img4.setOnClickListener { openLevel(4) }
        binding.img5.setOnClickListener { openLevel(5) }
        binding.img6.setOnClickListener { openLevel(6) }
    }

    fun openLevel(number: Int) {
        val fragment: LevelFragment = LevelFragment()
        val bundle = Bundle()
        bundle.putInt(LEVEL_KEY, number)
        fragment.arguments = bundle
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
    }

    companion object {
        const val LEVEL_KEY = "level"
    }
}