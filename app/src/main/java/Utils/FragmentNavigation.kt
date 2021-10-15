package Utils

import android.app.Activity
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

interface FragmentNavigation {
    fun navigateToFragment(
        fragment: Fragment,
        addToBackStack: Boolean,
        context: Activity,
        @IdRes contentId: Int
    ) {
        val transaction: FragmentTransaction =
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(fragment::class.java.simpleName)
        }
        transaction.replace(contentId, fragment).commit()
    }
}