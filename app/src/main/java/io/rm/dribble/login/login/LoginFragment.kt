package io.rm.dribble.login.login

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val homeFragment = HomeFragment()

        val enterTransitionSet = TransitionSet()
        enterTransitionSet.addTransition(LogoTransition())
        enterTransitionSet.duration = 300
        homeFragment.sharedElementEnterTransition = enterTransitionSet

        this.requireActivity().supportFragmentManager.beginTransaction()
            .addSharedElement(
                this.button,
                this.getString(R.string.button_transition_name)
            )
            .addSharedElement(
                this.logo,
                this.getString(R.string.logo_transition_name)
            )
            .replace(R.id.fragmentContainer, homeFragment, HomeFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }
}

class LogoTransition : TransitionSet() {
    init {
        addTransition(ChangeBounds())
    }
}