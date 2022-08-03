package com.sehatq.sdk

import android.app.Activity
import android.content.Intent

class SehatQSdk {

    companion object {
        fun getInstance(): SehatQSdk {
            return SehatQSdk()
        }
    }

    fun showSampleActivity(activity: Activity) {
        activity.startActivity(Intent(activity, SampleActivity::class.java))
    }


}