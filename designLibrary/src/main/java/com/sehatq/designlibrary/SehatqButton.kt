/*
 * Copyright (C) 2019. PT SehatQ Harsana Emedika - All Rights Reserved
 */

package com.sehatq.designlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton

/**
 * Project sehatq-android-ui.
 *
 * Created by Aldrich_W on 18/10/21.
 */
class SehatqButton : AppCompatButton {

    //Custom values
    private var mButtonColor = 0
    private var mButtonType: String? = "primary"
    private var mCornerRadius = 0
    private var mPaddingVertical = 0
    private var mPaddingHorizontal = 0
    private var isBorderAvailable = false
    private var isShadowAvailable = false

    //Background drawable
    private var buttonBackgroundDrawable: Drawable? = null

    constructor(ctx: Context) : super(ctx) {
        init()
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        init()
        parseAttrs(ctx, attrs)
    }

    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        ctx,
        attrs,
        defStyleAttr
    ) {
        init()
        parseAttrs(ctx, attrs)
    }

    private fun init() {
        //Init default values
        val resources = resources ?: return
        mButtonColor = resources.getColor(R.color.blue)
        mCornerRadius = resources.getDimensionPixelSize(R.dimen.dimen_4)
        mPaddingHorizontal = resources.getDimensionPixelSize(R.dimen.dimen_16)
        mPaddingVertical = resources.getDimensionPixelSize(R.dimen.dimen_10)
        isAllCaps = false
    }

    private fun parseAttrs(
        context: Context,
        attrs: AttributeSet
    ) {
        //Load from custom attributes
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.SehatqButton, 0, 0)
        for (i in 0 until typedArray.indexCount) {
            when (val attr = typedArray.getIndex(i)) {
                R.styleable.SehatqButton_borderEnable -> {
                    isBorderAvailable = typedArray.getBoolean(attr, true) //Default is true
                }
                R.styleable.SehatqButton_shadowEnable -> {
                    isShadowAvailable = typedArray.getBoolean(attr, true) //Default is true
                }
                R.styleable.SehatqButton_android_paddingVertical -> {
                    mPaddingVertical = typedArray.getDimensionPixelSize(attr, resources.getDimensionPixelSize(R.dimen.dimen_16))
                }
                R.styleable.SehatqButton_android_paddingHorizontal -> {
                    mPaddingHorizontal = typedArray.getDimensionPixelSize(attr, resources.getDimensionPixelSize(R.dimen.dimen_10))
                }
                R.styleable.SehatqButton_buttonColor -> {
                    mButtonColor =
                        typedArray.getColor(attr, resources.getColor(R.color.blue))
                }
                R.styleable.SehatqButton_buttonType -> {
                    mButtonType = typedArray.getString(attr).toString()
                }
                R.styleable.SehatqButton_cornerRadius -> {
                    mCornerRadius =
                        typedArray.getDimensionPixelSize(
                            attr,
                            resources.getDimensionPixelSize(R.dimen.dimen_4)
                        )
                }
            }
        }
        typedArray.recycle()
    }

    //Update the button background by using Drawables
    private fun refresh() {
        setPadding(mPaddingHorizontal, mPaddingVertical, mPaddingHorizontal, mPaddingVertical)
        buttonBackgroundDrawable = createDrawable()
        updateBackground(buttonBackgroundDrawable)
    }

    private fun createDrawable(): Drawable {
        //Create the drawable based on button's custom attributes
        val buttonDrawable = GradientDrawable()
        when(mButtonType) {
            "primary" -> {
                setTextColor(resources.getColor(R.color.white))
                isBorderAvailable = false
            }
            "secondary" -> {
                setTextColor(resources.getColor(R.color.sea))
                isBorderAvailable = true
            }
            else -> {
                setTextColor(resources.getColor(R.color.white))
                isBorderAvailable = false
            }
        }

        //Handle disabled button's view
        if (!isEnabled) {
            mButtonColor = resources.getColor(R.color.alto)
            setTextColor(resources.getColor(R.color.white))
            isBorderAvailable = false
        }
        buttonDrawable.setColor(mButtonColor)
        buttonDrawable.cornerRadius = mCornerRadius.toFloat()

        //Handle if border is available then apply the stroke
        if (isBorderAvailable)
            buttonDrawable.setStroke(4, resources.getColor(R.color.blue))

        //Handle if shadow is available then apply the elevation
        enableButtonShadow(isShadowAvailable)

        return buttonDrawable
    }

    //Set button background
    private fun updateBackground(background: Drawable?) {
        if (background == null) return
        this.background = background
    }

    //Set button main color
    fun setButtonColor(colorId: Int) {
        mButtonColor = colorId
        refresh()
    }

    //Set button type
    fun setButtonType(type: String) {
        mButtonType = type
        refresh()
    }

    //Set state enable / disable shadow for button
    fun enableButtonShadow(enabled: Boolean) {
        isShadowAvailable = enabled
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            if (enabled) {
                elevation = resources.getDimension(R.dimen.dimen_8)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    outlineAmbientShadowColor = mButtonColor
                    outlineSpotShadowColor = mButtonColor
                }
            }
            else
                elevation = 0.0f
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (isEnabled)
            when (event?.action) {
                //motion of pressing down button
                MotionEvent.ACTION_DOWN -> {
                    buttonBackgroundDrawable?.alpha = 200
                    updateBackground(buttonBackgroundDrawable)
                }
                //motion of releasing up button
                MotionEvent.ACTION_OUTSIDE, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    buttonBackgroundDrawable?.alpha = 255
                    updateBackground(buttonBackgroundDrawable)
                }
            }
        return super.onTouchEvent(event)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        //Update background color
        refresh()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        refresh()
    }
}