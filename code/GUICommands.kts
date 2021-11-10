class GUICommands (gui: TimetableGUI, df: ArrayList<Activity>) {

    fun populateGUI() {
        for (activity in df) {
            for (i in 1..activity.duration) {
                gui.getLabelFromCoordinates(activity.day + 1, (activity.time - 8 + i - 1)).text =
                    "${df.getModuleFromActivity(activity).id}"
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