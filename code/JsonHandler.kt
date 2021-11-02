package code

import java.io.File

class JsonHandler (val filename: String) {
    fun test(){
        try {
            var lines: List<String> = File(filename).readLines()
            for (line in lines) {
                println(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}