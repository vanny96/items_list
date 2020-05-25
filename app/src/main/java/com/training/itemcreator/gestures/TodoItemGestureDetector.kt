package com.training.itemcreator.gestures

import android.view.GestureDetector
import android.view.MotionEvent

class TodoItemGestureDetector(
    private val clickListener: () -> Unit,
    private val swipeLeftListener: () -> Unit
) : GestureDetector.SimpleOnGestureListener() {
    private val MIN_SWIPE_DISTANCE = 200f

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        clickListener()
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val swipeDistance = e2?.x?.let { e1?.x?.minus(it) } ?: 0f

        if (swipeDistance > MIN_SWIPE_DISTANCE) { // Right to left
            swipeLeftListener()
            return true;
        }

        return false
    }
}