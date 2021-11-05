class Timetable {

    fun createTimetable() : HashMap<String, HashMap<String, Activity?>?> {

        var timetable = HashMap<String, HashMap<String, Activity?>?>()

        for (day in 0..4) {
            timetable[day.toString()] = HashMap<String, Activity?>()
            for (hour in 9..20) {
                timetable[day.toString()]!![hour.toString()] = null
            }
        }
        return timetable
    }

    fun fillTimetable(timetable: HashMap<String, HashMap<String, Activity?>?>, listOfActivities: ArrayList<Activity>): HashMap<String, HashMap<String, Activity?>?> {
        for (activity in listOfActivities) {
            timetable[activity.day.toString()]!![activity.time.toString()] = activity
        }
    return timetable}
}