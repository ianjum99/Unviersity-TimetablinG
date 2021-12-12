import DataFactory.Companion.fromJson

fun main() {
    val jsonString = JsonHandler().readJSONFile().toString()
    var dataFactory = fromJson(jsonString)
    val gui = TimetableGUI(dataFactory)
    val commands = GUICommands.GUICommands(gui, dataFactory)

}
