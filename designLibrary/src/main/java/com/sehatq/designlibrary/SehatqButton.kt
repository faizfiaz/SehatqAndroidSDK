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

    //Init default values
    private fun init() {
        val resources = resources ?: return
        mButtonColor = resources.getColor(R.color.blue)
        mCornerRadius = resources.getDimensionPixelSize(R.dimen.dimen_4)
        mPaddingHorizontal = resources.getDimensionPixelSize(R.dimen.dimen_16)
        mPaddingVertical = resources.getDimensionPixelSize(R.dimen.dimen_10)
        isAllCaps = false
    }

    /**
     * Load and parse custom attributes for values of [isBorderAvailable], [isShadowAvailable],
     * [mPaddingVertical], [mPaddingHorizontal], [mButtonColor], [mButtonType], [mCornerRadius]
     *
     * @attr [R.styleable.SehatqButton_android_paddingHorizontal]
     * @attr [R.styleable.SehatqButton_android_paddingVertical]
     * @attr [R.styleable.SehatqButton_borderEnable]
     * @attr [R.styleable.SehatqButton_shadowEnable]
     * @attr [R.styleable.SehatqButton_buttonColor]
     * @attr [R.styleable.SehatqButton_buttonType]
     * @attr [R.styleable.SehatqButton_cornerRadius]
     */
    private fun parseAttrs(
        context: Context,
        attrs: AttributeSet
    ) {
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
                    mPaddingVertical = typedArray.getDimensionPixelSize(
                        attr,
                        resources.getDimensionPixelSize(R.dimen.dimen_16)
                    ) //Default is 16dp
                }
                R.styleable.SehatqButton_android_paddingHorizontal -> {
                    mPaddingHorizontal = typedArray.getDimensionPixelSize(
                        attr,
                        resources.getDimensionPixelSize(R.dimen.dimen_10)
                    ) //Default is 10dp
                }
                R.styleable.SehatqButton_buttonColor -> {
                    mButtonColor =
                        typedArray.getColor(
                            attr,
                            resources.getColor(R.color.blue)
                        ) //Default is color blue
                }
                R.styleable.SehatqButton_buttonType -> {
                    mButtonType = typedArray.getString(attr).toString()
                }
                R.styleable.SehatqButton_cornerRadius -> {
                    mCornerRadius =
                        typedArray.getDimensionPixelSize(
                            attr,
                            resources.getDimensionPixelSize(R.dimen.dimen_4)
                        ) //Default is 4dp
                }
            }
        }
        typedArray.recycle()
    }

    /**
     * Refresh the button  by updating its background using [updateBackground] with drawable
     * [buttonBackgroundDrawable] with the new attributes values.
     * Set Padding by the values of [mPaddingHorizontal] [mPaddingVertical]
     */
    private fun refresh() {
        this.setPadding(mPaddingHorizontal, mPaddingVertical, mPaddingHorizontal, mPaddingVertical)
        buttonBackgroundDrawable = createDrawable()
        updateBackground(buttonBackgroundDrawable)
    }

    /**
     * Create the drawable based on button's custom attributes of
     * [mButtonType] [mButtonColor] [isBorderAvailable] [isShadowAvailable]
     */
    private fun createDrawable(): Drawable {
        val buttonDrawable = GradientDrawable()
        when (mButtonType) {
            "primary" -> {
                this.setTextColor(resources.getColor(R.color.white))
                isBorderAvailable = false
            }
            "secondary" -> {
                this.setTextColor(resources.getColor(R.color.sea))
                isBorderAvailable = true
            }
            else -> {
                this.setTextColor(resources.getColor(R.color.white))
                isBorderAvailable = false
            }
        }

        //Handle disabled button's view
        if (!isEnabled) {
            mButtonColor = resources.getColor(R.color.alto)
            this.setTextColor(resources.getColor(R.color.white))
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

    /**
     * Update the background drawable of this button.
     *
     * @param background receive Drawable to apply with its attributes.
     */
    private fun updateBackground(background: Drawable?) {
        if (background == null) return
        this.background = background
    }

    /**
     * Set the color of this button.
     *
     * @param colorId receives color resource ID.
     */
    fun setButtonColor(colorId: Int) {
        mButtonColor = colorId
        refresh()
    }

    /**
     * Set the type / variant of this button to determine this button style.
     *
     * @param type receives types ( primary / secondary ).
     */
    fun setButtonType(type: String) {
        mButtonType = type
        refresh()
    }

    /**
     * Set the shadow state of this button.
     *
     * @param enabled True if this button is shadowed, false otherwise.
     */
    fun enableButtonShadow(enabled: Boolean) {
        isShadowAvailable = enabled
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            if (enabled) {
                this.elevation = resources.getDimension(R.dimen.dimen_8)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    this.outlineAmbientShadowColor = mButtonColor
                    this.outlineSpotShadowColor = mButtonColor
                }
            } else
                this.elevation = 0.0f
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (this.isEnabled)
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