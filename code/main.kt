import code.JsonHandler
import com.google.gson.Gson
import quicktype.Activity
import quicktype.DataFactory.Companion.fromJson
import quicktype.Module
import quicktype.Programme


fun main() {
    val jsonString = JsonHandler("programmes.json").readJSONFile().toString()
    var programmes = fromJson(jsonString)

//    programmes[0].modules[0].activities.add(Activity("TEST2",2,12,1))
//    programmes[0].modules?.add(Module(1,"computer",false,"Peter Smith",2, ArrayList<Activity>()))
//    println(programmes[0].modules!![2].activities!!.add(Activity("TEST2",2,12,1)))
    println(programmes[0])

}
