package io.rm.dribble.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
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

    private lateinit var progressDrawable: CircularProgressDrawable

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
                with(this@CircularProgressButton) {
                    progressDrawable = CircularProgressDrawable(
                        androidx.core.content.ContextCompat.getColor(
                            context,
                            android.R.color.white
                        ),
                        height / 2f,
                        4f
                    )

                    val progressDrawableBounds =
                        Rect(0, 0, width - 0, height - 0)
                    progressDrawable.bounds = progressDrawableBounds
                    progressDrawable.callback = this
                    progressDrawable.start()

                    setCompoundDrawablesWithIntrinsicBounds(
                        progressDrawable, null, null, null
                    )
                }
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