import com.beust.klaxon.Klaxon
import com.google.gson.GsonBuilder
import kotlin.random.Random

private val klaxon = Klaxon()

class DataFactory(elements: Collection<Programme>) : ArrayList<Programme>(elements) {
    //DataFactory is the class that holds all the programmes, which hold all the modules, that hold activities.

    fun toJson() = GsonBuilder().setPrettyPrinting().create().toJson(this)

    //The function above transforms the current instance of the class DataFactory into a String that will
    //be saved to "data.json" by JsonHandler.

    companion object {
        fun fromJson(json: String) = DataFactory(klaxon.parseArray(json)!!)
    }

    //fromJson takes a string as a parameter, and it parses it into Programme, Module, and Activities instances.

    fun createProgramme(programme: Programme) {
        this.add(programme)
    }

    fun deleteProgramme(programme: Programme) {
        this.remove(programme)
    }

    //The two functions above are used to create and delete programmes.

    fun createModule(programme: Programme, module: Module) {
        module.id = generateModuleId(programme)
        programme.modules.add(module)
    }

    //The function above takes a programme and a module as a parameter and it links them together.

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

    //The function above is used every time a new module is created to generate a module id. It gets the name of
    //the module's programme (e.g. Computer Science), and it takes the first letters of its name and appends four
    //random unique numbers to it in order to create the module code (e.g. COMP1815).

    fun deleteModule(programme: Programme, module: Module) {
        programme.modules.remove(module)
    }

    //The function above takes a programme and a module as parameters and deletes the module from the programme.

    fun createActivity(module: Module, activity: Activity) {
        module.activities.add(activity)
    }

    fun deleteActivity(module: Module, activity: Activity) {
        module.activities.remove(activity)
    }

    //The two functions above are used to create and delete activities.

    fun setActivityDayAndHour(activity: Activity,dayHour: Pair<Int,Int>) {
        activity.day = dayHour.first
        activity.time = dayHour.second
    }

    //The function above is used when the user wants to solve a clash. It takes an activity as a parameter and a pair
    //containing the new day and time, which will be changed by the same function.

    fun getProgrammeFromActivity(activity: Activity): Programme {
        return this.first { programme -> programme.modules.any { module -> module.activities.contains(activity) } }
    }

    fun getModuleFromActivity(activity: Activity): Module {
        return (this.flatMap { it.modules }.filter { it.activities.contains(activity) }).first()
    }

    fun getProgrammeInstanceFromString(programmeName: String): Programme {
        return this.first { it.name == programmeName }
    }

    fun getModuleInstanceFromString(moduleName: String): Module {
        return (this.flatMap { it.modules }.filter { it.name == moduleName }).first()
    }

    //The functions above are used to filter activities and modules from the list of programmes. These are necessary
    //as the classes we created do not hold methods and they do not have an attribute to "remember" their parent class.
    //For example, an instance of the class Activity does not store its parent module, but the module stores the activity
    //so it is possible through filters to look for the module that contains a specific activity, etc.

    fun getActivitiesInSameProgrammeYearTerm(programme: Programme, year: Int, term: Int): ArrayList<Activity> {
        return ArrayList((programme.modules.filter { module -> module.year == year && module.term == term }).flatMap { module -> module.activities })
    }

    //This function is yet another filter which takes a programme, year, and term as parameters and returns all the
    //activities that fall within the same categories.

    fun getClashes(programme: Programme, year: Int, term: Int):  ArrayList<Pair<Activity, Activity>> {
        val listOfActivities = getActivitiesInSameProgrammeYearTerm(programme,year,term)
        val listOfClashes = ArrayList<Pair<Activity, Activity>>()

        for (currentActivity in listOfActivities) {
            val clashes = ArrayList<Activity>()
            clashes += (listOfActivities.filter { activity -> activity.day == currentActivity.day
                    && (activity.time == currentActivity.time || (activity.time-1 == currentActivity.time && currentActivity.duration > 1))
                    && activity != currentActivity })

            if (clashes.isNotEmpty()) {
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

    //The functions above are in charge of detecting and returning all the clashes present in a specific programme,
    //year, and term. getClashes works by going through the list of activities and for each of them it filters out
    //all the activities that take place on the same day and hour. Since activities can last up to two hours, it also checks
    //that there are no activities in the previous hour with duration 2, which would create a clash. The last conditional
    //in the filter (activity != currentActivity) is to avoid that the methods return a list that has activities clashing
    //with themselves. Lastly, "removeDuplicateClashes" will be called in the return statement, which is a function
    //that deletes the inverse clashes, that occur when, for instance, A clashes with B, and B clashes with A. Since we
    //did not want to display the same clash twice, we delete one of them.
}

class Programme  (
    val name: String,
    val type: String,
    val modules: ArrayList<Module> = ArrayList()
)

class Module(
    var id: String,
    val year: Int,
    val name: String,
    val compulsory: Boolean,
    val term: Int,
    val activities: ArrayList<Activity> = ArrayList(),
)

class Activity(
    val type: String,
    var day: Int,
    var time: Int,
    val duration: Int,
)

//The classes above are the ones that hold all the data:
// - Programmes have a name, a type (undergraduate or postgraduate), and an ArrayList "modules", which contains many
//  instances of the class "Module"
// - Modules have an id, a year, a name, a term, a compulsory boolean, and an ArrayList of Activities.
// - Activities have a type, a day, a time, and a duration.