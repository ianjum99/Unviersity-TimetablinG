import DataFactory.Companion.fromJson

fun main() {
    val dataFactory = fromJson(JsonHandler().readJSONFile().toString())
    Timetable(dataFactory)
}
