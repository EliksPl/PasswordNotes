package com.eliks.passwordnotes.ui.notes.adapter

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.eliks.passwordnotes.R
import kotlin.math.abs

class SwipeToDeleteCallback(
    private val context : Context,
    private val onSwipeToDeleteCallback: (Int) -> (Unit)
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_24)!!
    private val backgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    val softRed = Color.parseColor("#EF9A9A")  // м’яко-червоний
    val softGray = Color.parseColor("#E0E0E0") // світло-сірий
    val strongRed = Color.parseColor("#f44336") //червоний
    private val evaluator = ArgbEvaluator()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        onSwipeToDeleteCallback(position)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val cardView = itemView.findViewById<CardView>(R.id.item_note_card_view)
        val threshold = getSwipeThreshold(viewHolder) * cardView.width

        val progress = abs(dX) / threshold

        val animatedColor = calculateChildBackgroundColor(progress)
        backgroundPaint.color = animatedColor

        setChildBackgroundConstraints(c, cardView, itemView)
        manageIconAnimationAndDrawing(c, progress, itemView)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.4f
    }

    private fun calculateChildBackgroundColor(progress: Float): Int {
        val evaluator = ArgbEvaluator()
        var innerProgress = progress
        if (progress < 1f) {
            return evaluator.evaluate(innerProgress, softGray, softRed) as Int
        }
        if (progress in 1f..1.1f){
            innerProgress = (progress - 1f) / 0.1f
            return evaluator.evaluate(innerProgress, softRed, strongRed) as Int
        }
        if (progress > 1.1f){
            return strongRed
        }

        return softGray
    }

    private fun manageIconAnimationAndDrawing(c: Canvas, progress: Float, itemView: View) {
        val itemHeight = itemView.height
        val scale = getIconScale(progress)
        val alpha = calculateIconAlpha(progress)

        val iconWidth = (deleteIcon.intrinsicWidth * scale).toInt()
        val iconHeight = (deleteIcon.intrinsicHeight * scale).toInt()
        val iconMargin = (itemHeight - iconHeight) / 2

        val iconLeft = itemView.right - iconMargin - iconWidth
        val iconRight = itemView.right - iconMargin
        val iconTop = itemView.top + iconMargin
        val iconBottom = iconTop + iconHeight

        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        deleteIcon.alpha = alpha
        deleteIcon.draw(c)

    }

    private fun getIconScale(progress: Float): Float {
        return 2f
    }

    private fun calculateIconAlpha(progress: Float): Int {
        when {
            progress < 1f -> return (progress * 255).toInt()
            else -> return 255
        }
    }

    private fun setChildBackgroundConstraints(
        c: Canvas,
        cardView: CardView,
        itemView: View
    ) {
        val left = cardView.left.toFloat()
        val right = cardView.right.toFloat()
        val top = cardView.top.toFloat()
        val bottom = cardView.bottom.toFloat()
        val marginTop = itemView.paddingTop.toFloat()
        val marginBottom = itemView.paddingBottom.toFloat()
        val marginLeft = itemView.paddingLeft.toFloat()
        val marginRight = itemView.paddingRight.toFloat()
        val round = cardView.radius

        c.drawRoundRect(left+marginLeft, top+marginTop, right-marginRight, bottom-marginBottom,round,round, backgroundPaint)
    }

}