package code


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
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

    fun parseJSONText(): ArrayList<HashMap<String, String>>? {
        return try {
            val gson = GsonBuilder().create()
            val json_text = readJSONFile()
            val jsonList: ArrayList<HashMap<String, String>> = gson.fromJson<ArrayList<HashMap<String, String>>>(json_text, object : TypeToken<ArrayList<HashMap<String, String>>>(){}.type)
            jsonList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}