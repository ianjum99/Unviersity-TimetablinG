import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

data class Course(val name: String, val underOrPost: String, val modules: MutableList<String>) {

}

val mapper = jacksonObjectMapper()
var filePath = "code/db.json"

fun main() {
//    val modulesList = mutableListOf<String>("JVM", "FYP")
//    val cs = Course(name = "Computer Science", underOrPost = "Undergraduate", modules = modulesList)
//    val userJson = mapper.writeValueAsString(cs)
//    File("code/db.json").writeText(userJson)

    val courses = mapper.readValue<Course>(File(filePath))
    println(courses.modules)
}


