import com.beust.klaxon.*
import com.google.gson.Gson

private val klaxon = Klaxon()

class DataFactory(elements: Collection<Programme>) : ArrayList<Programme>(elements) {
    fun toJson() = Gson().toJson(this)!!

    companion object {
        fun fromJson(json: String) = DataFactory(klaxon.parseArray<Programme>(json)!!)
    }

    fun createProgramme(programme: Programme) {
        this.add(programme)
    }

    fun deleteProgramme(programme: Programme) {
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

    fun checkForClashes(activity: Activity): Pair<Activity, ArrayList<Activity>> {
        val listOfActivities = this.getAllActivities()
        var clashesWith: ArrayList<Activity> = arrayListOf()

        for (activityTwo in listOfActivities) {
            if (this.getModuleFromActivity(activityTwo).year == this.getModuleFromActivity(activity).year) {
                if (this.getModuleFromActivity(activityTwo).term == this.getModuleFromActivity(activity).term) {
                    if (activityTwo.time == activity.time && activityTwo.day == activity.day) {
                        clashesWith.add(activityTwo)
                    }
                }
            }
        }
        return Pair(activity,clashesWith)}

    fun getProgrammeInstanceFromString(programmeName: String): Programme {
        return this.first { it.name == programmeName }
    }
}



data class Programme (
    val name: String,
    val type: String,
    val modules: ArrayList<Module>?
)

data class Module (
    val id: String,
    val year: Int,
    val name: String,
    val compulsory: Boolean,
    val term: Long,
    val activities: ArrayList<Activity>?,
)

data class Activity(
    val typeOfActivity: String?,
    val day: Int?,
    val time: Int?,
    val duration: Int?,
)





