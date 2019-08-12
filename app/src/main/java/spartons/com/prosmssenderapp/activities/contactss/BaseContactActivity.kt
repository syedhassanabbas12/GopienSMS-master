package spartons.com.prosmssenderapp.activities.contactss

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.sendBulkSms.ui.SendBulkSmsActivity
import java.util.*
import spartons.com.prosmssenderapp.activities.contactss.ui.*
import android.app.Activity
import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList


class BaseContactActivity : AppCompatActivity(), OneFragment.OnDataPass {
	val PICK_CONTACT_REQUEST = 1
	val EDIT_CONTACT_REQUEST = 2
//    private var selected: ArrayList<ContactHolder>? = null
    private lateinit var Phones: ArrayList<ContactHolder>
    private lateinit var Selected: ArrayList<ContactHolder>
    private lateinit var FinalList_CheckItem: BooleanArray
	private var groupModelList: ArrayList<GroupModel> = ArrayList()
	lateinit var sharedPreferences: SharedPreferences
	private lateinit var newCreatedGroup: GroupModel
	

    override fun onDataPass(yesPhones: ArrayList<ContactHolder>, arr: BooleanArray, Sel: ArrayList<ContactHolder>) {
        Phones = yesPhones
        Selected = Sel
        FinalList_CheckItem = BooleanArray(Phones.size)
        FinalList_CheckItem = arr
    }

    override fun onDataSelectedPass(Sel: ArrayList<ContactHolder>) {
        Selected = Sel
    }
	
	fun getGroupModelList(): ArrayList<GroupModel> {
		return groupModelList
	}
	
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		
		val gson = Gson()
		val json = data?.getStringExtra("newGroup")
		if(json!=null){
			var parsed: GroupModel = gson.fromJson(json, GroupModel::class.java)
			var isFound = false
			for(i in groupModelList.indices){
				if(groupModelList[i].getId() == parsed.getId()){
					isFound = true
				}
			}
			if(isFound){
				for(i in groupModelList.indices){
					if(groupModelList[i].getId() == parsed.getId()){
						groupModelList[i] = parsed
					}
				}
			} else {
				groupModelList.add(parsed)
			}
		} else {
		
		}
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contacts_base_activity)
		
		sharedPreferences = getSharedPreferences("group contacts", MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString("groupList", "")
        if (json.isNullOrEmpty()) {

        } else {
	        groupModelList = gson.fromJson(json, object : TypeToken<ArrayList<GroupModel>>() {}.type)
        }

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
    
    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(applicationContext, SendBulkSmsActivity::class.java)
        startActivity(i)
        finish()
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