package io.rm.dribble.login.home

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import io.rm.dribble.login.R
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        return view
    }

    override fun onResume() {
        super.onResume()

        val oldY = this.button.y

        this.button.scaleX = 30f
        this.button.scaleY = 30f
        this.button.y = 800f

        val startAlpha = 0f
        val endAlpha = 1f

        this@HomeFragment.logo.alpha = startAlpha
        this@HomeFragment.logo2.alpha = startAlpha
        this@HomeFragment.logo3.alpha = startAlpha
        this@HomeFragment.logo4.alpha = startAlpha

        val viewPropertyAnimator =
            this.button.animate().y(1600f).scaleX(1f).scaleY(1f)
                .setInterpolator(DecelerateInterpolator(1f))
        viewPropertyAnimator.duration = 500
        viewPropertyAnimator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                this@HomeFragment.button.setImageResource(R.drawable.ic_add)

                val alphaDuration = 100L

                val a1 = ObjectAnimator.ofFloat(this@HomeFragment.logo, View.ALPHA, endAlpha)
                a1.duration = alphaDuration
                val a2 = ObjectAnimator.ofFloat(this@HomeFragment.logo2, View.ALPHA, endAlpha)
                a2.duration = alphaDuration
                val a3 = ObjectAnimator.ofFloat(this@HomeFragment.logo3, View.ALPHA, endAlpha)
                a3.duration = alphaDuration
                val a4 = ObjectAnimator.ofFloat(this@HomeFragment.logo4, View.ALPHA, endAlpha)
                a4.duration = alphaDuration

                val animatorSet = AnimatorSet()
                animatorSet.playSequentially(
                    a1, a2, a3, a4
                )
                animatorSet.start()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })
    }
}