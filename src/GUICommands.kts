import java.awt.Color
import java.awt.Font

class GUICommands (val gui: Timetable, var dataFactory: DataFactory) {

    //This class is used to perform some operations on the Timetable, such as adding activities to it, or erasing the
    //grid when changing programmes. It takes an instance of the GUI and DataFactory as parameters.

    fun addActivityToGUI(activity: Activity) {
        for (i in 1..activity.duration) {
            var compulsoryOrOptional = if (dataFactory.getModuleFromActivity(activity).compulsory) {
                "Compulsory"
            } else {
                "Optional"
            }
            gui.getLabelFromCoordinates(activity.day + 1, (activity.time - 9 + i)).text =
                "${dataFactory.getModuleFromActivity(activity).id}\n${dataFactory.getModuleFromActivity(activity).name}\n${activity.type}\n${compulsoryOrOptional}"
        }
    }
    //The function above is used to add single activities to the Timetable in the GUI. It takes a single activity as
    //a parameter and using some functions of DataFactory it formats what will be displayed in the cell of the
    //Timetable.

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
    //This method is used to erase everything on the GUI, and it is used when the user wants to visualise a new programme.
    //It consists of a nested loop which is great for iterating through matrices. The loops will go through the entire
    //timetable and reset the cells' colour, font, and erase the text in them.

    fun removeActivityFromGUI(activity: Activity) {
        for (i in 1..activity.duration) {
            gui.getLabelFromCoordinates(activity.day + 1, (activity.time - 9 + i)).text = String()
        }
    }
    //This method is used to remove activities from the GUI. There is a for loop because activities might be longer
    //than one hour, so more cells of the GUI would need to be erased. All it does is it gets the Label corresponding to
    //the activity passed to the function, and it deletes its text.

    fun populateGUIbyProgramme(programme: Programme, year: Int, term: Int) {
        clearGUI()
        dataFactory.getActivitiesInSameProgrammeYearTerm(programme, year, term).forEach { activity -> addActivityToGUI(activity) }
    }

    //This method is called when a user clicks on "View Programme" on the Admin Panel. It starts by deleting everything
    //on the GUI and then it calls a method from DataFactory to get a list of activities in the same programme, year,
    //and term, and it calls "addActivityToGUI" for all of them using a forEach.


    fun findFirstAvailableSlot(day: Int = 0, hour: Int = 9, listOfActivities: ArrayList<Activity>, activity: Activity): Pair<Int, Int>? {
        return if (listOfActivities.none { it.day == day && it.time == hour} && listOfActivities.none { it.day == day && it.time == hour-1 && it.duration == 2}) {
            return Pair(day, hour)
        } else if (hour >= 19) {
            findFirstAvailableSlot(day + 1, 9, listOfActivities, activity)
        } else {
            findFirstAvailableSlot(day, hour + 1, listOfActivities, activity)
        }
    }

    //This function is called when the user wants to fix a clash in the clashes window. This function does not used nested
    //loops, but recursion, as it calls itself. This method will check


    fun solveClash(activity: Activity, pair: Pair<Int, Int>) {
        dataFactory.setActivityDayAndHour(activity,pair)
        addActivityToGUI(activity)
    }
}