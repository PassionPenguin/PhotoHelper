/*
 * ======================================================================
 * =  PROJECT     PhotoHelper
 * =  MODULE      PhotoHelper.PhotoHelper
 * =  FILE NAME   GridListActivity.kt
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

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.passionpenguin.photohelper.adapters.PhotoGridItemAdapter
import com.passionpenguin.photohelper.helpers.PermissionChecker
import com.passionpenguin.photohelper.items.PhotoItem


class GridListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_list)

        if (!(PermissionChecker.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") || PermissionChecker.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE"))) {
            PermissionChecker.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0x01)
        } else {
            val mImageUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val mCursor: Cursor? = contentResolver.query(
                mImageUri,
                null,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                arrayOf("image/jpeg", "image/png"),
                MediaStore.Images.Media.DATE_MODIFIED
            )
            val mPhotos = arrayListOf<PhotoItem>()
            if (mCursor != null) {
                var index = 0
                while (mCursor.moveToNext()) {
                    mPhotos.add(PhotoItem(index, mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)), "false"))
                    index++
                }
            }
            mPhotos.reverse()

            val adapter = PhotoGridItemAdapter(this, mPhotos)
            val ivPhotoGridList = findViewById<RecyclerView>(R.id.photoGridList)
            ivPhotoGridList.layoutManager = GridLayoutManager(this, 3)
            ivPhotoGridList.adapter = adapter

            val tvPhotoPreview = findViewById<TextView>(R.id.tvPhotoPreview)
            val tvPhotoEdit = findViewById<TextView>(R.id.tvPhotoEdit)
            val ivPhotoOriginalCheckBox = findViewById<CheckBox>(R.id.ivOriginalCheckbox)

            tvPhotoPreview.setOnClickListener{
                if(adapter.getSelectedData().isNotEmpty()) {
                    val i = Intent(this, PhotoPreview::class.java)
                    i.putParcelableArrayListExtra("selectedData", adapter.getSelectedData())
                    startActivity(i)
                }
            }
        }
    }
}