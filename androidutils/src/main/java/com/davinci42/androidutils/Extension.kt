package com.davinci42.androidutils

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View

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

fun View.screenShot(clipStatusBar: Boolean = true, clipNavigationBar: Boolean = true): Bitmap {

    this.isDrawingCacheEnabled = true
    val cache = this.drawingCache
    val bitmap = Bitmap.createBitmap(cache, 0, 0, cache.width, cache.height)
    this.isDrawingCacheEnabled = false

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


/**
 * Int
 */
fun Int.dp(): Int {
    return Math.round(this.toFloat().dp())
}

fun Int.px(): Int {
    return Math.round(this.toFloat().px())
}


/**
 * Float
 */
fun Float.dp(): Float {
    return this / Resources.getSystem().displayMetrics.density
}

fun Float.px(): Float {
    return this * Resources.getSystem().displayMetrics.density
}


/**
 * Activity
 */
fun Activity.logd(msg: String) {
    Log.d(this::class.java.simpleName, msg)
}

fun Activity.logi(msg: String) {
    Log.i(this::class.java.simpleName, msg)
}

fun Activity.logw(msg: String) {
    Log.w(this::class.java.simpleName, msg)
}

fun Activity.loge(msg: String, e: Error? = null) {
    if (e != null) {
        Log.e(this::class.java.simpleName, msg, e)
    } else {
        Log.e(this::class.java.simpleName, msg, e)
    }
}


/**
 * Fragment
 */
fun Fragment.logd(msg: String) {
    activity.logd(msg)
}

fun Fragment.logi(msg: String) {
    activity.logi(msg)
}

fun Fragment.logw(msg: String) {
    activity.logw(msg)
}

fun Fragment.loge(msg: String, e: Error? = null) {
    activity.loge(msg, e)
}


/**
 * Context
 */
fun Context.statusBarHeight(): Int {
    val statusBarResId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (statusBarResId > 0) resources.getDimensionPixelSize(statusBarResId) else 0
}

fun Context.navigationBarHeight(): Int {
    val navigationBarResId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (navigationBarResId > 0) resources.getDimensionPixelSize(navigationBarResId) else 0
}