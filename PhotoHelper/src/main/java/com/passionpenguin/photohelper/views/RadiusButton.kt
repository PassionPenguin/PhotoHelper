/*
 * ======================================================================
 * =  PROJECT     PhotoHelper
 * =  MODULE      PhotoHelper.PhotoHelper
 * =  FILE NAME   RadiusButton.kt
 * =  LAST MODIFIED BY PASSIONPENGUIN [8/15/20 2:25 PM]
 * ======================================================================
 * Copyright 2020 PassionPenguin. All rights reserved.
 *   ~
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ======================================================================
 */

package com.passionpenguin.photohelper.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import androidx.core.content.res.ResourcesCompat
import com.passionpenguin.photohelper.R
import kotlin.properties.Delegates


open class RadiusButton(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    private var mRectF by Delegates.notNull<RectF>()
    private var mRadius = 16F * context.resources.displayMetrics.density
    private var mWidth = this.width
    private var mHeight = this.height

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w
        mHeight = h
        mRectF = RectF(width.toFloat(), 0F, 0F, height.toFloat())
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        if (width == 0) return

        paint.color = Color.parseColor("#EFEFEF")
        paint.style = Paint.Style.FILL

        canvas?.drawRoundRect(mRectF, mRadius, mRadius, paint)
        super.onDraw(canvas)
    }
}