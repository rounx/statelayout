package com.rounx.statelayout

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.*
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.state_layout_info.view.*

/**
 * @author Perry Lance
 * @since 2020-10-15 Created
 */
class StateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var state: State = State.CONTENT
    private var stateMap = HashMap<State, View>()

    @LayoutRes
    private var loadingRes = -1

    @LayoutRes
    private var infoRes = -1

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.StateLayout)

        state = State.values()[a.getInteger(
            R.styleable.StateLayout_state_layout_state,
            State.CONTENT.ordinal
        )]
        loadingRes =
            a.getResourceId(
                R.styleable.StateLayout_state_layout_loading_layout,
                R.layout.state_layout_loading
            )
        infoRes =
            a.getResourceId(
                R.styleable.StateLayout_state_layout_info_layout,
                R.layout.state_layout_info
            )

        a.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1) {
            throw IllegalArgumentException("You must have only one content view.")
        }

        if (childCount == 1) {
            val contentView = getChildAt(0)
            stateMap[State.CONTENT] = contentView
        }

        if (loadingRes != -1) {
            setViewForState(State.LOADING, loadingRes)
        }

        if (infoRes != -1) {
            setViewForState(State.INFO, infoRes)
        }

        setState(state)
    }

    fun setState(state: State) {
        if (!stateMap.containsKey(state)) {
            throw IllegalStateException("Invalid state: $state")
        }
        for (key in stateMap.keys) {
            stateMap[key]!!.visibility = if (key == state) View.VISIBLE else View.GONE
        }
        this.state = state
    }

    fun infoTitle(title: String) {
        state_layout_info_title.text = title
    }

    fun infoMessage(message: String) {
        state_layout_info_message.text = message
    }

    fun infoImage(@DrawableRes imageRes: Int, @ColorRes colorRes: Int) {
        val iconColor = ContextCompat.getColor(context, colorRes)
        val wrapperColor = modifyAlpha(iconColor, 0.2F)

        state_layout_info_image.setImageResource(imageRes)
        state_layout_info_image.setColorFilter(iconColor)

        state_layout_info_wrapper.setBackgroundColor(wrapperColor)
    }

    fun infoImageVisible(isVisible: Boolean) {
        state_layout_info_view.isVisible = isVisible
    }

    fun infoButton(buttonText: String? = null, block: (() -> Unit)?) {
        state_layout_info_button.apply {
            buttonText?.let { text = it }
            setOnClickListener { block?.invoke() }
        }
    }

    fun infoButtonVisible(isVisible: Boolean) {
        state_layout_info_button.isVisible = isVisible
    }


    private fun setViewForState(state: State, @LayoutRes res: Int) {
        val view = LayoutInflater.from(context).inflate(res, this, false)
        setViewForState(state, view)
    }

    private fun setViewForState(state: State, view: View) {
        if (stateMap.containsKey(state)) {
            removeView(stateMap[state])
        }
        addView(view)
        view.visibility = View.GONE
        stateMap[state] = view
    }

    @Suppress("SameParameterValue")
    private fun modifyAlpha(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float
    ): Int {
        return modifyAlpha(color, (255f * alpha).toInt())
    }

    private fun modifyAlpha(@ColorInt color: Int, @IntRange(from = 0, to = 255) alpha: Int): Int {
        return color and 0x00ffffff or (alpha shl 24)
    }

    @ColorInt
    private fun Context.colorSecondary(): Int {
        val value = TypedValue()
        theme.resolveAttribute(R.attr.colorSecondary, value, true)
        return value.data
    }
}

enum class State {
    CONTENT, LOADING, INFO
}


