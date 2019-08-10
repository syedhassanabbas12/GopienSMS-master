package spartons.com.prosmssenderapp.activities.contactss.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_contacts_group.*
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.contactss.CreateGroupActivity
import spartons.com.prosmssenderapp.activities.sendBulkSms.ui.SendBulkSmsActivity
import spartons.com.prosmssenderapp.fragments.BaseFragment
import java.security.acl.Group
import java.util.*
import kotlin.collections.ArrayList

class TwoFragment : BaseFragment() {

    private lateinit var createGroupButton: MaterialButton
    private lateinit var SelectedGroup: ArrayList<ContactHolder>
    lateinit var sharedPreferences: SharedPreferences
//    private lateinit var oneNewGroup: GroupModel
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }

//    companion object {
//        fun getInstance() = TwoFragment()
//    }
    override fun onResume() {
        super.onResume()

        sharedPreferences = activity!!.getSharedPreferences("group contacts", AppCompatActivity.MODE_PRIVATE)
        val gson = Gson()
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val json: String = sharedPreferences.getString("groupOne", "")
        var groupModel: GroupModel = gson.fromJson(json, GroupModel::class.java)
        Toast.makeText(context,"PAYA: "+groupModel.getName(), Toast.LENGTH_SHORT).show()
        Toast.makeText(context,"Han Ho gaii RESUME", Toast.LENGTH_SHORT).show()
    }

    override fun getLayoutResId() = R.layout.fragment_contacts_group

    override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {
//        sharedPreferences = activity!!.getSharedPreferences("group contacts", AppCompatActivity.MODE_PRIVATE)
//        val gson: Gson = Gson()
//        val json: String = sharedPreferences.getString("groupOne", "")
//        var groupModel: GroupModel = gson.fromJson(json, GroupModel::class.java)
//        Toast.makeText(context,"PAYA: "+groupModel.getName(), Toast.LENGTH_SHORT).show()

//        if (!json.isNullOrEmpty()){
//            var groupModel: GroupModel = gson.fromJson(json, GroupModel::class.java)
//            if(!groupModel.getList().isNullOrEmpty()){
//                Toast.makeText(context,"PAYA: "+groupModel.getName(), Toast.LENGTH_SHORT).show()
//            }
//        }

        createGroupButton = mRootView.findViewById(R.id.create_group_btn1)
        var cc: Context? = context
        val fab: View = mRootView.findViewById(R.id.groupFloatingActionButton)
        SelectedGroup = ArrayList()

        fab.setOnClickListener { view ->
            if(SelectedGroup.isEmpty()) {
                Snackbar.make(view, "Must select Group", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            } else {
//                val i = Intent(context, SendBulkSmsActivity::class.java)
//                val v:ArrayList<String> = ArrayList(SelectedGroup.size)
//                i.putExtra("group", v)
//                startActivity(i)
//                activity?.finish()
            }
        }

        createGroupButton.setOnClickListener {
            val i = Intent(context, CreateGroupActivity::class.java)
            startActivity(i)
        }
    }
}

