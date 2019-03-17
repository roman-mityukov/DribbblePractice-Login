package io.rm.dribble.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.rm.dribble.login.login.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        val loginFragment = LoginFragment()
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, loginFragment, LoginFragment.TAG)
            .commit()
    }
}