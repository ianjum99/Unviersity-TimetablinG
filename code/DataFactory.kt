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
        programme.modules!!.forEach { module -> arrayOfIds.add(module.id.slice(4..7)) }
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

    fun checkForClashes(activity: Activity,
                        moduleOfActivity: Module = getModuleFromActivity(activity),
                        clashesWith:ArrayList<Activity> = ArrayList()): ArrayList<Activity> {
        ArrayList(this.getAllActivities().filter {getModuleFromActivity(it).year == moduleOfActivity.year &&
                                                        getModuleFromActivity(it).term == moduleOfActivity.term &&
                                                        it.time == activity.time &&
                                                        it.day == activity.day}).forEach { clash -> clashesWith.add(clash) }
        if (activity.duration > 1) {
            checkForClashes(Activity(activity.type,activity.day,activity.time+1,activity.duration-1), moduleOfActivity,clashesWith)
        }
        return clashesWith
    }

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
    val year: Long,
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