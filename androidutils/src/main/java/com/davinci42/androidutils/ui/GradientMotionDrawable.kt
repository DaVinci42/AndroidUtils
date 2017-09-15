package com.davinci42.androidutils.ui

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.GradientDrawable

/**
 * Created by DaVinci42 on 2017/9/15.
 */
class GradientMotionDrawable(private val durationInMs: Int, isOneShot: Boolean = false) : AnimationDrawable() {

    init {
        this.setEnterFadeDuration(durationInMs)
        this.setExitFadeDuration(durationInMs)
        this.isOneShot = isOneShot
    }

    fun withDrawables(drawables: List<Triple<GradientDrawable.Orientation, Int, Int>>): GradientMotionDrawable {
        drawables.forEach {
            val drawable = GradientDrawable(it.first, intArrayOf(it.second, it.third))
            this.addFrame(drawable, durationInMs)
        }
        return this
    }
}