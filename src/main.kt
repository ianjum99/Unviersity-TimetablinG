import DataFactory.Companion.fromJson

fun main() {
    val jsonString = JsonHandler().readJSONFile().toString()
    var dataFactory = fromJson(jsonString)
    val gui = TimetableGUI(dataFactory)
    val commands = GUICommands.GUICommands(gui, dataFactory);
//    scalaClashDetection(dataFactory)
//
//    val daysOfWeek = arrayListOf<String>("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
//    val clashes = (dataFactory.checkForClashes(dataFactory[0],1,1))
//
//
//    for(pair in clashes) {
//        println("${dataFactory.getModuleFromActivity(pair.first).name} on ${daysOfWeek[pair.first.day]} at ${pair.first.time} (Duration: ${pair.first.duration} hours) clashes with:")
//
//        for (activity in pair.second!!) {
//            println("${dataFactory.getModuleFromActivity(activity).name} on ${daysOfWeek[activity.day]} at ${activity.time}")
//        }
//        println()
//    }


}
