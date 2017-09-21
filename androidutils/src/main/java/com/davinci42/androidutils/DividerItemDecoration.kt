package com.davinci42.androidutils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by DaVinci42 on 2017/9/21.
 */
class DividerItemDecoration(val color: Int, val height: Int, private val leftPadding: Int = 0, private val rightPadding: Int = 0) : RecyclerView.ItemDecoration() {

    private val mPaint = Paint()

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect?.bottom = height
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)

        if (c != null && parent != null) {
            mPaint.color = color

            for (i in 0 until parent.childCount - 1 - 1) {
                val child = parent.getChildAt(i)
                val left = (parent.paddingLeft + leftPadding).toFloat()
                val top = child.bottom.toFloat()
                val right = (parent.measuredWidth - parent.paddingRight - rightPadding).toFloat()
                val bottom = top + height
                c.drawRect(left, top, right, bottom, mPaint)
            }
        }
    }
}
