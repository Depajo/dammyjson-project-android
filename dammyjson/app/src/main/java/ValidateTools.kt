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
        return username.length > 3 &&
                username.contains(Regex("[a-zA-Z]"))
    }

    fun isPhoneValid(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }

    fun isFirstNameValid(firstName: String): Boolean {
        return firstName.length in 2..24 &&
                firstName.contains(Regex("[a-zA-Z]"))
    }

    fun isLastNameValid(lastName: String): Boolean {
        return lastName.length in 2..24 &&
                lastName.contains(Regex("[a-zA-Z]"))
    }

    fun isAgeValid(age: String): Boolean {
        return age.isNotEmpty() && age.toInt() > 0 && age.toInt() < 120 && age.toIntOrNull() != null
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