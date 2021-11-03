package quicktype

import com.beust.klaxon.*
import com.google.gson.Gson

private val klaxon = Klaxon()

class DataFactory(elements: Collection<Programme>) : ArrayList<Programme>(elements) {
    public fun toJson() = Gson().toJson(this)

    companion object {
        public fun fromJson(json: String) = DataFactory(klaxon.parseArray<Programme>(json)!!)
    }
}

data class Programme (
    val name: String,
    val type: String,
    val modules: ArrayList<Module>?
)

data class Module (
    val id: Long,
    val name: String,
    val compulsory: Boolean,

    @Json(name = "moduleLeader")
    val moduleLeader: String,
    val term: Long,
    val activities: ArrayList<Activity>?
)

data class Activity(
    @Json(name = "typeOfActivity")
    val typeOfActivity: String?,
    val day: Int?,
    val time: Int?,
    val duration: Int?
)
