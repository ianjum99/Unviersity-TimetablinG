import java.io.File
import java.io.InputStream

class JsonHandler (val fileName: String) {

    fun readJSONFile(): String? {
        var jsonData: String? = null
        try {
            val file = File(fileName)
            val  inputStream: InputStream = file.inputStream()
            jsonData = inputStream.bufferedReader().use{it.readText()}
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return jsonData
    }
    fun saveJsonFile(JsonData : String) {
        File(fileName).bufferedWriter().use { out ->
            out.write(JsonData)
        }
    }
}