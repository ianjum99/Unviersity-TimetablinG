class GUICommands (val gui: TimetableGUI, var dataFactory: DataFactory) {

    fun addActivityToGUI(activity: Activity) {
        for (i in 1..activity.duration) {
            gui.getLabelFromCoordinates(activity.day + 1, (activity.time - 8 + i - 1)).text =
                "${dataFactory.getModuleFromActivity(activity).id}\n${dataFactory.getModuleFromActivity(activity).name}\n${activity.type}"
        }
    }

    fun clearGUI() {
        for (i in 1..5) {
            for (k in 1..12) {
                gui.getLabelFromCoordinates(i, k).text = String()
            }
        }
    }

    fun populateGUIbyProgramme(programme: Programme, year: Int, term: Int) {
        clearGUI()
        val activities = (programme.modules.filter { module -> module.year == year && module.term == term }).flatMap { it.activities }
        val clashes = dataFactory.checkForClashes(programme, year, term)
        activities.forEach { activity -> addActivityToGUI(activity) }
        println(clashes)
    }
}