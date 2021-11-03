package quicktype

import com.beust.klaxon.*

private val klaxon = Klaxon()

class DataFactory(elements: Collection<Programme>) : ArrayList<Programme>(elements) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = DataFactory(klaxon.parseArray<Programme>(json)!!)
    }
}

data class Programme (
    val name: String,
    val type: String,
    val modules: ArrayList<Module>
)

data class Module (
    val id: Long,
    val name: String,
    val compulsory: Boolean,

    @Json(name = "module_leader")
    val moduleLeader: String,
    val term: Long,
    val activities: ArrayList<Activity>
)

data class Activity (
    @Json(name = "type_of_activity")
    val typeOfActivity: String,
    val day: Long,
    val time: Long,
    val duration: Long
)
