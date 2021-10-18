package com.she.ui

import Utils.FragmentNavigation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Project sehatq-android-ui.
 *
 * Created by Aldrich_W on 18/10/21.
 */
class MainActivity : AppCompatActivity(), FragmentNavigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateToFragment(DemoListFragment(), true, this, R.id.main_container)
    }
}