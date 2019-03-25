package io.rm.dribble.login.home

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import io.rm.dribble.login.R
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName
    }

    private lateinit var buttonAnimatorSet: AnimatorSet
    private lateinit var viewsAnimatorSet: AnimatorSet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()

        this.button.scaleX = 35f
        this.button.scaleY = 35f
        this.button.y = 800f

        val listAnimatedElements = listOf<View>(this.logo, this.logo2, this.logo3, this.logo4)
        listAnimatedElements.forEach { it.alpha = 0f }

        this.buttonAnimatorSet = AnimatorSet()
        this.buttonAnimatorSet.playTogether(
            arrayListOf(
                ObjectAnimator.ofFloat(this.button, View.Y, 1600f),
                ObjectAnimator.ofFloat(this.button, View.SCALE_X, 1f),
                ObjectAnimator.ofFloat(this.button, View.SCALE_Y, 1f)
            ) as Collection<Animator>
        )
        this.buttonAnimatorSet.interpolator = DecelerateInterpolator()
        this.buttonAnimatorSet.duration = 500L
        this.buttonAnimatorSet.start()

        this.buttonAnimatorSet.addListener(onEnd = {
            this@HomeFragment.button.setImageResource(R.drawable.ic_add)

            this@HomeFragment.viewsAnimatorSet = AnimatorSet()
            this@HomeFragment.viewsAnimatorSet.playSequentially(listAnimatedElements.map {
                ObjectAnimator.ofFloat(it, View.ALPHA, 1f)
            })
            this@HomeFragment.viewsAnimatorSet.duration = 100L
            this@HomeFragment.viewsAnimatorSet.start()
        })
    }

    override fun onStop() {
        super.onStop()
        this.buttonAnimatorSet.cancel()

        if (this::viewsAnimatorSet.isInitialized) {
            this.viewsAnimatorSet.cancel()
        }
    }
}