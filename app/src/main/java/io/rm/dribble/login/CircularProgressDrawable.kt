package io.rm.dribble.login

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator

class CircularProgressDrawable(
    color: Int,
    private val radius: Float,
    private val borderWidth: Float
) :
    Drawable(), Animatable {

    companion object {
        private val ANGLE_INTERPOLATOR = LinearInterpolator()
        private const val ANGLE_ANIMATOR_DURATION = 1000L
    }

    var angle: Float = 0f
        set(value) {
            field = value
            this@CircularProgressDrawable.invalidateSelf()
        }
    private val paint = Paint()
    private val myBounds = RectF()
    private var myBoundsOffset: Float = 0f
    private val angleAnimator = ObjectAnimator.ofFloat(this, "angle", 360f)

    init {
        this.paint.isAntiAlias = true
        this.paint.style = Paint.Style.STROKE
        this.paint.strokeWidth = borderWidth
        this.paint.color = color

        this.angleAnimator.interpolator = ANGLE_INTERPOLATOR
        this.angleAnimator.duration = ANGLE_ANIMATOR_DURATION
        this.angleAnimator.repeatMode = ValueAnimator.RESTART
        this.angleAnimator.repeatCount = ValueAnimator.INFINITE
    }

    override fun draw(canvas: Canvas) {
        canvas.drawArc(this.myBounds, this.angle, 45f, false, this.paint)
    }

    override fun getIntrinsicHeight(): Int {
        return this.bounds.width()
    }

    override fun getIntrinsicWidth(): Int {
        return this.bounds.height()
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        this.myBoundsOffset = this.borderWidth * 4
        this.myBounds.left = bounds.left.toFloat() + this.myBoundsOffset
        this.myBounds.right = bounds.right.toFloat() - this.myBoundsOffset
        this.myBounds.top = bounds.top.toFloat() + this.myBoundsOffset
        this.myBounds.bottom = bounds.bottom.toFloat() - this.myBoundsOffset

    }

    override fun setAlpha(alpha: Int) {
        this.paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter) {
        this.paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    override fun isRunning(): Boolean {
        return true
    }

    override fun start() {
        this.angleAnimator.start()
    }

    override fun stop() {

    }
}