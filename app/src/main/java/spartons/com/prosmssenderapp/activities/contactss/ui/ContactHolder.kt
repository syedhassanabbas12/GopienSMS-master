package spartons.com.prosmssenderapp.activities.contactss.ui

import androidx.annotation.NonNull
import java.io.Serializable

class ContactHolder(ID: Long, name: String, number: String) : Comparable<ContactHolder>, Serializable {

    private var id: Long = ID
    private var Name: String = name
    private var Number: String = number
    private var checkbox: Boolean = false

    init {
        this.id = ID
        setId(ID)
        this.Name = name
        setName(name)
        this.Number = number
        setNumber(number)
        this.checkbox = false
        setCheckbox(false)
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

    fun getNumber(): String? {
        return Number
    }

    fun setNumber(number: String) {
        Number = number
    }

    fun isCheckbox(): Boolean {
        return checkbox
    }

    fun setCheckbox(checkbox: Boolean) {
        this.checkbox = checkbox
    }

    override fun compareTo(@NonNull o: ContactHolder): Int {
        return Name!!.compareTo(o.Name!!)
    }

}
