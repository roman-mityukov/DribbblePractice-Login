package io.rm.dribble.login.login

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.rm.dribble.login.R
import io.rm.dribble.login.home.HomeFragment
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    companion object {
        val TAG: String = LoginFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }


    override fun onResume() {
        super.onResume()

        this.button.setOnClickListener {
            if (this.requireActivity().isFinishing) {
                return@setOnClickListener
            }

            val homeFragment = HomeFragment()

            val enterTransitionSet = TransitionSet()
            enterTransitionSet.addTransition(LogoTransition())
            enterTransitionSet.duration = 1000
            homeFragment.sharedElementEnterTransition = enterTransitionSet

            this.requireActivity().supportFragmentManager.beginTransaction()
                .addSharedElement(this.logo, this.getString(R.string.logo_transition_name))
                .replace(R.id.fragmentContainer, homeFragment, HomeFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }
}

class LogoTransition : TransitionSet() {
    init {
        addTransition(ChangeBounds())
    }
}