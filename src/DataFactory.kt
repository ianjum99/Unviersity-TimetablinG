import com.beust.klaxon.Klaxon
import com.google.gson.GsonBuilder
import kotlin.random.Random

private val klaxon = Klaxon()

class DataFactory(elements: Collection<Programme>) : ArrayList<Programme>(elements) {
    fun toJson() = GsonBuilder().setPrettyPrinting().create().toJson(this)

    companion object {
        fun fromJson(json: String) = DataFactory(klaxon.parseArray(json)!!)
    }

    fun getDataFactoryInstance(): DataFactory {
        return this
    }

    fun createProgramme(programme: Programme) {
        this.add(programme)
    }

    fun deleteProgramme(programme: Programme) {
        this.remove(programme)
    }

    fun createModule(programme: Programme, module: Module) {
        module.id = generateModuleId(programme)
        programme.modules.add(module)
    }

    fun generateModuleId(programme: Programme): String {
        var prefix = programme.name.slice(0..3).uppercase()
        val arrayOfIds = ArrayList<String>()
        var suffix = 0
        programme.modules.forEach { module -> arrayOfIds.add(module.id.slice(4..7)) }
        while (true) {
            suffix = Random.nextInt(1000, 1999)
            if (!(arrayOfIds.contains(suffix.toString()))) {
                break
            }
        }
        return prefix + suffix
    }

    fun deleteModule(programme: Programme, module: Module) {
        programme.modules.remove(module)
    }

    fun createActivity(module: Module, activity: Activity) {
        module.activities.add(activity)
    }

    fun deleteActivity(module: Module, activity: Activity) {
        module.activities.remove(activity)
    }

    fun setActivityDayAndHour(activity: Activity,dayHour: Pair<Int,Int>) {
        activity.day = dayHour.first
        activity.time = dayHour.second
    }

    fun getProgrammeFromActivity(activity: Activity): Programme {
        return this.first { programme -> programme.modules.any { module -> module.activities.contains(activity) } }
    }

    fun getModuleFromActivity(activity: Activity): Module {
        return (this.flatMap { it.modules }.filter { it.activities.contains(activity) }).first()
    }

    fun getAllActivities(): ArrayList<Activity> {
        return ArrayList(this.flatMap { it.modules }.flatMap { it.activities })
    }

    fun getProgrammeInstanceFromString(programmeName: String): Programme {
        return this.first { it.name == programmeName }
    }

    fun getModuleInstanceFromString(moduleName: String): Module {
        return (this.flatMap { it.modules }.filter { it.name == moduleName }).first()
    }

    fun getActivitiesInSameProgrammeYearTerm(programme: Programme, year: Int, term: Int): ArrayList<Activity> {
        return ArrayList((programme.modules.filter { module -> module.year == year && module.term == term }).flatMap { module -> module.activities })
    }

    fun checkForClashes(programme: Programme, year: Int, term: Int):  ArrayList<Pair<Activity, Activity>> {
        val listOfClashes = ArrayList<Pair<Activity, Activity>>()
        val listOfActivities = getActivitiesInSameProgrammeYearTerm(programme,year,term)

        for (currentActivity in listOfActivities) {
            val listCopy = listOfActivities.toMutableList()
            listCopy.remove(currentActivity)

            val clashes = ArrayList(listCopy.filter { activity -> activity.day == currentActivity.day && activity.time == currentActivity.time })

            if (currentActivity.duration > 1) {
                clashes += ArrayList(listCopy.filter { activity -> activity.day == currentActivity.day && activity.time == currentActivity.time+1 })
            }


            if (clashes.isNotEmpty()) {
//                listOfClashes.add(Pair(currentActivity, clashes))
                clashes.forEach { clashesWith -> listOfClashes.add(Pair(currentActivity,clashesWith))}

            }
        }
        return removeDuplicateClashes(listOfClashes)

    }

    fun removeDuplicateClashes(clashes: ArrayList<Pair<Activity, Activity>>): ArrayList<Pair<Activity, Activity>> {
        var i = 0
        while (i < clashes.size) {
            if (clashes.any { clashes[i].first == it.second && clashes[i].second == it.first }) {
                clashes.removeAt(i)
            }
            i+=1
        }
    return clashes}
}

class Programme (
    val name: String,
    val type: String,
    val modules: ArrayList<Module> = ArrayList<Module>()
)

class Module(
    var id: String,
    val year: Int,
    val name: String,
    val compulsory: Boolean,
    val term: Int,
    val activities: ArrayList<Activity> = ArrayList<Activity>(),
)

class Activity(
    val type: String,
    var day: Int,
    var time: Int,
    val duration: Int,
)