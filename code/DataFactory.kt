import com.beust.klaxon.*
import com.google.gson.Gson
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

private val klaxon = Klaxon()

class DataFactory(elements: Collection<Programme>) : ArrayList<Programme>(elements) {
    fun toJson() = Gson().toJson(this)!!

    companion object {
        fun fromJson(json: String) = DataFactory(klaxon.parseArray<Programme>(json)!!)
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

    fun getProgrammeFromActivity(activity: Activity): Programme {
        return(filter { it -> (it.modules!!.filter { it.activities!!.contains(activity)}).isNotEmpty()}).first()
    }

    fun getModuleFromActivity(activity: Activity): Module {
        val programme = this.getProgrammeFromActivity(activity)
        return programme.modules!!.filter { it.activities!!.contains(activity)}.first()
    }

    fun getAllActivities(): ArrayList<Activity> {
        var listOfActivities = ArrayList<Activity>()
        for (programme in this) {
            for (module in programme.modules!!) {
                for (activity in module.activities!!) {
                    listOfActivities.add(activity)
                }
            }
        }
    return listOfActivities}

    fun checkForClashes(newActivity: Activity, newActivityModule: Module): Pair<Int, ArrayList<Activity>> {
        var numberOfClashes = 0
        val listOfActivities = this.getAllActivities()
        var clashesWith: ArrayList<Activity> = arrayListOf()

        for (activity in listOfActivities) {
            if (this.getModuleFromActivity(activity).term == newActivityModule.term) {
                if (activity.time == newActivity.time && activity.day == newActivity.day) {
                    numberOfClashes += 1
                    clashesWith.add(activity)
                }
            }
        }
        return Pair(numberOfClashes,clashesWith)}

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
    val activities: ArrayList<Activity>?,
)

data class Activity(
    val typeOfActivity: String?,
    val day: Int?,
    val time: Int?,
    val duration: Int?,
)





