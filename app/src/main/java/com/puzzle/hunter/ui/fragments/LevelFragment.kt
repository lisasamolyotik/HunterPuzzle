package com.puzzle.hunter.ui.fragments

import android.graphics.*
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.puzzle.hunter.R
import com.puzzle.hunter.TouchListener
import com.puzzle.hunter.databinding.FragmentLevelBinding
import com.puzzle.hunter.util.DimensionConverter
import com.puzzle.model.PuzzlePiece
import kotlin.math.absoluteValue


class LevelFragment : Fragment(R.layout.fragment_level) {
    private var layoutChangeListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    private var _binding: FragmentLevelBinding? = null
    private val binding get() = _binding!!
    var timer: CountDownTimer? = null
    private var time = 180000L

    var puzzleImageLeftMargin = 0
    var puzzleImageTopMargin = 0

    var pieces: List<PuzzlePiece>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelBinding.inflate(inflater, container, false)
        when (requireArguments().getInt(SelectFragment.LEVEL_KEY)) {
            1 -> binding.puzzleImg.setImageResource(R.drawable.img1_full)
            2 -> binding.puzzleImg.setImageResource(R.drawable.img2_full)
            3 -> binding.puzzleImg.setImageResource(R.drawable.img3_full)
            4 -> binding.puzzleImg.setImageResource(R.drawable.img4_full)
            5 -> binding.puzzleImg.setImageResource(R.drawable.img5_full)
            6 -> binding.puzzleImg.setImageResource(R.drawable.img6_full)
            else -> {
            }
        }

        layoutChangeListener =
            ViewTreeObserver.OnGlobalLayoutListener {
                puzzleImageLeftMargin = binding.puzzleImg.left
                puzzleImageTopMargin = binding.puzzleImg.top
                pieces = splitImage()
                attachPieces(pieces!!)
                changePiecesOriginalLocation()
                binding.v11.viewTreeObserver.removeOnGlobalLayoutListener(layoutChangeListener)
                layoutChangeListener = null
            }

        binding.v11.viewTreeObserver.addOnGlobalLayoutListener(layoutChangeListener)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time = millisUntilFinished
            }

            override fun onFinish() {
                openTryAgainAlert()
            }
        }
        (timer as CountDownTimer).start()
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }

    private fun createPuzzlePiece(puzzleView: ImageView): PuzzlePiece {
        val left = puzzleView.left
        val top = puzzleView.top
        val width = puzzleView.width
        val height = puzzleView.height

        val mask = Bitmap.createBitmap(puzzleView.drawable.toBitmap())
        val maskBitmap = Bitmap.createScaledBitmap(mask, width, height, true)

        val original = Bitmap.createScaledBitmap(
            binding.puzzleImg.drawable.toBitmap(),
            DimensionConverter.pixelsToDp(binding.puzzleImg.width, requireContext()),
            DimensionConverter.pixelsToDp(binding.puzzleImg.height, requireContext()),
            true
        )

        val croppedBitmap = Bitmap.createBitmap(
            original,
            DimensionConverter.pixelsToDp(left, requireContext()),
            DimensionConverter.pixelsToDp(top, requireContext()),
            DimensionConverter.pixelsToDp(width, requireContext()),
            DimensionConverter.pixelsToDp(height, requireContext())
        )

        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(maskBitmap, 0f, 0f, null)
        canvas.drawBitmap(croppedBitmap, 0f, 0f, paint)
        paint.xfermode = null
        paint.style = Paint.Style.STROKE
        val piece = PuzzlePiece(left, top, true, requireActivity())
        piece.setImageBitmap(result)
        return piece
    }

    private fun splitImage(): List<PuzzlePiece> {
        val pieces = mutableListOf<PuzzlePiece>()
        for (i in 0 until binding.cellsContainer.childCount) {
            Log.d("tag", i.toString())
            pieces.add(createPuzzlePiece(binding.cellsContainer.getChildAt(i) as ImageView))
        }
        pieces.shuffle()
        return pieces
    }

    private fun attachPieces(pieces: List<PuzzlePiece>) {
        val listener = TouchListener(this)
        pieces.forEach {
            it.setOnTouchListener(listener)
            binding.relativeLayout.addView(it)
        }
    }

    private fun changePiecesOriginalLocation() {
        var left = binding.puzzleImg.left
        var top = binding.puzzleImg.bottom + 50
        val bottom = binding.imageView4.bottom
        val hh = binding.puzzleImg.width / 10
        val vh = (top - bottom).absoluteValue / 6

        var i = 0
        Log.d("tag", "child count ${binding.relativeLayout.childCount}")
        for (j in 0 until binding.relativeLayout.childCount) {
            val piece = binding.relativeLayout.getChildAt(j) as PuzzlePiece
            val params = piece.layoutParams as RelativeLayout.LayoutParams
            params.leftMargin = left
            params.topMargin = top
            piece.originX = left
            piece.originY = top
            i++
            if (i < 8) {
                left += hh
            } else {
                left -= hh * 7
                top += vh
                i = 0
            }
        }
    }

    fun openYouWinAlert() {
        binding.youWinImage.visibility = View.VISIBLE
    }

    fun openTryAgainAlert() {
        binding.tryAgainImage.visibility = View.VISIBLE
        for (piece in pieces!!) {
            piece.canMove = false
        }
    }
}
