import java.io.File
import java.io.InputStream

class JsonHandler (val fileName: String) {

    fun readJSONFile(): String? {
        var json_data: String? = null
        try {
            val file = File(fileName)
            val  inputStream: InputStream = file.inputStream()
            json_data = inputStream.bufferedReader().use{it.readText()}
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return json_data
    }
    fun saveJsonFile(JsonData : String) {
        File(fileName).bufferedWriter().use { out ->
            out.write(JsonData)
        }
    }
}