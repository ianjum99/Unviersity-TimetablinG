import code.JsonHandler
import code.Module
import code.Programme

import java.io.File
import java.lang.Exception
import java.nio.file.Paths

fun main() {
    val map : HashMap<String,Array<Module>>
    val module = Module("COMP1815", "JVM Applications", false, "Markus Wolf")
    val programme = Programme("Computer Science",'U')

    println(module.ID)

    val json = JsonHandler("test.json")
    json.test()

}

