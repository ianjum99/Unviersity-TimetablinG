import java.awt.Color
import java.awt.Font

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
                gui.getLabelFromCoordinates(i, k).background = Color.decode("#2D142C")
                gui.getLabelFromCoordinates(i, k).parent.background = Color.decode("#2D142C")
                gui.getLabelFromCoordinates(i, k).font = Font("Arial", Font.PLAIN, 10);
            }
        }
    }

    fun removeActivityFromGUI(activity: Activity) {
        for (i in 1..activity.duration) {
            gui.getLabelFromCoordinates(activity.day + 1, (activity.time - 8 + i - 1)).text = String()
        }
    }

    fun populateGUIbyProgramme(programme: Programme, year: Int, term: Int) {
        clearGUI()
        val activities = (programme.modules.filter { module -> module.year == year && module.term == term }).flatMap { it.activities }
        val clashes = dataFactory.getClashes(programme, year, term)
        activities.forEach { activity -> addActivityToGUI(activity) }
    }


    fun findFirstAvailableSlot(day: Int = 0, hour: Int = 9, listOfActivities: ArrayList<Activity>, activity: Activity): Pair<Int, Int>? {
        return if (listOfActivities.none { it.day == day && it.time == hour} && listOfActivities.none { it.day == day && it.time == hour-1 && it.duration == 2}) {
            return Pair(day, hour)
        } else if (hour >= 19) {
            findFirstAvailableSlot(day + 1, 9, listOfActivities, activity)
        } else {
            findFirstAvailableSlot(day, hour + 1, listOfActivities, activity)
        }
    }


    fun solveClash(activity: Activity, pair: Pair<Int, Int>) {
        dataFactory.setActivityDayAndHour(activity,pair)
        addActivityToGUI(activity)
    }
}