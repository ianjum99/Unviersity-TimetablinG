import DataFactory.Companion.fromJson

val db = "programmes.json"

fun main() {
    val jsonString = JsonHandler(db).readJSONFile().toString()
    var programmes = fromJson(jsonString)

//    programmes[0].modules[0].activities.add(Activity("TEST2",2,12,1))
//    programmes[0].modules?.add(Module(1,"computer",false,"Peter Smith",2, ArrayList<Activity>()))
//    println(programmes[0].modules!![2].activities!!.add(Activity("TEST2",2,12,1)))
    var timetable = (Timetable().createTimetable())

    var specific = programmes.forEach {
        programme -> programme.modules?.forEach {
                module -> module.activities?.forEach {
                    activity -> println(activity)
                }
        }
    }

    print(specific)
}