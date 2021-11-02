import code.JsonHandler


fun main() {
    val programmesHashMap = JsonHandler("programmes.json").parseJSONText()
    val modulesHashMap = JsonHandler("modules.json").parseJSONText()
    val activitiesHashMap = JsonHandler("activities.json").parseJSONText()
    println(programmesHashMap)
    println(modulesHashMap)
    println(activitiesHashMap)











}
