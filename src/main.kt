import DataFactory.Companion.fromJson

fun main() {
    Timetable(fromJson(JsonHandler().readJSONFile().toString()))
    //The line above calls the class Timetable so the GUI opens.
}
