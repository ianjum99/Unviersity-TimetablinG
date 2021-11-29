import DataFactory.Companion.fromJson
val fileName = "programmes.json"


fun main() {
    val jsonString = JsonHandler(fileName).readJSONFile().toString()
    var dataFactory = fromJson(jsonString)
    val gui = TimetableGUI(dataFactory)
    var commands = GUICommands.GUICommands(gui, dataFactory)


//    commands.populateGUIbyProgramme(dataFactory[0],1,1)
//    val activity = dataFactory[0].modules!![0].activities!![0]
//    println(dataFactory.checkForClashes(activity,dataFactory.getModuleFromActivity(activity)))
//    commands.populateGUIbyProgramme(dataFactory[0],1,1)
//    println(dataFactory.checkForClashes(dataFactory[0].modules!![0].activities!![0]))
}


