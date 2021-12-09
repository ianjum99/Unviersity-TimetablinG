import DataFactory.Companion.fromJson

fun main() {
    val jsonString = JsonHandler().readJSONFile().toString()
    var dataFactory = fromJson(jsonString)
    val gui = TimetableGUI(dataFactory)
    val commands = GUICommands.GUICommands(gui, dataFactory);
//    println(dataFactory.checkForClashes(dataFactory[0],1,1))
    (commands.findFirstAvailableSlot(dataFactory[0],1,1, Activity("Test",3,4,1) ))
}
