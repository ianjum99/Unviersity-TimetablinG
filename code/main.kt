import DataFactory.Companion.fromJson

val fileName = "programmes.json"


fun main() {
    val jsonString = JsonHandler(fileName).readJSONFile().toString()
    var dataFactory = fromJson(jsonString)
    val gui = TimetableGUI(dataFactory)
    var commands = GUICommands.GUICommands(gui, dataFactory)


    commands.populateGUI()


//    commands.populateGUIbyProgramme(dataFactory[0],1,1)
//    println(dataFactory.checkForClashes(dataFactory[0].modules!![0].activities!![0]))
}


