import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import com.fasterxml.jackson.module.kotlin.readValue


class FetchTools {

    // This function is used to get data from the server
    fun getData(url: String, response: (String) -> Unit, failure: (IOException) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                failure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { responseBody ->
                    response(responseBody)
                }
            }
        })
    }

    // This function is used to post data to the server
    fun postData(url: String, json: String,
                 response: (String) -> Unit, failure: (IOException) -> Unit) {
        val client = OkHttpClient()
        val reqBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder().url(url).post(reqBody).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                failure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { responseBody ->
                    response(responseBody)
                }
            }
        })
    }

    // This function is parsing the data from the server to a list of User objects
    fun parseAllUserDataToObject(jsonData: String): List<User> {
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        return objectMapper.readValue<Users>(jsonData).users
    }

    // This function is parsing the data from the server to a single User object
    fun parseOneUserDataToObject(jsonData: String): User {
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        return objectMapper.readValue<User>(jsonData)
    }

    // This function is parsing the object to json format to be sent to the server
    fun parseUserDataToJson(user: User): String {
        val objectMapper = ObjectMapper()
        val userJson = objectMapper.writeValueAsString(user)
        Log.d("tojson", userJson)
        return userJson
    }
}
