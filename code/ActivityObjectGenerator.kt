import code.Activity

class ActivityObjectGenerator () {
    fun generateObjects(parsedJSON: ArrayList<HashMap<String, String>>): ArrayList<Activity> {
        val array = ArrayList<Activity>()
        for (i in parsedJSON) {
            array.add(Activity(i["type_of_activity"] as String, i["day"]!!.toInt(), i["time"]!!.toInt(), 1))
        }
        return array
    }
}