/*
 * ======================================================================
 * =  PROJECT     PhotoHelper
 * =  MODULE      PhotoHelper.PhotoHelper
 * =  FILE NAME   PhotoItem.kt
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

package com.passionpenguin.photohelper.items

import android.os.Parcel
import android.os.Parcelable
import com.passionpenguin.photohelper.adapters.PhotoGridItemAdapter
import kotlin.properties.Delegates

class PhotoItem() : Parcelable {
    var mPosition: Int by Delegates.notNull()
    var mPath: String by Delegates.notNull()
    var mHolder: PhotoGridItemAdapter.ViewHolder by Delegates.notNull()
    var mCheckState = "false"
    var mSelectedPosition = 0

    constructor(position: Int, path: String, checkState: String) : this() {
        mPosition = position
        mPath = path
        mCheckState = checkState
    }

    constructor(position: Int, path: String, checkState: String, selectedPosition: Int) : this() {
        mPosition = position
        mPath = path
        mCheckState = checkState
        mSelectedPosition = selectedPosition
    }

    constructor(parcel: Parcel) : this() {
        mPosition = parcel.readInt()
        mSelectedPosition = parcel.readInt()
        mPath = parcel.readString()!!
        mCheckState = parcel.readString()!!
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.mPosition)
        dest.writeInt(this.mSelectedPosition)
        dest.writeString(this.mPath)
        dest.writeString(this.mCheckState)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoItem> {
        override fun createFromParcel(parcel: Parcel): PhotoItem {
            return PhotoItem(parcel)
        }

        override fun newArray(size: Int): Array<PhotoItem?>? {
            return arrayOfNulls(size)
        }
    }
}