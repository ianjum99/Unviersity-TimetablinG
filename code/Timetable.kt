class Timetable (var listOfProgrammes: DataFactory, var timetable: HashMap<String, HashMap<String, Activity?>?> = HashMap<String, HashMap<String, Activity?>?>()) {

    init {
        for (day in 0..4) {
            timetable[day.toString()] = HashMap<String, Activity?>()
            for (hour in 9..20) {
                timetable[day.toString()]!![hour.toString()] = null
            }
        }

    }

    fun fillTimetable() {
        val listOfActivities = listOfProgrammes.getAllActivities()

        for (activity in listOfActivities) {
            if (timetable[activity.day.toString()]!![activity.time.toString()] == null) {
                timetable[activity.day.toString()]!![activity.time.toString()] = activity
            } else {
                val clash1 = timetable[activity.day.toString()]!![activity.time.toString()]!!
                val clash2 = activity!!
                println("Clash between ${listOfProgrammes.getModuleFromActivity(clash1).name} " +
                        "(${clash1.typeOfActivity}) and ${listOfProgrammes.getModuleFromActivity(clash2).name}" +
                        " (${clash2.typeOfActivity})")
            }
        }
    }

    fun checkForClashes(newActivity: Activity) {
        var numberOfClashes = 0
        val listOfActivities = listOfProgrammes.getAllActivities()

        for (activity in listOfActivities) {
            if (activity.time == newActivity.time && activity.day == newActivity.day) {
                numberOfClashes += 1
            }
        }
        println("Number of clashes: ${numberOfClashes}")
    }
}