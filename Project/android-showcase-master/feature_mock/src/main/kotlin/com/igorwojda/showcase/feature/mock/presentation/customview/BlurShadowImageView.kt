package com.igorwojda.showcase.feature.mock.presentation.customview

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.igorwojda.showcase.feature.mock.R 

class BlurShadowImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0 
) :
    RelativeLayout(context, attrs, defStyleAttr) {
    private var imageRound = dpToPx(0)
    private var shadowOffset = dpToPx(20)
    private var mInvalidat = false
    private var blurImageView: FadingImageView? = null
    private var blurredImage: Bitmap? = null
    private var imageresource = 0
    private fun initView(context: Context, attrs: AttributeSet?) {
        gravity = Gravity.CENTER
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        imageresource = -1
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.BlurShadowImageView)
            if (a.hasValue(R.styleable.BlurShadowImageView_v_imageSrc)) {
                imageresource = a.getResourceId(R.styleable.BlurShadowImageView_v_imageSrc, -1)
            }
            imageRound = a.getDimensionPixelSize(R.styleable.BlurShadowImageView_v_imageRound, imageRound)
            shadowOffset = a.getDimensionPixelSize(R.styleable.BlurShadowImageView_v_shadowOffset, shadowOffset)
            a.recycle()
        } else {
            val density = context.resources.displayMetrics.density
            imageRound = (imageRound * density).toInt()
            imageresource = -1
        }

        val roundImageView = RoundImageView(context)
        roundImageView.scaleType = ImageView.ScaleType.FIT_XY
        if (imageresource == -1) {
            roundImageView.setImageResource(R.color.transparent)
        } else {
            roundImageView.setImageResource(imageresource)
        }


        blurImageView = FadingImageView(context)
        blurImageView!!.scaleType = ImageView.ScaleType.CENTER_CROP
        if (imageresource == -1) {
            blurImageView!!.setImageResource(R.color.transparent)
        } else {
            val bitmap = BitmapFactory.decodeResource(resources, imageresource)
            blurredImage = Bitmap.createScaledBitmap(bitmap, 15, 15, true)
            blurImageView!!.setImageBitmap(blurredImage)
        }
        val lp =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        blurImageView!!.layoutParams = lp
        val lp2 = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        lp2.setMargins(dpToPx(7), 0, dpToPx(7), shadowOffset)
        roundImageView.layoutParams = lp2
        addView(blurImageView)
        addView(roundImageView)
        viewTreeObserver.addOnGlobalLayoutListener {
            (getChildAt(1) as RoundImageView).setRound(imageRound)
            mInvalidat = true
        }
    }

    fun setImageResource(resId: Int) {
        (getChildAt(1) as RoundImageView).setImageResource(resId)

        //Setting FadedBlurredImageView layer
        val bitmap = BitmapFactory.decodeResource(resources, resId)
        blurredImage = Bitmap.createScaledBitmap(bitmap, 8, 8, true)
        (getChildAt(0) as FadingImageView).setImageBitmap(blurredImage)
        invalidate()
        mInvalidat = true
    }

    fun setImageDrawable(drawable: Drawable) {
        //Setting RoundedImageView layer
        (getChildAt(1) as RoundImageView).setImageDrawable(drawable)

        //Setting FadedBlurredImageView layer
        val bitmap = (drawable as BitmapDrawable).bitmap
        blurredImage = Bitmap.createScaledBitmap(bitmap, 8, 8, true)
        (getChildAt(0) as FadingImageView).setImageBitmap(blurredImage)
        invalidate()
        mInvalidat = true
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        (getChildAt(1) as RoundImageView).setImageBitmap(bitmap)
        blurredImage = Bitmap.createScaledBitmap(bitmap!!, 8, 8, true)
        (getChildAt(0) as FadingImageView).setImageBitmap(blurredImage)
        invalidate()
        mInvalidat = true
    }

    fun setImageRadius(radius: Int) {
        var radius1 = radius
        if (radius1 > getChildAt(1).width / 2 || radius1 > getChildAt(1).height / 2) {
            radius1 = if (getChildAt(1).width > getChildAt(1).height) {
                getChildAt(1).height / 2
            } else {
                getChildAt(1).width / 2
            }
        }
        imageRound = radius1
        (getChildAt(1) as RoundImageView).setRound(imageRound)
        invalidate()
        mInvalidat = true
    }

    companion object {
        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }
    }

    init {
        initView(context, attrs)
    }
}
