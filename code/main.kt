import DataFactory.Companion.fromJson

val fileName = "programmes.json"

fun main() {
    val jsonString = JsonHandler(fileName).readJSONFile().toString()
    var programmes = fromJson(jsonString)


    val test =(programmes.getAllActivities())


}