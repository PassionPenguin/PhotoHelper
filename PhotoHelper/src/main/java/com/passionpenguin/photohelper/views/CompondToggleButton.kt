/*
 * ======================================================================
 * =  PROJECT     PhotoHelper
 * =  MODULE      PhotoHelper.PhotoHelper
 * =  FILE NAME   CompondToggleButton.kt
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


class CompoundToggleButton(context: Context, attrs: AttributeSet) :
    RadiusButton(context, attrs) {
    private var curState = false
    /*
       ICON - true  -> ic_baseline_arrow_drop_up_24
       ICON - false -> ic_baseline_arrow_drop_down_24
     */

    init{
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_down_24, 0)
    }

    // Prevent Perform Click Error
    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            if (event.action == ACTION_UP) {
                super.performClick()
            }
            if (event.action == ACTION_MOVE || event.action == ACTION_DOWN)
                curState = when (curState) {
                    true -> {
                        this.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_up_24, 0)
                        false
                    }
                    else -> {
                        this.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_down_24, 0)
                        true
                    }
                }
        }
        return super.onTouchEvent(event)
    }
}