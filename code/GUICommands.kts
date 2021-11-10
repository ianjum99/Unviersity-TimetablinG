class GUICommands (val gui: TimetableGUI, var dataFactory: DataFactory) {

    fun populateGUI() {
        for (activity in dataFactory.getAllActivities()) {
            for (i in 1..activity.duration) {
                gui.getLabelFromCoordinates(activity.day + 1, (activity.time - 8 + i - 1)).text =
                    "${dataFactory.getModuleFromActivity(activity).id}"
            }
        }
    }

    fun clearGUI() {
        for (i in 1..5) {
            for (k in 1..12) {
                gui.getLabelFromCoordinates(i, k).text = ""
            }
        }
    }
}