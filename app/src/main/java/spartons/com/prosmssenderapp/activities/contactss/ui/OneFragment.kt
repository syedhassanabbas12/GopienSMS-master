package spartons.com.prosmssenderapp.activities.contactss.ui

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.fragments.BaseFragment
import java.util.*

class OneFragment : BaseFragment() {

    private lateinit var Phones: ArrayList<ContactHolder>
    private lateinit var SelectedList: ArrayList<ContactHolder>
    private lateinit var textViewTotalSel: TextView
    private lateinit var contact: ListView
    private lateinit var myAdapter: ContactAdapter
    private lateinit var arr: BooleanArray
    private lateinit var btn: ImageButton
    private lateinit var mySearchView: SearchView

    interface OnDataPass {
        fun OnDataPass(yesPhones: ArrayList<ContactHolder>, arr: BooleanArray, Sel: ArrayList<ContactHolder>)
        fun OnDataSelectedPass(Sel: ArrayList<ContactHolder>)
    }
    private var dataPasser: OnDataPass? = null


    companion object {
        fun getInstance() = OneFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_contacts

    override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {

        val fab: View = mRootView.findViewById(R.id.contactsFloatingActionButton)
        fab.setOnClickListener { view ->
            if(SelectedList.isEmpty()) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            } else {
                dataPasser?.OnDataSelectedPass(SelectedList)
                activity?.finish()
            }
        }
        addContacts()

        var cc: Context? = context
        mySearchView = mRootView.findViewById(R.id.SearchViewmy)
        textViewTotalSel = mRootView.findViewById(R.id.TextViewTotal)
        contact = mRootView.findViewById(R.id.myListView)
        btn = mRootView.findViewById(R.id.ImageButtonCancel)

        btn.setOnClickListener {
            for (i in Phones.indices) {
                Phones[i].setCheckbox(false)
                arr[i] = false
            }
            myAdapter.notifyDataSetChanged()
            SelectedList.clear()
            textViewTotalSel.text = "" + 0
        }

        SelectedList = ArrayList()

        arr = BooleanArray(Phones.size)
        for (i in arr.indices) arr[i] = false
        dataPasser?.OnDataPass(Phones, arr, SelectedList)


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

   /* override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_one, container, false)

        addContacts()


        contact = view.findViewById<View>(R.id.myListView) as ListView
//        mySearchView = view.findViewById(R.id.SearchViewmy)
//        textViewTotalSel = view.findViewById(R.id.TextViewTotal)
//        contact = view.findViewById(R.id.myListView)
//        btn = view.findViewById(R.id.ImageButtonCancel)
//
//        btn.setOnClickListener {
//            for (i in Phones.indices) {
//                Phones[i].setCheckbox(false)
//                arr[i] = false
//            }
//            myAdapter.notifyDataSetChanged()
//            SelectedList.clear()
//            textViewTotalSel.text = "" + 0
//        }
//
//        btn.setOnLongClickListener {
//            Toast.makeText(activity, "Long CLick", Toast.LENGTH_SHORT).show()
//            contact.deferNotifyDataSetChanged()
//            false
//        }

        SelectedList = ArrayList()

        arr = BooleanArray(Phones.size)
        for (i in arr.indices) arr[i] = false
        dataPasser?.OnDataPass(Phones, arr, SelectedList)

        myAdapter = ContactAdapter(Phones, context)
        val myComparator =
            Comparator<ContactHolder> { obj1, obj2 -> obj1.getName().toString().compareTo(obj2.getName().toString()) }
        Collections.sort(Phones, myComparator)
        contact.adapter = myAdapter
        contact.choiceMode = ListView.CHOICE_MODE_MULTIPLE
//        selectContacts(view)

        return view
    }*/

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
        dataPasser?.OnDataPass(Phones, arr, SelectedList)
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
            dataPasser?.OnDataSelectedPass(SelectedList)
            //Toast.makeText(getActivity(), ""+SelectedList.size(), Toast.LENGTH_SHORT).show();
            textViewTotalSel.text = count.toString() + ""
            dataPasser?.OnDataPass(Phones, arr, SelectedList)
        }
    }


    /*private fun getContacts(): StringBuilder {
        Phones = ArrayList()
        val builder = StringBuilder()
        val resolver: ContentResolver = context!!.contentResolver
        val cursor : Cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
            null)

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = (cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                if (phoneNumber > 0) {
                    val cursorPhone = context!!.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                    if(cursorPhone.count > 0) {
                        while (cursorPhone.moveToNext()) {
                            val phoneNumValue = cursorPhone.getString(
                                cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            builder.append("Contact: ").append(name).append(", Phone Number: ").append(
                                phoneNumValue).append("\n\n")
                            Phones.add(ContactHolder(id.toLong(), name, phoneNumber))
//                            Log.e("Name ===>", Phones[0].getName().toString())
                        }
                    }
                    cursorPhone.close()
                }
            }
        } else {
            //   toast("No contacts available!")
        }
        cursor.close()
        return builder
    }*/
}

