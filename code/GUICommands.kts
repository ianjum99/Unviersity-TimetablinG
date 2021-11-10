class GUICommands (val gui: TimetableGUI, var dataFactory: DataFactory) {

    fun populateGUI() {
        for (activity in dataFactory.getAllActivities()) {
                addActivityToGUI(activity)
        }
    }

    fun addActivityToGUI(activity: Activity) {
        for (i in 1..activity.duration) {
            gui.getLabelFromCoordinates(activity.day + 1, (activity.time - 8 + i - 1)).text =
                dataFactory.getModuleFromActivity(activity).id
        }
    }

    fun clearGUI() {
        for (i in 1..5) {
            for (k in 1..12) {
                gui.getLabelFromCoordinates(i, k).text = ""
            }
        }
    }

    fun populateGUIbyProgramme(programme: Programme) {
        val activities = programme.modules!!.flatMap { module -> module.activities!! }
        for (activity in activities) {
            addActivityToGUI(activity)
        }
    }

//    fun populateGUIbyYear(year: Int) {
//        val modules = (dataFactory.flatMap { it.modules!!.filter { module -> module.year == year }.flatMap { module -> module.activities!! }})
//    }


    //combine programme year and term
}