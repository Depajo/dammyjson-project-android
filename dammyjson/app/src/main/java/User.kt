import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * User data class. It is used to parse the user data from the API.
 *
 * @param id is the id of the user.
 * @param firstName is the first name of the user.
 * @param lastName is the last name of the user.
 * @param age is the age of the user.
 * @param email is the email of the user.
 * @param phone is the phone number of the user.
 * @param username is the username of the user.
 * @param password is the password of the user.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String
)
