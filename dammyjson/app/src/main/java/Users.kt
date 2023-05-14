import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Users data class. It is used to parse the users data from the API.
 *
 * @param users is the list of users.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Users(val users: List<User>)
