import code.Module

class ModuleObjectGenerator () {
    fun generateObjects(parsedJSON: ArrayList<HashMap<String, String>>?): ArrayList<Module> {
        val array = ArrayList<Module>()
        if (parsedJSON != null) {
            for (i in parsedJSON) {
                array.add(Module(i["id"], i["name"], i["compulsory"], i["term"], i["module_leader"]))
            }
        }
        return array
    }
}