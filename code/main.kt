import code.JsonHandler
import quicktype.Activity
import quicktype.Programme
import quicktype.DataFactory
import quicktype.DataFactory.Companion.fromJson
import quicktype.Module


fun main() {
    val jsonString = JsonHandler("programmes.json").readJSONFile().toString()
    var programmes = DataFactory.fromJson(jsonString)
}

