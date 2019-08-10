package spartons.com.prosmssenderapp.activities.contactss.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_contacts.*
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.sendBulkSms.ui.SendBulkSmsActivity
import spartons.com.prosmssenderapp.fragments.BaseFragment
import java.util.*
import kotlin.collections.ArrayList

class OneFragment : BaseFragment() {

    private lateinit var Phones: ArrayList<ContactHolder>
    private lateinit var SelectedList: ArrayList<ContactHolder>
    private lateinit var textViewTotalSel: TextView
    private lateinit var contact: ListView
    private lateinit var myAdapter: ContactAdapter
    private lateinit var arr: BooleanArray
    private lateinit var allselectbtn: MaterialButton
    private lateinit var clearbtn: MaterialButton
//    private lateinit var btn: ImageButton
//    private lateinit var btnSelectAll: ImageButton
    private lateinit var mySearchView: SearchView

    interface OnDataPass {
        fun onDataPass(yesPhones: ArrayList<ContactHolder>, arr: BooleanArray, Sel: ArrayList<ContactHolder>)
        fun onDataSelectedPass(Sel: ArrayList<ContactHolder>)
    }
    private var dataPasser: OnDataPass? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        dataPasser = activity as OnDataPass
    }


    companion object {
        fun getInstance() = OneFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_contacts

    override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {

        val fab: View = mRootView.findViewById(R.id.contactsFloatingActionButton)
        fab.setOnClickListener { view ->
            if(SelectedList.isEmpty()) {
                Snackbar.make(view, "Must select contacts", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            } else {
                dataPasser?.onDataSelectedPass(SelectedList)
                val i = Intent(context, SendBulkSmsActivity::class.java)
                val v:ArrayList<String> = ArrayList(SelectedList.size)
                for(i in SelectedList.indices){
                    v.add(SelectedList[i].getNumber().toString())
                }
//                Toast.makeText(activity, "Checking V $v", Toast.LENGTH_SHORT).show()
//                i.putStringArrayListExtra("javidan", v)
//                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                i.putExtra("contacts", v)
                startActivity(i)
                activity?.finish()
            }
        }
        addContacts()

        SelectedList = ArrayList()
        arr = BooleanArray(Phones.size)
        for (i in arr.indices) arr[i] = false
        dataPasser?.onDataPass(Phones, arr, SelectedList)

        var cc: Context? = context
        mySearchView = mRootView.findViewById(R.id.SearchViewmy)
        textViewTotalSel = mRootView.findViewById(R.id.TextViewTotal)
        contact = mRootView.findViewById(R.id.myListView)
        allselectbtn = mRootView.findViewById(R.id.select_all_btn)
        clearbtn = mRootView.findViewById(R.id.clear_btn)

        allselectbtn.setOnClickListener {
            for (i in Phones.indices) {
                Phones[i].setCheckbox(true)
                arr[i] = true
                SelectedList.add(ContactHolder(Phones[i].getId(), Phones[i].getName().toString(), Phones[i].getNumber().toString()))
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
            SelectedList.clear()
            textViewTotalSel.text = "" + 0
        }




        myAdapter = ContactAdapter(Phones, cc!!)
        val myComparator =
            Comparator<ContactHolder> { obj1, obj2 -> obj1.getName().toString().compareTo(obj2.getName().toString()) }
        Collections.sort(Phones, myComparator)
        contact.adapter = myAdapter
        contact.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        selectContacts(view)
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



    private fun addContacts() {
        Phones = ArrayList()
        try {
            val phones = context!!.contentResolver
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
            while (phones.moveToNext()) {
                val id = phones.getLong(phones.getColumnIndex(ContactsContract.Data._ID))
                val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                Toast.makeText(activity, ""+name, Toast.LENGTH_SHORT).show()
                Phones.add(ContactHolder(id, name, phoneNumber))
            }
            phones.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        Toast.makeText(activity, "Long CL1ick "+Phones[0].getName().toString(), Toast.LENGTH_SHORT).show()
//        dataPasser?.onDataPass(Phones, arr, SelectedList)
    }

    fun selectContacts(view: View?) {
        contact.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val check = view.findViewById<View>(R.id.checkBox) as CheckBox
            var count = 0
            SelectedList.clear()
            if (check.isChecked) {
                check.isChecked = false
                arr[position] = false

                myAdapter.setItemCheckedFalse(position)
                for (x in Phones.indices) {
                    if (Phones[x].getId() === id) {
                        Phones[x].setCheckbox(false)
                    }
                }
                //Phones.get(position).setCheckbox(false);
            } else {
                check.isChecked = true
                arr[position] = true
                myAdapter.setItemCheckedTrue(position)
                for (x in Phones.indices) {
                    if (Phones[x].getId() === id) {
                        Phones[x].setCheckbox(true)
                    }
                }
                //Phones.get(position).setCheckbox(true);
            }
            /*
                          for (int i=0;i<arr.length;i++) {
                              if(arr[i]) {
                                  count++;
                                  ContactHolder contactHolder = new ContactHolder(Phones.get(i).getId(),Phones.get(i).getName(),Phones.get(i).getNumber());
                                  contactHolder.setCheckbox(Phones.get(i).isCheckbox());
                                  SelectedList.add(contactHolder);
                              }
                          }*/
            for (x in Phones.indices) {
                if (Phones[x].isCheckbox()) {
                    count++
                    SelectedList.add(ContactHolder(Phones[x].getId(), Phones[x].getName().toString(), Phones[x].getNumber().toString()))
                }
            }
            dataPasser?.onDataSelectedPass(SelectedList)
            //Toast.makeText(getActivity(), ""+SelectedList.size(), Toast.LENGTH_SHORT).show();
            textViewTotalSel.text = count.toString() + ""
            dataPasser?.onDataPass(Phones, arr, SelectedList)
        }
    }
}

