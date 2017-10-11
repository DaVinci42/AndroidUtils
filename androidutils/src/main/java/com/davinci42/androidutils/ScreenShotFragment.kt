package com.davinci42.androidutils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import io.reactivex.Observer
import java.lang.Error
import java.util.*
import kotlin.concurrent.schedule


/**
 * Created by DaVinci42 on 2017/9/30.
 */
class ScreenShotFragment : Fragment(), ImageReader.OnImageAvailableListener {

    private val REQUEST_MEDIA_PROJECTION = 42

    lateinit private var mMediaProjectionManager: MediaProjectionManager
    lateinit private var mMediaProjection: MediaProjection
    lateinit private var mImageReader: ImageReader

    private var mScreenDensity = 0
    private var mDisplayHeight = 0
    private var mDisplayWidth = 0

    private var observer: Observer<Bitmap>? = null


    companion object {

        fun takeScreenShot(activity: FragmentActivity, observer: Observer<Bitmap>) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            val fragment = ScreenShotFragment()
            fragment.observer = observer
            transaction.add(fragment, ScreenShotFragment::class.java.simpleName)
            transaction.commit()

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getRealMetrics(metrics)

        mScreenDensity = metrics.densityDpi
        mDisplayWidth = metrics.widthPixels
        mDisplayHeight = metrics.heightPixels

        mImageReader = ImageReader.newInstance(mDisplayWidth, mDisplayHeight, PixelFormat.RGBA_8888, 1)
        mImageReader.setOnImageAvailableListener(this, null)

        mMediaProjectionManager = activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            REQUEST_MEDIA_PROJECTION -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {

                    Timer().schedule(500) {

                        // Ensure the permission request dialog has already dismissed after 500ms delay
                        mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data)

                        mMediaProjection.createVirtualDisplay("ScreenShot",
                                mDisplayWidth, mDisplayHeight, mScreenDensity,
                                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                                mImageReader.surface, null, null)
                    }

                } else {
                    observer?.onError(Error("Permission Not Granted."))
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }


    override fun onImageAvailable(reader: ImageReader?) {

        val image = mImageReader.acquireLatestImage()
        val width = image.width
        val height = image.height
        val plane = image.planes[0]
        val pixelStride = plane.pixelStride
        val rowStride = plane.rowStride
        val rowPadding = rowStride - pixelStride * width

        val bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888)
        bitmap.copyPixelsFromBuffer(plane.buffer)

        image.close()
        mMediaProjection.stop()

        observer?.onNext(bitmap)
    }
}