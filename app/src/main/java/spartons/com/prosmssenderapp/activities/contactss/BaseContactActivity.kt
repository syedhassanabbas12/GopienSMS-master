package spartons.com.prosmssenderapp.activities.contactss

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.contactss.ui.ContactAdapter
import spartons.com.prosmssenderapp.activities.contactss.ui.ContactHolder
import spartons.com.prosmssenderapp.activities.contactss.ui.OneFragment
import spartons.com.prosmssenderapp.activities.contactss.ui.TwoFragment
import spartons.com.prosmssenderapp.activities.sendBulkSms.adapter.SendBulkSmsContactAdapter
import java.util.*


class BaseContactActivity : AppCompatActivity(), OneFragment.OnDataPass {

//    private var selected: ArrayList<ContactHolder>? = null
    private lateinit var Phones: ArrayList<ContactHolder>
    private lateinit var Selected: ArrayList<ContactHolder>
    private lateinit var FinalList_CheckItem: BooleanArray

    override fun onDataPass(yesPhones: ArrayList<ContactHolder>, arr: BooleanArray, Sel: ArrayList<ContactHolder>) {
        Phones = yesPhones
        Selected = Sel
        FinalList_CheckItem = BooleanArray(Phones.size)
        FinalList_CheckItem = arr
    }

    override fun onDataSelectedPass(Sel: ArrayList<ContactHolder>) {
        Selected = Sel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contacts_base_activity)

        val viewPager: ViewPager = findViewById(R.id.contacts_viewpager)
        setupViewPager(viewPager)

        val tabLayout: TabLayout = findViewById(R.id.contacts_tabs)
        tabLayout.setupWithViewPager(viewPager)

        Phones = ArrayList()
        Selected = ArrayList()
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(OneFragment(), "Contacts")
        adapter.addFrag(TwoFragment(), "Groups")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
    }

    @Suppress("DEPRECATION")
    inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

}