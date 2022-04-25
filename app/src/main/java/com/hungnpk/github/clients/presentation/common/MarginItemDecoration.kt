package com.hungnpk.github.clients.presentation.common

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Margin ItemDecoration for RecyclerView
 *
 * Ignore margin top of first item
 * @param head margin top of list
 * @param between margin between items
 * @param tail margin bottom of list
 * @param side margin left/right of items
 * @param color color value to fill in the gap between margins
 * @param reservedLayout boolean value to define the list reserved
 */
class MarginItemDecoration(
    private val head: Int = 0,
    private val between: Int = 0,
    private val tail: Int = 0,
    private val side: Int = 0,
    private val color: Int? = Color.TRANSPARENT,
    private val reservedLayout: Boolean = false
) : RecyclerView.ItemDecoration() {

    var paint: Paint? = null

    init {
        color?.let {
            paint = Paint().apply {
                color = it
                isAntiAlias = true
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        paint?.let {
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            val childCount = parent.childCount

            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                val bottom: Int = top + between
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), it)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            return
        }
        with(outRect) {
            if ((parent.layoutManager as LinearLayoutManager).orientation == LinearLayoutManager.VERTICAL) {
                top = if (parent.adapter != null && isHead(position, parent.adapter!!.itemCount)) head else between
                if (parent.adapter != null && isBottom(position, parent.adapter!!.itemCount)) {
                    bottom = tail
                }
                right = side
                left = side
            } else {
                left = if (parent.adapter != null && isHead(position, parent.adapter!!.itemCount)) head else between
                if (parent.adapter != null && isBottom(position, parent.adapter!!.itemCount)) {
                    right = tail
                }
                top = side
                bottom = side
            }
        }
    }

    private fun isHead(position: Int, size: Int) = (!reservedLayout && position == 0) || (reservedLayout && position == size - 1)
    private fun isBottom(position: Int, size: Int) = (reservedLayout && position == 0) || (!reservedLayout && position == size - 1)
}