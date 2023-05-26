class ValidateTools {
// Regex from Android documentation
// (https://developer.android.com/reference/android/util/Patterns)

    fun isEmailValid(email: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length > 7 &&
                password.contains(Regex("[0-9]")) &&
                password.contains(Regex("[A-Z]"))
                password.contains(Regex("[a-z]"))
    }

    fun isUsernameValid(username: String): Boolean {
        return username.contains(Regex("^[a-zA-Z0-9_]{2,25}$"))
    }

    fun isPhoneValid(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }

    fun isFirstNameValid(firstName: String): Boolean {
        return firstName.length in 2..24 &&
                firstName.contains(Regex("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"))
    }

    fun isLastNameValid(lastName: String): Boolean {
        return lastName.length in 2..24 &&
                lastName.contains(Regex("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"))
    }

    fun isAgeValid(age: String): Boolean {
        return age.isNotEmpty() && age.toIntOrNull() != null && age.toInt() > 0 && age.toInt() < 120
    }

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