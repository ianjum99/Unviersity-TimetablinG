import com.beust.klaxon.*
import com.google.gson.Gson

private val klaxon = Klaxon()

class DataFactory(elements: Collection<Programme>) : ArrayList<Programme>(elements) {
    public fun toJson() = Gson().toJson(this)

    companion object {
        public fun fromJson(json: String) = DataFactory(klaxon.parseArray<Programme>(json)!!)
    }

    fun createProgramme(programme: Programme) {
        this.add(programme)
    }

    fun deleteProramme(programme: Programme) {
        this.remove(programme)
    }

    fun createModule(programme: Programme, module: Module) {
        programme.modules?.add(module)
    }

    fun deleteModule(programme: Programme, module: Module) {
        programme.modules?.remove(module)
    }

    fun createActivity(module: Module, activity: Activity) {
        module.activities?.add(activity)
    }

    fun deleteActivity(module: Module, activity: Activity) {
        module.activities?.remove(activity)
    }

    fun getProgrammeInstance(programmeName: String): Programme? {
        for (i in this) {
            if (i.name == programmeName) {
                return i
            }
        }
        return null}
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
        val moduleLeader: String,
        val term: Long,
        val activities: ArrayList<Activity>?
)

data class Activity(
        val typeOfActivity: String?,
        val day: Int?,
        val time: Int?,
        val duration: Int?
)