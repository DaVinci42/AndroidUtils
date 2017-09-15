package com.davinci42.androidutils

import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.Window

/**
 * Created by DaVinci42 on 2017/9/15.
 */

/**
 * Drawable
 */
fun AnimationDrawable.gradientMotion(duration: Int, drawables: List<GradientDrawable>, isOneShot: Boolean = false, enterDurationInMs: Int = duration, exitDurationInMs: Int = duration): AnimationDrawable {

    this.setEnterFadeDuration(enterDurationInMs)
    this.setExitFadeDuration(exitDurationInMs)
    this.isOneShot = isOneShot

    drawables.forEach {
        this.addFrame(it, duration)
    }
    return this
}


/**
 * View
 */
fun View.toBitmap(): Bitmap {
    this.isDrawingCacheEnabled = true
    val cache = this.drawingCache
    val bitmap = Bitmap.createBitmap(cache, 0, 0, cache.width, cache.height)
    this.isDrawingCacheEnabled = false
    return bitmap
}


/**
 * Window
 */
fun Window.screenShot(clipStatusBar: Boolean = true, clipNavigationBar: Boolean = true): Bitmap {
    val view = this.decorView.rootView

    view.isDrawingCacheEnabled = true
    val cache = view.drawingCache
    val bitmap = Bitmap.createBitmap(cache, 0, 0, cache.width, cache.height)
    view.isDrawingCacheEnabled = false

    val resources = this.context.resources
    val statusBarResId = resources.getIdentifier("status_bar_height", "dimen", "android")
    val statusBarHeight = if (statusBarResId > 0) resources.getDimensionPixelSize(statusBarResId) else 0

    val navigationBarResId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    val navigationBarHeight = if (navigationBarResId > 0) resources.getDimensionPixelSize(navigationBarResId) else 0

    val top = if (clipStatusBar) statusBarHeight else 0
    var height = bitmap.height - top
    height = if (clipNavigationBar) height - navigationBarHeight else height
    return Bitmap.createBitmap(bitmap, 0, top, bitmap.width, height)
}
