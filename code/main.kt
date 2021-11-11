import DataFactory.Companion.fromJson

val fileName = "programmes.json"

fun main() {
    val jsonString = JsonHandler(fileName).readJSONFile().toString()
    var dataFactory = fromJson(jsonString)
    val gui = TimetableGUI(dataFactory)
    var commands = GUICommands.GUICommands(gui, dataFactory)


    commands.populateGUI()

//    commands.populateGUIbyProgramme(dataFactory[0])
//    commands.populateGUIbyYear(2)
}