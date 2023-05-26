

/**
 * This class contains methods that are used to validate the user's input.
 *
 * @see isEmailValid
 * @see isPasswordValid
 * @see isUsernameValid
 * @see isPhoneValid
 * @see isFirstNameValid
 * @see isLastNameValid
 * @see isAgeValid
 * @see isUserValid
 *
 * @see android.util.Patterns
 */
class ValidateTools {

    /**
     * Checks if the email is valid.
     * @param email is the email that is checked.
     *
     * @return true if the email is valid, false if it is not.
     *
     * @see android.util.Patterns
     */
    fun isEmailValid(email: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Checks if the password is valid.
     * @param password is the password that is checked.
     *
     * @return true if the password is valid, false if it is not.
     */
    fun isPasswordValid(password: String): Boolean {
        return password.length > 7 &&
                password.contains(Regex("[0-9]")) &&
                password.contains(Regex("[A-Z]"))
                password.contains(Regex("[a-z]"))
    }

    /**
     * Checks if the username is valid.
     * @param username is the username that is checked.
     *
     * @return true if the username is valid, false if it is not.
     */
    fun isUsernameValid(username: String): Boolean {
        return username.contains(Regex("^[a-zA-Z0-9_]{2,25}$"))
    }

    /**
     * Checks if the phone number is valid.
     * @param phone is the phone number that is checked.
     *
     * @return true if the phone number is valid, false if it is not.
     *
     * @see android.util.Patterns
     */
    fun isPhoneValid(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }

    /**
     * Checks if the first name is valid.
     * @param firstName is the first name that is checked.
     *
     * @return true if the first name is valid, false if it is not.
     */
    fun isFirstNameValid(firstName: String): Boolean {
        return firstName.length in 2..24 &&
                firstName.contains(Regex("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"))
    }

    /**
     * Checks if the last name is valid.
     * @param lastName is the last name that is checked.
     *
     * @return true if the last name is valid, false if it is not.
     */
    fun isLastNameValid(lastName: String): Boolean {
        return lastName.length in 2..24 &&
                lastName.contains(Regex("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"))
    }

    /**
     * Checks if the age is valid.
     * @param age is the age that is checked.
     *
     * @return true if the age is valid, false if it is not.
     */
    fun isAgeValid(age: String): Boolean {
        return age.isNotEmpty() && age.toIntOrNull() != null && age.toInt() > 0 && age.toInt() < 120
    }

    /**
     * Checks if the user is valid.
     * @param user is the user that is checked.
     *
     * @return true if the user is valid, false if it is not.
     *
     * @see isFirstNameValid
     * @see isLastNameValid
     * @see isAgeValid
     * @see isEmailValid
     * @see isPhoneValid
     * @see isUsernameValid
     * @see isPasswordValid
     */
    fun isUserValid(user: User): Boolean {
        return isFirstNameValid(user.firstName) &&
                isLastNameValid(user.lastName) &&
                isAgeValid(user.age.toString()) &&
                isEmailValid(user.email) &&
                isPhoneValid(user.phone) &&
                isUsernameValid(user.username) &&
                isPasswordValid(user.password)
    }
}