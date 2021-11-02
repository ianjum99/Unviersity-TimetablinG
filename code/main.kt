import code.Hour
import code.JsonHandler
import code.Programme
import code.Timetable


fun main() {
    val programmesInstances = ProgrammeObjectGenerator().generateObjects(JsonHandler("programmes.json").parseJSONText())
    val modulesHashInstances = ModuleObjectGenerator().generateObjects(JsonHandler("modules.json").parseJSONText())
    val activitiesInstances = ActivityObjectGenerator().generateObjects(JsonHandler("activities.json").parseJSONText())

    val week_one = Timetable()


    for (i in activitiesInstances) {
        if (week_one.days[i.day].hours[i.start_time] == null) {
            week_one.days[i.day].hours[i.start_time] = i
        } else {
            println("clash")
        }
    }
    val weekdays: Array<String> = arrayOf("Monday","Tuesday","Wednesday","Thursday","Friday")
    for (day in 0..4) {
        println()
        println(weekdays[day])
        print(week_one.days[day].hours.toSortedMap())
    }
}

