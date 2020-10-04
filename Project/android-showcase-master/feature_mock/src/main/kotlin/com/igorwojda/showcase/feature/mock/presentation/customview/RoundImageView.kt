package com.igorwojda.showcase.feature.mock.presentation.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView


@SuppressLint("AppCompatCustomView")
class RoundImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    ImageView(context, attrs, defStyle) {
    private var paint: Paint? = null 
    private var roundWidth = 0
    private var roundHeight = 0
    private var paint2: Paint? = null
    private fun init() {
        paint = Paint()
        paint!!.color = Color.WHITE
        paint!!.isAntiAlias = true
        paint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        paint2 = Paint()
        paint2!!.xfermode = null
    }

    fun setRound(round: Int) {
        var round1 = round
        if (round1 > width / 2 || round1 > height / 2) {
            round1 = if (width > height) {
                height / 2
            } else {
                width / 2
            }
        }
        roundHeight = round1
        roundWidth = round1
        invalidate()
    }

    override fun draw(canvas: Canvas) {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas2 = Canvas(bitmap)
        super.draw(canvas2)
        drawLiftUp(canvas2)
        drawLiftDown(canvas2)
        drawRightUp(canvas2)
        drawRightDown(canvas2)
        canvas.drawBitmap(bitmap, 0f, 0f, paint2)
        bitmap.recycle()
    }

    private fun drawLiftUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, roundHeight.toFloat())
        path.lineTo(0f, 0f)
        path.lineTo(roundWidth.toFloat(), 0f)
        path.arcTo(RectF(0F, 0F, (roundWidth * 2).toFloat(), (roundHeight * 2).toFloat()), -90f, -90f)
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawLiftDown(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, height - roundHeight.toFloat())
        path.lineTo(0f, height.toFloat())
        path.lineTo(roundWidth.toFloat(), height.toFloat())
        path.arcTo(
            RectF(
                0F, (height - roundHeight * 2).toFloat(), (roundWidth * 2).toFloat(),
                height.toFloat()
            ), 90f, 90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawRightDown(canvas: Canvas) {
        val path = Path()
        path.moveTo(width - roundWidth.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height - roundHeight.toFloat())
        path.arcTo(
            RectF(
                (width - roundWidth * 2).toFloat(),
                (height - roundHeight * 2).toFloat(), width.toFloat(), height.toFloat()
            ), -0f, 90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawRightUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(width.toFloat(), roundHeight.toFloat())
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width - roundWidth.toFloat(), 0f)
        path.arcTo(
            RectF(
                (width - roundWidth * 2).toFloat(), 0F, width.toFloat(),
                (0 + roundHeight * 2).toFloat()
            ), -90f, 90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    init {
        init()
    }
}
