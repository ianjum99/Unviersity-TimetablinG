package quicktype

import com.beust.klaxon.*
import com.google.gson.Gson

private val klaxon = Klaxon()

class DataFactory(elements: Map<String, Programme>) : HashMap<String, Programme>(elements) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = DataFactory (
                klaxon.parseJsonObject(java.io.StringReader(json)) as Map<String, Programme>
        )
    }
}

//    fun createProgramme(programme: Programme) {
//        this.add(programme)
//    }
//
//    fun deleteProramme(programme: Programme) {
//        this.remove(programme)
//    }
//
//    fun createModule(programme: Programme, module: Module) {
//        programme.modules?.add(module)
//    }
//
//    fun deleteModule(programme: Programme, module: Module) {
//        programme.modules?.remove(module)
//    }
//
//    fun createActivity(module: Module, activity: Activity) {
//        module.activities?.add(activity)
//    }
//
//    fun deleteActivity(module: Module, activity: Activity) {
//        module.activities?.remove(activity)
//    }


data class Programme (
        val name: String,
        val type: String,
        val modules: HashMap<String, Module>?
)
data class Module (
        val name: String,
        val compulsory: Boolean,
        val moduleLeader: String,
        val term: Long,
        val activities: HashMap<String, Activity>?
)

data class Activity (
        val typeOfActivity: String,
        val day: Long,
        val time: Long,
        val duration: Long
)
