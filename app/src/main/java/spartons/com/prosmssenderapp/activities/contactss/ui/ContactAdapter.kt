package spartons.com.prosmssenderapp.activities.contactss.ui

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.phone.view.*
import spartons.com.prosmssenderapp.R
import kotlin.coroutines.coroutineContext
import java.util.ArrayList as ArrayList1


class ContactAdapter(phones: ArrayList1<ContactHolder>, context: Context) : BaseAdapter(), Filterable{

    private var mContext: Context = context
    private var inflator: LayoutInflater
    private var Phones: ArrayList1<ContactHolder> = phones
    private var filterList: ArrayList1<ContactHolder> = phones
    private var filter: CustomFilter? = null
    private lateinit var nameText: TextView
    private lateinit var cellText:TextView
    private lateinit var box: CheckBox
    private var itemChecked: BooleanArray
    private var isSelected: Boolean? = null

    init {
        Phones = phones
        this.filterList = Phones
        this.itemChecked = BooleanArray(Phones.size)
        for (i in Phones.indices) itemChecked[i] = false
        inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        isSelected = false
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup): View? {
        var ra:View  = inflator.inflate(R.layout.phone, p2, false)

        if(p1!=null) {
            ra = p1
        }

        nameText = ra.findViewById(R.id.nameField)
        cellText = ra.findViewById(R.id.cellField)
        box = ra.findViewById(R.id.checkBox)

        nameText.text = Phones[p0].getName()
        cellText.text = Phones[p0].getNumber()
        if (!isSelected!!) {
            if (Phones[p0].isCheckbox())
                box.isChecked = true
            else
                box.isChecked = false
        } else {
            box.visibility = View.GONE
        }
        return ra
    }

    override fun getItem(p0: Int): Any {
        return Phones[p0]
    }

    override fun getItemId(p0: Int): Long {
        return Phones[p0].getId()
    }

    override fun getCount(): Int {
        return this.Phones.size
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = CustomFilter()
        }
        return filter as CustomFilter
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


    internal inner class CustomFilter : android.widget.Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var constraint = constraint
            val results = FilterResults()

            if (constraint != null && constraint.length > 0) {
                val filters = ArrayList1<ContactHolder>()
                constraint = constraint.toString().toLowerCase()
                for (i in filterList.indices) {
                    if (filterList[i].getName()?.toLowerCase()?.contains(constraint)!!) {
                        val ch =
                            ContactHolder(filterList[i].getId(), filterList[i].getName().toString(), filterList[i].getNumber().toString())
                        ch.setCheckbox(filterList[i].isCheckbox())
                        filters.add(ch)
                    }
                }
                results.count = filters.size
                results.values = filters
            } else {
                results.count = filterList.size
                results.values = filterList
            }
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            Phones = results.values as java.util.ArrayList<ContactHolder>
            notifyDataSetChanged()
        }
    }

}


