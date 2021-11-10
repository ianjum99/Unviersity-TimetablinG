import DataFactory.Companion.fromJson

val fileName = "programmes.json"

fun main() {
    val jsonString = JsonHandler(fileName).readJSONFile().toString()
    var dataFactory = fromJson(jsonString)


    val test =(dataFactory.getAllActivities())

//    var timetable = Timetable(dataFactory)
//    (timetable.fillTimetable())
//    println(timetable.timetable)

    val mockActivity = Activity("Lecture", 1,12,1)
    val mockActivity2 = Activity("Lecture", 1,20,1)


//    println(dataFactory.checkForClashes(mockActivity))
//    print(Programme("test","U", null))
//      println(dataFactory)
    val gui = TimetableGUI(dataFactory)

    val df = dataFactory.getAllActivities()

    for (activity in df) {
        gui.getLabelFromCoordinates(activity.day!!+1,activity.time!!-8).text =
            "${dataFactory.getModuleFromActivity(activity).id}"
    }
    gui.run {  }



}