package io.rm.dribble.login.login

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import io.rm.dribble.login.R
import io.rm.dribble.login.home.HomeFragment
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(), LoginPresenterOutput {

    companion object {
        val TAG: String = LoginFragment::class.java.simpleName
    }

    private val presenterInput = LoginPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()

        this.presenterInput.attachOutput(this)

        this.button.requestFocus()
        this.button.setOnClickListener {
            this.button.startAnimation()
            this.presenterInput.doLoginJob()
        }
    }

    override fun onPause() {
        super.onPause()
        this.presenterInput.detachOutput()
    }

    override fun onComplete() {
        if (this.requireActivity().isFinishing) {
            return
        }

        this.button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

        val viewPropertyAnimator =
            this.button.animate().y(800f).scaleX(30f).scaleY(30f).setInterpolator(DecelerateInterpolator(1f))
        viewPropertyAnimator.duration = 800
        viewPropertyAnimator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                Thread.sleep(300)
                val homeFragment = HomeFragment()

                this@LoginFragment.requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, homeFragment, HomeFragment.TAG)
                    .addToBackStack(null)
                    .commit()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })


    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }
}