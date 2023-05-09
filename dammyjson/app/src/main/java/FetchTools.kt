import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import com.fasterxml.jackson.module.kotlin.readValue


class FetchTools {
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

    fun parseDatatoObject(jsonData: String): List<User> {
        //Log.d("test", jsonData)
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        return objectMapper.readValue<Users>(jsonData).users
    }

    fun parseOneUserData(jsonData: String): User {
        Log.d("testUser", jsonData.toString())
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        val d = objectMapper.readValue<User>(jsonData)
        Log.d("testUser", d.toString())
        return d
    }

    fun parseUserDataToJson(user: User): String {
        val objectMapper = ObjectMapper()
        val userJson = objectMapper.writeValueAsString(user)
        Log.d("tojson", userJson)
        return userJson
    }
}
