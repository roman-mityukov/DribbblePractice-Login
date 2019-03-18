package io.rm.dribble.login

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import androidx.core.content.ContextCompat

class CircularProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) {

    enum class State {
        PROGRESS, IDLE
    }

    init {
        this.background = ContextCompat.getDrawable(this.context, R.drawable.shape_default)
    }
}