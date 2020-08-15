/*
 * ======================================================================
 * =  PROJECT     PhotoHelper
 * =  MODULE      PhotoHelper.PhotoHelper
 * =  FILE NAME   PhotoGridItemAdapter.kt
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

package com.passionpenguin.photohelper.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.passionpenguin.photohelper.R
import com.passionpenguin.photohelper.items.PhotoItem

class PhotoGridItemAdapter(activity: Activity, private val mItems: ArrayList<PhotoItem>) :
    RecyclerView.Adapter<PhotoGridItemAdapter.ViewHolder>() {
    private val mInflater = LayoutInflater.from(activity)
    private var checkItems = arrayListOf<PhotoItem>()
    private var checkCount = 0

    fun getSelectedData(): ArrayList<PhotoItem> {
        return checkItems
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhotoContent: ImageView = view.findViewById(R.id.ivPhotoContent)
        val ivCheckBox: CheckBox = view.findViewById(R.id.ivCheckBox)
        val ivCheckedItemCount: TextView = view.findViewById(R.id.ivCheckedItemCount)
        val vgCheckboxWrap: RelativeLayout = view.findViewById(R.id.vgCheckboxWrap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(mInflater.inflate(R.layout.grid_photo_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.ivPhotoContent).load(mItems[position].mPath).into(holder.ivPhotoContent)
        mItems[position].mHolder = holder
        holder.ivCheckBox.isClickable = false
        holder.vgCheckboxWrap.setOnClickListener {
            when (mItems[position].mCheckState) {
                "false" -> {
                    mItems[position].mCheckState = "true"
                    checkCount++
                    checkItems.add(mItems[position])
                    mItems[position].mSelectedPosition = checkCount
                    holder.ivCheckedItemCount.text = checkCount.toString()
                    holder.ivCheckBox.isChecked = true
                }
                "true" -> {
                    mItems[position].mCheckState = "false"
                    checkCount--
                    checkItems.remove(mItems[position])
                    checkItems.forEachIndexed { index, el ->
                        el.mHolder.ivCheckedItemCount.text = (index + 1).toString()
                    }
                    holder.ivCheckedItemCount.text = ""
                    holder.ivCheckBox.isChecked = false
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}