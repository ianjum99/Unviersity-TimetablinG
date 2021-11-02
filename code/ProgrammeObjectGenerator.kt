import code.Programme

class ProgrammeObjectGenerator () {
    fun generateObjects(parsedJSON: ArrayList<HashMap<String, String>>): ArrayList<Programme> {
        val array = ArrayList<Programme>()
        for (i in parsedJSON) {
            array.add(Programme(i["name"], i["type"]))
        }
        return array
    }
}