import DataFactory.Companion.fromJson

fun main() {
    val jsonString = JsonHandler().readJSONFile().toString()
    var dataFactory = fromJson(jsonString)
    val gui = TimetableGUI(dataFactory)
    val commands = GUICommands.GUICommands(gui, dataFactory);
    var freeSlot = (commands.findFirstAvailableSlot(day=4, hour = 10, listOfActivities = dataFactory.getActivitiesInSameProgrammeYearTerm(dataFactory[0],1,1)))
    if (freeSlot != null) {
        commands.solveClash(dataFactory[0].modules[0].activities[1], freeSlot)
    }
}
