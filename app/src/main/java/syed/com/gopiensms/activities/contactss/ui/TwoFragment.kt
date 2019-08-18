package syed.com.gopiensms.activities.contactss.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import syed.com.gopiensms.R
import syed.com.gopiensms.activities.contactss.BaseContactActivity
import syed.com.gopiensms.activities.contactss.CreateGroupActivity
import syed.com.gopiensms.activities.sendBulkSms.ui.SendBulkSmsActivity
import syed.com.gopiensms.fragments.BaseFragment
import java.util.*
import kotlin.collections.ArrayList




class TwoFragment : BaseFragment() {
	
	private lateinit var createGroupButton: MaterialButton
	private lateinit var groupListView: ListView
	private lateinit var myGroupAdapter: ContactGroupAdapter
	private var selectedGroup: GroupModel? = null
	private lateinit var sharedPreferences: SharedPreferences
	private lateinit var groupModelList: ArrayList<GroupModel>
	
	
	override fun onResume() {
		super.onResume()
		groupModelList = (activity as BaseContactActivity).getGroupModelList()
		for(i in groupModelList.indices){
			groupModelList[i].setId(i.toLong())
		}
		sharedPreferences = activity!!.getSharedPreferences("group contacts", AppCompatActivity.MODE_PRIVATE)
		val prefsEditor = sharedPreferences.edit()
		val gson = Gson()
		val jsonList = gson.toJson(groupModelList)
		prefsEditor.putString("groupList", jsonList)
		prefsEditor.apply()
	}
	
	override fun getLayoutResId() = R.layout.fragment_contacts_group
	
	override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {
		
		groupModelList = (activity as BaseContactActivity).getGroupModelList()
		if(groupModelList.isNullOrEmpty()){

		} else {

		}
		
		val cc: Context? = context
		
		createGroupButton = mRootView.findViewById(R.id.create_group_btn1)
		groupListView = mRootView.findViewById(R.id.group_list_view)
		val fab: View = mRootView.findViewById(R.id.groupFloatingActionButton)
		
		
		fab.setOnClickListener { view ->
			if(selectedGroup==null) {
				Snackbar.make(view, "Must select Group", Snackbar.LENGTH_LONG)
					.setAction("Action", null)
					.show()
			} else {
                val i = Intent(context, SendBulkSmsActivity::class.java)

                i.putExtra("contacts", selectedGroup?.getNumbersArrayList())
                startActivity(i)
                activity?.finish()
			}
		}
		
		createGroupButton.setOnClickListener {
			val i = Intent(context, CreateGroupActivity::class.java)
			startActivityForResult(i, BaseContactActivity().PICK_CONTACT_REQUEST)
		}
		
		
		myGroupAdapter = ContactGroupAdapter(groupModelList, cc!!)
		val myComparator =
			Comparator<GroupModel> { obj1, obj2 ->
				obj1.getName().toString().compareTo(obj2.getName().toString())
			}
		Collections.sort(groupModelList, myComparator)
		groupListView.adapter = myGroupAdapter
		groupListView.choiceMode = ListView.CHOICE_MODE_SINGLE
		myGroupAdapter.notifyDataSetChanged()
		selectContactsGroup()
		
		groupListView.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, i, _ ->

			val gson = Gson()
			val json = gson.toJson(groupModelList[i])
			
			val intentResult = Intent(context, CreateGroupActivity::class.java)
			intentResult.putExtra("editGroup", json)
			startActivityForResult(intentResult, 2)
			
			true
		}
	}
	
	
	private fun selectContactsGroup() {
		groupListView.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->

			if(groupModelList[position].isChecked()){
				groupModelList[position].setCheckValue(false)
			} else {
				groupModelList[position].setCheckValue(true)
				selectedGroup = groupModelList[position]
				for(i in groupModelList.indices){
					if(i==position){
					
					} else{
						groupModelList[i].setCheckValue(false)
					}
				}
			}
			
			val check = view.findViewById<View>(R.id.checkBoxGroup) as CheckBox
			
			check.isChecked = !check.isChecked
			myGroupAdapter.notifyDataSetChanged()
		}
	}
}

