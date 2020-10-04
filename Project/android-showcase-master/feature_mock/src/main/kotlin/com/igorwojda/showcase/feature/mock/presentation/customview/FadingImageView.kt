package com.igorwojda.showcase.feature.mock.presentation.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView


@SuppressLint("AppCompatCustomView")
class FadingImageView : ImageView {
    private var mFadeRight = false
    private var mFadeLeft = false
    private var mFadeTop = false
    private var mFadeBottom = false
    private var paint: Paint? = null
    private var paint2: Paint? = null
    private var c: Context
 
    constructor(c: Context, attrs: AttributeSet?, defStyle: Int) : super(c, attrs, defStyle) {
        this.c = c
        init()
    }

    constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
        this.c = c
        init()
    }

    constructor(c: Context) : super(c) {
        this.c = c
        init()
    }

    private fun init() {
        // Enable horizontal fading
        this.isHorizontalFadingEdgeEnabled = true
        this.isVerticalFadingEdgeEnabled = true
        // Apply default fading length
        setEdgeLength(30)
        // Apply default side
        setFadeRight(true)
        setFadeLeft(true)
        setFadeBottom(true)
        setFadeTop(true)

        //code to round the fadded view
        paint = Paint()
        paint!!.color = Color.WHITE
        paint!!.isAntiAlias = true
        paint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        paint2 = Paint()
        paint2!!.xfermode = null
    }

    fun setFadeRight(fadeRight: Boolean) {
        mFadeRight = fadeRight
    }

    fun setFadeLeft(fadeLeft: Boolean) {
        mFadeLeft = fadeLeft
    }

    fun setFadeTop(fadeTop: Boolean) {
        mFadeTop = fadeTop
    }

    fun setFadeBottom(fadeBottom: Boolean) {
        mFadeBottom = fadeBottom
    }

    fun setEdgeLength(length: Int) {
        setFadingEdgeLength(getPixels(length))
    }

    override fun getTopFadingEdgeStrength(): Float {
        return if (mFadeTop) 1.0f else 0.0f
    }

    override fun getBottomFadingEdgeStrength(): Float {
        return if (mFadeBottom) 1.0f else 0.0f
    }

    override fun getLeftFadingEdgeStrength(): Float {
        return if (mFadeLeft) 1.0f else 0.0f
    }

    override fun getRightFadingEdgeStrength(): Float {
        return if (mFadeRight) 1.0f else 0.0f
    }

    override fun hasOverlappingRendering(): Boolean {
        return true
    }

    public override fun onSetAlpha(alpha: Int): Boolean {
        return false
    }

    private fun getPixels(dipValue: Int): Int {
        val r = c.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dipValue.toFloat(), r.displayMetrics
        ).toInt()
    }

    override fun draw(canvas: Canvas) {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas2 = Canvas(bitmap)
        super.draw(canvas2)
        canvas.drawBitmap(bitmap, 0f, 0f, paint2)
        bitmap.recycle()
    }
}
