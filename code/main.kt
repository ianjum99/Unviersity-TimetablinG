import DataFactory.Companion.fromJson

val fileName = "programmes.json"

fun main() {
    val jsonString = JsonHandler(fileName).readJSONFile().toString()
    var programmes = fromJson(jsonString)


    val test =(programmes.getAllActivities())

    var timetable = Timetable(programmes)
    (timetable.fillTimetable())
    println(timetable.timetable)

    val mockActivity = Activity("Lecture", 1,12,1)

    timetable.checkForClashes(mockActivity)

}