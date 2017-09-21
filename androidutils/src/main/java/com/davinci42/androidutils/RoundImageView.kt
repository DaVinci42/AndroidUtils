package com.davinci42.androidutils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView


/**
 * Created by DaVinci42 on 2017/9/21.
 */
class RoundImageView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr) {

    private var mBmpPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mBoardPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mMatrix: Matrix = Matrix()

    private var mBitmapShader: BitmapShader? = null

    private var mRadius: Float = 0f
    private var mBoardWidth: Float = 0f
    private var mBmpWidth: Float = 0f
    private var mBmpHeight: Float = 0f

    fun load(resId: Int): RoundImageView {
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)
        mBitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mBmpWidth = bitmap.width.toFloat()
        mBmpHeight = bitmap.height.toFloat()
        return this
    }

    fun withBorder(borderColor: Int, borderWidth: Float, radius: Float): RoundImageView {
        mBoardPaint.color = borderColor
        mBoardWidth = borderWidth
        mRadius = radius
        return this
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (mBitmapShader != null) {
            mBmpPaint.shader = mBitmapShader
            val scale = Math.max(measuredWidth / mBmpWidth, measuredHeight / mBmpHeight)
            mMatrix.setScale(scale, scale)
            mBitmapShader?.setLocalMatrix(mMatrix)
        }
        val centerX = (measuredWidth / 2).toFloat()
        val centerY = (measuredHeight / 2).toFloat()
        if (mRadius == 0f) {
            mRadius = Math.min(centerX, centerY)
        }
        mBoardPaint.strokeWidth = mBoardWidth
        canvas?.drawCircle(centerX, centerY, mRadius, mBoardPaint)
        canvas?.drawCircle(centerX, centerY, mRadius - mBoardWidth, mBmpPaint)
    }
}