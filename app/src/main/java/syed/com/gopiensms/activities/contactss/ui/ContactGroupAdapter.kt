package syed.com.gopiensms.activities.contactss.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import syed.com.gopiensms.R
import java.util.ArrayList as ArrayList1


class ContactGroupAdapter(groups: ArrayList1<GroupModel>, context: Context) : BaseAdapter(){

    private var mContext: Context = context
    private var inflator: LayoutInflater
    private var Groups: ArrayList1<GroupModel> = groups
    private lateinit var groupNameText: TextView
    private lateinit var groupSizeText:TextView
    private lateinit var box: CheckBox
    private var itemChecked: BooleanArray
    private var isSelected: Boolean? = null

    
    
    init {
        Groups = groups
        this.itemChecked = BooleanArray(Groups.size)
        for (i in Groups.indices) itemChecked[i] = false
        inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        isSelected = false
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup): View? {
        var ra:View  = inflator.inflate(R.layout.group, p2, false)

        if(p1!=null) {
            ra = p1
        }

        groupNameText = ra.findViewById(R.id.nameField)
        groupSizeText = ra.findViewById(R.id.sizeField)
        box = ra.findViewById(R.id.checkBoxGroup)

        groupNameText.text = Groups[p0].getName()
        groupSizeText.text = Groups[p0].getNumber()?.size.toString()
	    box.isChecked = Groups[p0].isChecked()
        return ra
    }

    override fun getItem(p0: Int): Any {
        return Groups[p0]
    }

    override fun getItemId(p0: Int): Long {
        return Groups[p0].getId()
    }

    override fun getCount(): Int {
        return this.Groups.size
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }

    fun setItemCheckedTrue(i: Int) {
        itemChecked[i] = true
    }

    fun setItemCheckedFalse(i: Int) {
        itemChecked[i] = false
    }
}


