/*
 * ======================================================================
 * =  PROJECT     PhotoHelper
 * =  MODULE      PhotoHelper.PhotoHelper
 * =  FILE NAME   PhotoPreview.kt
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

package com.passionpenguin.photohelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.passionpenguin.photohelper.items.PhotoItem
import kotlin.properties.Delegates

var checkCount = 0
var ivCheckedItemCount by Delegates.notNull<TextView>()
var ivCheckBox by Delegates.notNull<CheckBox>()
var vgCheckboxWrap by Delegates.notNull<RelativeLayout>()
var curPos = 0
var mItems by Delegates.notNull<ArrayList<PhotoItem>>()

class PhotoPreview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_preview)

        ivCheckedItemCount = findViewById(R.id.ivCheckedItemCount)
        ivCheckBox = findViewById(R.id.ivCheckBox)
        vgCheckboxWrap = findViewById(R.id.vgCheckboxWrap)

        ivCheckBox.isClickable = false

        mItems = intent.extras?.getParcelableArrayList("selectedData") ?: arrayListOf()
        checkCount = mItems.filter { i -> i.mCheckState == "true" }.size
        if (mItems.isEmpty()) onBackPressed()
        else {
            val ivViewPager = findViewById<ViewPager>(R.id.ivViewPager)
            ivViewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                override fun getCount(): Int {
                    return mItems.size
                }

                override fun getItem(position: Int): Fragment {
                    return PreviewPhoto(mItems[position].mPath)
                }
            }
            ivViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    curPos = position
                    val photoItem = mItems[position]
                    ivCheckedItemCount.text = ""
                    when (photoItem.mCheckState) {
                        "false" -> {
                            ivCheckBox.isChecked = false
                        }
                        "true" -> {
                            ivCheckBox.isChecked = true
                            ivCheckedItemCount.text = photoItem.mSelectedPosition.toString()
                        }
                    }
                    vgCheckboxWrap.setOnClickListener {
                        when (photoItem.mCheckState) {
                            "false" -> {
                                photoItem.mCheckState = "true"
                                checkCount++
                                photoItem.mSelectedPosition = checkCount
                                ivCheckedItemCount.text = checkCount.toString()
                                ivCheckBox.isChecked = true
                            }
                            "true" -> {
                                photoItem.mCheckState = "false"
                                checkCount--
                                mItems.filter { i -> i.mCheckState == "true" }.forEachIndexed { index, photoItem -> photoItem.mSelectedPosition = index + 1 }
                                ivCheckedItemCount.text = ""
                                ivCheckBox.isChecked = false
                            }
                        }
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                }
            })

            if (mItems[0].mCheckState == "true") {
                ivCheckBox.isChecked = true
                ivCheckedItemCount.text = mItems[0].mSelectedPosition.toString()
            }
        }
    }

    class PreviewPhoto(val path: String) : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.preview_image, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val v = view.findViewById<ImageView>(R.id.ivImage)
            if (v != null)
                Glide.with(v).load(path).into(v)
        }
    }
}