import com.beust.klaxon.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlin.random.Random

private val klaxon = Klaxon()

class DataFactory(elements: Collection<Programme>) : ArrayList<Programme>(elements) {
    fun toJson() = GsonBuilder().setPrettyPrinting().create().toJson(this)

    companion object {
        fun fromJson(json: String) = DataFactory(klaxon.parseArray(json)!!)
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
        var prefix = programme.name.slice(0..3).uppercase()
        val arrayOfIds = ArrayList<String>()
        var suffix = 0
        programme.modules!!.forEach { module -> arrayOfIds.add(module.id.slice(4..7)) }
        while (true) {
            suffix = Random.nextInt(1000,1999)
            if (!(arrayOfIds.contains(suffix.toString()))) {
                break
            }
        }
    return prefix+suffix}

    fun deleteModule(programme: Programme, module: Module) {
        programme.modules!!.remove(module)
    }

    fun createActivity(module: Module, activity: Activity) {
        module.activities?.add(activity)
    }

    fun deleteActivity(module: Module, activity: Activity) {
        module.activities!!.remove(activity)
    }

    fun getProgrammeFromActivity(activity: Activity): Programme {
        return this.first{programme -> programme.modules!!.any { module -> module.activities!!.contains(activity) } }
    }

    fun getModuleFromActivity(activity: Activity): Module {
        return (this.flatMap { it.modules!! }.filter { it.activities!!.contains(activity) }).first()
    }

    fun getAllActivities(): ArrayList<Activity> {
        return ArrayList(this.flatMap { it.modules!!}.flatMap { it.activities!! })
    }

    fun getProgrammeInstanceFromString(programmeName: String): Programme {
        return this.first { it.name == programmeName }
    }

    fun getModuleInstanceFromString(moduleName: String): Module {
        return (this.flatMap { it.modules!! }.filter { it.name == moduleName }).first()
    }

    fun getActivitiesInSameYearAndTerm(year: Long,term: Long): List<Activity> {
        return this.flatMap { it.modules!! }.filter { module -> module.year == year && module.term == term  }.flatMap {module-> module.activities!!}
    }

    fun checkForClashes(activity: Activity,
                        moduleOfActivity: Module = getModuleFromActivity(activity),
                        clashesWith:ArrayList<Activity> = ArrayList()): Pair<Activity,ArrayList<Activity>>? {

        val listOfActivities = this.getAllActivities()
        listOfActivities.remove(activity)

        ArrayList(listOfActivities.filter { currentActivity ->
            getModuleFromActivity(currentActivity).year == moduleOfActivity.year &&
            getModuleFromActivity(currentActivity).term == moduleOfActivity.term &&
            currentActivity.time == activity.time &&
            currentActivity.day == activity.day}).forEach { clash -> clashesWith.add(clash) }

        if (activity.duration > 1) {
            checkForClashes(Activity(activity.type,activity.day,activity.time+1,activity.duration-1), moduleOfActivity,clashesWith)
        }

        if (clashesWith.isNotEmpty()) {
            return Pair(activity,clashesWith)
        }
        return null
    }
}

class Programme (
    val name: String,
    val type: String,
    val modules: ArrayList<Module>?
)

data class Module(
    var id: String,
    val year: Int,
    val name: String,
    val compulsory: Boolean,
    val term: Int,
    val activities: ArrayList<Activity>?,
)

data class Activity(
    val type: String,
    val day: Int,
    val time: Int,
    val duration: Int,
)
