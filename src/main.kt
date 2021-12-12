import DataFactory.Companion.fromJson

fun main() {
    var dataFactory = fromJson(JsonHandler().readJSONFile().toString())
    val gui = Timetable(dataFactory)


}
