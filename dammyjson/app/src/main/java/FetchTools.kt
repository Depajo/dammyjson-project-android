import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * FetchTools is a class that contains functions for fetching data from the server. This class
 * uses the OkHttp3 and Jackson libraries. In this class we have four functions to call
 * API: getData, postData, putData and deleteData. And three functions to parse JSON and
 * convert it to Kotlin objects: parseUser, parseUsers and parseUserPosts.
 *
 * @see getData
 * @see postData
 * @see putData
 * @see deleteData
 * @see parseAllUserDataToObject
 * @see parseOneUserDataToObject
 * @see parseUserDataToJson
 */
class FetchTools {

    /**
     * getData is used to fetch data from the server.
     *
     * @param url is the url where the data is fetched from.
     * @param response is the function that is called when the data is fetched successfully.
     * @param failure is the function that is called when the data is not fetched successfully.
     *
     * @see OkHttpClient
     */
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

    /**
     * postData is used to post data to the server.
     *
     * @param url is the url where the data is posted to.
     * @param json is the data that is posted to the server.
     * @param response is the function that is called when the data is posted successfully.
     * @param failure is the function that is called when the data is not posted successfully.
     *
     * @see OkHttpClient
     */
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

    /**
     * putData is used to put data to the server.
     *
     * @param url is the url where the data is put to.
     * @param json is the data that is put to the server.
     * @param response is the function that is called when the data is put successfully.
     * @param failure is the function that is called when the data is not put successfully.
     *
     * @see OkHttpClient
     */
    fun putData(url: String, json: String,
                 response: (String) -> Unit, failure: (IOException) -> Unit) {
        val client = OkHttpClient()
        val reqBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder().url(url).put(reqBody).build()

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

    /**
     * deleteData is used to delete data from the server.
     *
     * @param url is the url where the data is deleted from.
     * @param response is the function that is called when the data is deleted successfully.
     * @param failure is the function that is called when the data is not deleted successfully.
     *
     * @see OkHttpClient
     */
    fun deleteData(url: String, response: (String) -> Unit, failure: (IOException) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).delete().build()

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

    /**
     * parseAllUserDataToObject is used to parse data from the server to a list of User objects.
     *
     * @param jsonData is the data that is parsed to a list of User objects.
     * @return a list of User objects.
     */
    fun parseAllUserDataToObject(jsonData: String): List<User> {
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        return objectMapper.readValue<Users>(jsonData).users
    }

    /**
     * parseOneUserDataToObject is used to parse data from the server to a single User object.
     *
     * @param jsonData is the data that is parsed to a single User object.
     * @return a single User object.
     */
    fun parseOneUserDataToObject(jsonData: String): User {
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        return objectMapper.readValue<User>(jsonData)
    }

    /**
     * parseUserDataToJson is used to parse a User object to json format.
     *
     * @param user is the User object that is parsed to json format.
     * @return a User object in json format.
     */
    fun parseUserDataToJson(user: User): String {
        val objectMapper = ObjectMapper()
        val userJson = objectMapper.writeValueAsString(user)
        Log.d("tojson", userJson)
        return userJson
    }
}
