package spartons.com.prosmssenderapp.activities.contactss.ui

class GroupModel(var groupName:String, var groupList:ArrayList<String>) {


    var n: String = groupName
    var v: ArrayList<String> = groupList

    fun getName():String{
        return n
    }

    fun getList():ArrayList<String>{
        return v
    }

    fun setName(nn:String){
        n = nn
    }

    fun setList(nn:ArrayList<String>){
        v = nn
    }
}