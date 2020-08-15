/*
 * ======================================================================
 * =  PROJECT     PhotoHelper
 * =  MODULE      PhotoHelper.PhotoHelper
 * =  FILE NAME   PermissionChecker.kt
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

package com.passionpenguin.photohelper.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object PermissionChecker {
    /**
     * 检查是否有某个权限
     *
     * @param ctx
     * @param permission
     * @return
     */
    fun checkSelfPermission(ctx: Context, permission: String?): Boolean {
        return (ContextCompat.checkSelfPermission(ctx.applicationContext, permission!!)
                == PackageManager.PERMISSION_GRANTED)
    }

    /**
     * 动态申请多个权限
     *
     * @param activity
     * @param code
     */
    fun requestPermissions(activity: Activity?, permissions: Array<String?>, code: Int) {
        ActivityCompat.requestPermissions(activity!!, permissions, code)
    }

    /**
     * Launch the application's details settings.
     */
    fun launchAppDetailsSettings(context: Context) {
        val applicationContext: Context = context.applicationContext
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + applicationContext.packageName)
        if (!isIntentAvailable(context, intent)) return
        applicationContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun isIntentAvailable(context: Context, intent: Intent): Boolean {
        return context.applicationContext.packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        ).size > 0
    }
}
