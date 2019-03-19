package io.rm.dribble.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat

class CircularProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) {

    companion object {
        private const val WIDTH_ANIMATION_DURATION = 500L
    }

    init {
        this.background =
            ContextCompat.getDrawable(this.context, R.drawable.circular_progress_button_background)
    }

    private val progressDrawable = CircularProgressDrawable(
        ContextCompat.getColor(
            this@CircularProgressButton.context,
            android.R.color.white
        ),
        20f,
        8f
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //this.progressDrawable.draw(canvas)
    }

    fun startAnimation() {
        this.text = null

        val widthAnimator = ValueAnimator.ofInt(this.width, this.height)
        widthAnimator.addUpdateListener {
            val layoutParams: ViewGroup.LayoutParams = this.layoutParams
            layoutParams.width = it.animatedValue as Int
            this.layoutParams = layoutParams
        }

        widthAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                this@CircularProgressButton.setCompoundDrawablesWithIntrinsicBounds(
                    this@CircularProgressButton.progressDrawable, null, null, null
                )

                val padding =
                    (this@CircularProgressButton.width - this@CircularProgressButton.progressDrawable.intrinsicWidth) / 2

                this@CircularProgressButton.setPadding(padding, 0, 0, 0)

                val offset = 0
                val left = offset + padding
                val right = this@CircularProgressButton.width - offset - padding
                val bottom = this@CircularProgressButton.height - padding
                val top = padding

                this@CircularProgressButton.progressDrawable.setBounds(left, top, right, bottom)
                this@CircularProgressButton.progressDrawable.callback = this@CircularProgressButton
                this@CircularProgressButton.progressDrawable.start()
            }
        })

        val animatorSet = AnimatorSet()
        animatorSet.duration = WIDTH_ANIMATION_DURATION
        animatorSet.playTogether(widthAnimator)
        animatorSet.start()
    }

    fun stopAnimation() {

    }
}