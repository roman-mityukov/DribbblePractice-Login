package io.rm.dribble.login.login

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface LoginPresenterOutput {
    fun onComplete()
    fun onError(e: Throwable)
}

interface LoginPresenterInput {
    fun doLoginJob()
    fun attachOutput(output: LoginPresenterOutput)
    fun detachOutput()
}

class LoginPresenter : LoginPresenterInput, CoroutineScope {
    override val coroutineContext: CoroutineContext =
        Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            launch {
                this@LoginPresenter.output?.onError(throwable)
            }
        }
    private var job: Job? = null
    private var output: LoginPresenterOutput? = null
    private var isBusy: Boolean = false

    override fun attachOutput(output: LoginPresenterOutput) {
        this.output = output
    }

    override fun detachOutput() {
        this.output = null
        this.job?.cancel()
    }

    override fun doLoginJob() {
        if (!this.isBusy) {
            this.isBusy = true
            this.job = launch {
                delay(2000)

                withContext(Dispatchers.Main) {
                    this@LoginPresenter.isBusy = false
                    this@LoginPresenter.output?.onComplete()
                }
            }
        }
    }
}