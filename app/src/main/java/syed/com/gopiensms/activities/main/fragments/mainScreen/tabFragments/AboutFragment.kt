package syed.com.gopiensms.activities.main.fragments.mainScreen.tabFragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import syed.com.gopiensms.R
import syed.com.gopiensms.fragments.BaseFragment


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class AboutFragment : BaseFragment(){

    companion object {
        fun getInstance() = AboutFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_about

    override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {
    }
}