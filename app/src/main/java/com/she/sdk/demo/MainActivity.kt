package com.she.sdk.demo

import Utils.FragmentNavigation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sehatq.sdk.SampleActivity
import com.sehatq.sdk.SehatQSdk

/**
 * Project sehatq-android-ui.
 *
 * Created by Aldrich_W on 18/10/21.
 */
class MainActivity : AppCompatActivity(), FragmentNavigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SehatQSdk.getInstance().showSampleActivity(this)
    }
}