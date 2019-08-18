package syed.com.gopiensms.activities.contactss.ui

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import java.io.Serializable

class GroupModel(ID: Long, name: String, numbers: ArrayList<ContactHolder>) : Comparable<GroupModel>, Serializable, Parcelable {

    private var id: Long = ID
    private var Name: String = name
    private var Numbers: ArrayList<ContactHolder> = numbers
    private var CheckValue: Boolean = false
    
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readArrayList(null) as ArrayList<ContactHolder>
    )
    
    init {
        this.id = ID
        setId(ID)
        this.Name = name
        setName(name)
        this.Numbers = numbers
        setNumber(numbers)
    }

    fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun getName(): String? {
        return Name
    }

    fun setName(name: String) {
        Name = name
    }

    fun getNumber(): ArrayList<ContactHolder>? {
        return Numbers
    }
    
    fun getNumbersArrayList(): ArrayList<String>? {
        var n: ArrayList<String> = ArrayList(Numbers.size)
        for(i in Numbers.indices){
            n.add(Numbers[i].getNumber().toString())
        }
        return n
    }

    fun setNumber(numbers: ArrayList<ContactHolder>) {
        Numbers = numbers
    }
    
    fun isChecked(): Boolean {
        return CheckValue
    }
    
    fun setCheckValue(name: Boolean) {
        CheckValue = name
    }
    
    override fun compareTo(@NonNull o: GroupModel): Int {
        return Name.compareTo(o.Name)
    }
    
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(Name)
        parcel.writeList(Numbers)
    }
    
    override fun describeContents(): Int {
        return 0
    }
    
    companion object CREATOR : Parcelable.Creator<GroupModel> {
        override fun createFromParcel(parcel: Parcel): GroupModel {
            return GroupModel(parcel)
        }
        
        override fun newArray(size: Int): Array<GroupModel?> {
            return arrayOfNulls(size)
        }
    }
    
}
