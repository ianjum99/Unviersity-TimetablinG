import code.Hour
import code.JsonHandler
import code.Programme
import code.Timetable


fun main() {
    val programmesInstances = ProgrammeObjectGenerator().generateObjects(JsonHandler("programmes.json").parseJSONText())
    val modulesHashInstances = ModuleObjectGenerator().generateObjects(JsonHandler("modules.json").parseJSONText())
    val activitiesInstances = ActivityObjectGenerator().generateObjects(JsonHandler("activities.json").parseJSONText())

    val week_one = Timetable()

    for (day in 0..4) {
        print(week_one.days[day].hours)
        println()
    }


}
