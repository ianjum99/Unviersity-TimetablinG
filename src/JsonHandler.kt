import java.io.File
import java.io.InputStream

class JsonHandler {

    fun readJSONFile(): String? {
        var jsonData: String? = null
        try {
            val file = File("data.json")
            val  inputStream: InputStream = file.inputStream()
            jsonData = inputStream.bufferedReader().use{it.readText()}
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return jsonData
    }

    //The function above will open the file "data.json" and it will parse into a string, which is the data format
    //that "fromJson" in DataFactory uses to turn it into data classes.

    fun saveJSONFile(JsonData : String) {
        File("data.json").bufferedWriter().use { out ->
            out.write(JsonData)
        }
    }

    //This function takes the output of the method "toJson" in DataFactory, which is a string, and saves it to
    //"data.json", achieving persistence.
}