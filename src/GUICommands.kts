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

//    fun removeDuplicateClashes(list: ArrayList<Pair<Activity, ArrayList<Activity>>?>) {
//        var newList: ArrayList<Pair<Activity, ArrayList<Activity>>?> = ArrayList()
//
//        for (pairs in list) {
//            list.forEach { pair -> pair!!.second.filter { activity -> list.filter { it.first == activity && pair.first in it.second }}
//        }
//    }
    fun populateGUIbyProgramme(programme: Programme, year: Int, term: Int) {
        clearGUI()
        val activities = (programme.modules.filter { module -> module.year == year && module.term == term }).flatMap { it.activities }
        val clashes = ArrayList(activities.map { activity -> dataFactory.checkForClashes(activity) })
        clashes.removeAll(listOf(null))
//        removeDuplicateClashes(clashes)
        clashes.forEach { clash ->
            if (clash != null) {
                println("${dataFactory.getModuleFromActivity(clash.first)} and ${clash.second.forEach { dataFactory.getModuleFromActivity(it).name }}")
            }
        }

        activities.forEach { activity -> addActivityToGUI(activity) }
    }
}