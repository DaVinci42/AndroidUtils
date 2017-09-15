package com.davinci42.androidutils.image

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

    fun addDrawable(orientation: GradientDrawable.Orientation, startColor: Int, endColor: Int): GradientMotionDrawable {
        val drawable = GradientDrawable(orientation, intArrayOf(startColor, endColor))
        this.addFrame(drawable, durationInMs)
        return this
    }
}