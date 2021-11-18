class GUICommands (val gui: TimetableGUI, var dataFactory: DataFactory) {

    fun populateGUI() {
        dataFactory.getAllActivities().forEach { activity -> addActivityToGUI(activity) }
    }
    //need to delete this method

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
        val activities = (programme.modules!!.filter { module -> module.year == year && module.term == term }).flatMap { it.activities!! }
        val clashesWithSameProgrammeActivities = activities.map { activity -> dataFactory.checkForClashes(activity) }

//        clashesWithSameProgrammeActivities.forEach { clash ->
//            if (clash != null) {
//                println("${dataFactory.getModuleFromActivity(clash.first)} and ${clash.second.forEach { dataFactory.getModuleFromActivity(it) }}")
//            }
//        }

        activities.forEach { activity -> addActivityToGUI(activity) }
    }
}