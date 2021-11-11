import com.beust.klaxon.*
import com.google.gson.Gson
import kotlin.random.Random

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
        module.id = generateModuleId(programme)
        programme.modules?.add(module)
    }

    fun generateModuleId(programme: Programme): String {
        var moduleId = programme.name.slice(0..3).uppercase()
        val arrayOfIds = ArrayList<String>()
        for (module in programme.modules!!) {
            arrayOfIds.add(module.id.slice(4..7))
        }
        var suffix = 0
        while (true) {
            suffix = Random.nextInt(1000,1999)
            if (!(arrayOfIds.contains(suffix.toString()))) {
                moduleId += suffix
                break
            }
        }
    return moduleId}

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
        return (this.flatMap { it.modules!! }.filter { module -> module.activities!!.contains(activity) }).first()
    }

    fun getAllActivities(): ArrayList<Activity> {
        return ArrayList(this.flatMap { it.modules!!}.flatMap { it.activities!! })
    }

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

    fun getModuleInstanceFromString(moduleName: String): Module {
        return (this.flatMap { it.modules!! }.filter { it.name == moduleName }).first()
    }
}



data class Programme (
    val name: String,
    val type: String,
    val modules: ArrayList<Module>?
)

data class Module (
    var id: String,
    val year: Int,
    val name: String,
    val compulsory: Boolean,
    val term: Long,
    val activities: ArrayList<Activity>?,
)

data class Activity(
    val type: String,
    val day: Int,
    val time: Int,
    val duration: Int,
)