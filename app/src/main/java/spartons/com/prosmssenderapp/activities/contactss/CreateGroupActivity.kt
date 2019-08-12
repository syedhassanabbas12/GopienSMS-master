package spartons.com.prosmssenderapp.activities.contactss

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.contactss.ui.*
import java.util.*
import kotlin.Comparator
import android.content.Intent


class CreateGroupActivity: AppCompatActivity() {
    
    private lateinit var Phones: ArrayList<ContactHolder>
    private lateinit var Selected: ArrayList<ContactHolder>
    private lateinit var FinalList_CheckItem: BooleanArray
    private lateinit var textViewTotalSel: TextView
    private lateinit var contact: ListView
    private lateinit var myAdapter: ContactAdapter
    private lateinit var arr: BooleanArray
    private lateinit var allselectbtn: MaterialButton
    private lateinit var clearbtn: MaterialButton
    private lateinit var createGroupButton: MaterialButton
    private lateinit var mySearchView: SearchView
    private lateinit var groupNameEditText: EditText
    private lateinit var groupModel: GroupModel
    private var idForGroup: Long = 100000000
    lateinit var context: Context
	
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_group)

        context = applicationContext
        Phones = ArrayList()
        Selected = ArrayList()
        
        shah()
        
        arr = BooleanArray(Phones.size)
        for (i in arr.indices) arr[i] = false
        
        mySearchView = findViewById(R.id.SearchViewmy1)
        textViewTotalSel = findViewById(R.id.TextViewTotal1)
        contact = findViewById(R.id.myListView1)
        allselectbtn = findViewById(R.id.select_all_btn1)
        clearbtn = findViewById(R.id.clear_btn1)
        createGroupButton = findViewById(R.id.createGroupButton)
        groupNameEditText = findViewById(R.id.groupNameEditText)
        
        val json = intent.getStringExtra("editGroup")
        val gson = Gson()
        if(json != null) {
            val editGroupModel: GroupModel = gson.fromJson(json, object : TypeToken<GroupModel>() {}.type)
            idForGroup = editGroupModel.getId()
            groupNameEditText.setText(editGroupModel.getName().toString())
            createGroupButton.text = "Save Changes"
            val editGroupPhonesList = editGroupModel.getNumber()
            for(i in editGroupPhonesList!!.indices){
                for(j in Phones.indices) {
                    if (Phones[j].getId() == editGroupPhonesList[i].getId()) {
                        Phones[j].setCheckbox(true)
                    }
                }
            }
        }

        createGroupButton.setOnClickListener{
            var n = groupNameEditText.text
            if(n.isEmpty()){
                Snackbar.make(contact, "Group name can not be empty!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            }
            if(Selected.isEmpty()){
                Snackbar.make(contact, "Must select contacts", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            }
            if(n.isNotEmpty() && !Selected.isNullOrEmpty()){
                
                
                val v:ArrayList<ContactHolder> = ArrayList(Selected.size)
                for(i in Selected.indices){
                    v.add(Selected[i])
                }
	            
                groupModel = GroupModel(idForGroup,n.toString(), v)

                val parsedValue = Gson().toJson(groupModel, object: TypeToken<GroupModel>(){}.type)
                val resultIntent = Intent()
                resultIntent.putExtra("newGroup", parsedValue)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        allselectbtn.setOnClickListener {
            for (i in Phones.indices) {
                Phones[i].setCheckbox(true)
                arr[i] = true
                Selected.add(ContactHolder(Phones[i].getId(), Phones[i].getName().toString(), Phones[i].getNumber().toString()))
            }
            myAdapter.notifyDataSetChanged()
            textViewTotalSel.text = "" + Phones.size
        }

        clearbtn.setOnClickListener {
            for (i in Phones.indices) {
                Phones[i].setCheckbox(false)
                arr[i] = false
            }
            myAdapter.notifyDataSetChanged()
            Selected.clear()
            textViewTotalSel.text = "" + 0
        }


        myAdapter = ContactAdapter(Phones, context)
        val myComparator =
            Comparator<ContactHolder> { obj1, obj2 -> obj1.getName().toString().compareTo(obj2.getName().toString()) }
        Collections.sort(Phones, myComparator)
        contact.adapter = myAdapter
        contact.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        selectContacts()

        mySearchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                myAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun shah(){
        try {
            val phones = context!!.contentResolver
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
            while (phones.moveToNext()) {
                val id = phones.getLong(phones.getColumnIndex(ContactsContract.Data._ID))
                val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                Phones.add(ContactHolder(id, name, phoneNumber))
            }
            phones.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
	

    fun selectContacts() {
        contact.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val check = view.findViewById<View>(R.id.checkBox) as CheckBox
            var count = 0
            Selected.clear()
            if (check.isChecked) {
                check.isChecked = false
                arr[position] = false

                myAdapter.setItemCheckedFalse(position)
                for (x in Phones.indices) {
                    if (Phones[x].getId() === id) {
                        Phones[x].setCheckbox(false)
                    }
                }
            } else {
                check.isChecked = true
                arr[position] = true
                myAdapter.setItemCheckedTrue(position)
                for (x in Phones.indices) {
                    if (Phones[x].getId() === id) {
                        Phones[x].setCheckbox(true)
                    }
                }
            }
            for (x in Phones.indices) {
                if (Phones[x].isCheckbox()) {
                    count++
                    Selected.add(ContactHolder(Phones[x].getId(), Phones[x].getName().toString(), Phones[x].getNumber().toString()))
                }
            }
            textViewTotalSel.text = count.toString() + ""
        }
    }
	
	
	override fun onBackPressed() {
		super.onBackPressed()
		finish()
	}
}


