/*
 * Copyright (C) 2019. PT SehatQ Harsana Emedika - All Rights Reserved
 */

package com.sehatq.designlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton


class SehatqButton : AppCompatButton {

    //Custom values
    private var mButtonColor = 0
    private var mButtonType: String? = "primary"
    private var mCornerRadius = 0
    private var isBorderAvailable = false

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

    private fun refresh() {
        buttonBackgroundDrawable = createDrawable()
        updateBackground(buttonBackgroundDrawable)
    }

    private fun createDrawable(): Drawable {
        //Create the drawable based on button type
        val buttonDrawable = GradientDrawable()
        when(mButtonType) {
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
        if (!this.isEnabled) {
            mButtonColor = resources.getColor(R.color.alto)
            this.setTextColor(resources.getColor(R.color.white))
            isBorderAvailable = false
        }
        buttonDrawable.setColor(mButtonColor)  // Changes this drawbale to use a single color instead of a gradient
        buttonDrawable.cornerRadius = mCornerRadius.toFloat()

        if (isBorderAvailable)
            buttonDrawable.setStroke(4, resources.getColor(R.color.blue))

        return buttonDrawable
    }

    private fun updateBackground(background: Drawable?) {
        if (background == null) return
        //Set button background
        if (Build.VERSION.SDK_INT >= 16) {
            this.background = background
        } else {
            this.setBackgroundDrawable(background)
        }
    }

    fun setButtonColor(colorId: Int) {
        mButtonColor = colorId
        refresh()
    }

    fun setButtonType(type: String) {
        mButtonType = type
        refresh()
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