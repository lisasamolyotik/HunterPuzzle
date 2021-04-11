package com.puzzle.model

import android.content.Context

class PuzzlePiece(
    var x: Int,
    var y: Int,
    var canMove: Boolean = true,
    mContext: Context,
    var originX: Int = 0,
    var originY: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(mContext) {
}