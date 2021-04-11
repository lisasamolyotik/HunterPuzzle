package com.puzzle.hunter

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.puzzle.hunter.ui.fragments.LevelFragment
import com.puzzle.model.PuzzlePiece
import kotlin.math.absoluteValue


class TouchListener(val fragment: LevelFragment) : View.OnTouchListener {
    private var xDelta = 0f
    private var yDelta = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.rawX
        val y = motionEvent.rawY
        val tolerance: Double =
            kotlin.math.sqrt((view.width * view.width + view.height * view.height).toDouble()) / 10
        val piece = view as PuzzlePiece
        if (!piece.canMove) {
            return true
        }
        val lParams = view.getLayoutParams() as RelativeLayout.LayoutParams
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                xDelta = x - lParams.leftMargin
                yDelta = y - lParams.topMargin
                piece.bringToFront()
            }
            MotionEvent.ACTION_MOVE -> {
                lParams.leftMargin = (x - xDelta).toInt()
                lParams.topMargin = (y - yDelta).toInt()
                view.setLayoutParams(lParams)
            }
            MotionEvent.ACTION_UP -> {
                val xDiff: Int =
                    (piece.x - view.left + fragment.puzzleImageLeftMargin).absoluteValue
                val yDiff: Int =
                    (piece.y - view.top + fragment.puzzleImageTopMargin).absoluteValue
                if (xDiff <= tolerance && yDiff <= tolerance) {
                    lParams.leftMargin = piece.x + fragment.puzzleImageLeftMargin
                    lParams.topMargin = piece.y + fragment.puzzleImageTopMargin
                    piece.layoutParams = lParams
                    piece.canMove = false
                    sendViewToBack(piece)
                    checkGameOver()
                    Log.d("tag", "pieces count: ${countPiece()}")
                    Toast.makeText(fragment.context, "the correct place", Toast.LENGTH_SHORT).show()
                } else {
                    lParams.leftMargin = piece.originX
                    lParams.topMargin = piece.originY
                    piece.layoutParams = lParams
                    Toast.makeText(fragment.context, "not the correct place", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return true
    }

    private fun sendViewToBack(child: View) {
        val parent = child.parent as ViewGroup
        parent.removeView(child)
        parent.addView(child, 0)
    }

    fun checkGameOver() {
        if (countPiece() == 0) {
            Log.d("tag", "you win")
            fragment.openYouWinAlert()
        }
    }

    private fun countPiece(): Int {
        var count = 0
        for (piece in fragment.pieces!!) {
            if (piece.canMove) count++
        }
        return count
    }
}
