package com.udacity.shoestore.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.shoestore.models.User

class LoginVM(application: Application) : AndroidViewModel(application) {

    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    private val users = mutableListOf<User>()

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    var currentUser: User? = null

    enum class Status {
        EMPTY_EMAIL,
        EMPTY_PASSWORD,
        EMPTY_AND_PASSWORD,
        ALREADY_REGISTERED,
        USER_NOT_REGISTERED,
        WRONG_EMAIL_OR_PASSWORD,
        REGISTRATION_SUCCESS,
        AUTH_SUCCESS
    }

    private fun isRegistered(email: String): Boolean {
        return users.find { it.email.equals(email, true) } != null
    }

    fun signUp() {
        val emailString = email.value ?: ""
        val passwordString = password.value ?: ""

        if (validateData()) {
            if (isRegistered(emailString)) {
                _status.value = Status.ALREADY_REGISTERED
            } else {
                val newUser = User(emailString, passwordString)
                users.add(newUser)
                currentUser = newUser
                _status.value = Status.REGISTRATION_SUCCESS
            }
        }
    }

    fun signIn() {
        val emailString = email.value ?: ""
        val passwordString = password.value ?: ""

        if (validateData()) {
            currentUser =
                users.find { it.email.equals(emailString, true) && it.password == passwordString }
            if (currentUser != null) {
                _status.value = Status.AUTH_SUCCESS
            } else {
                if (isRegistered(emailString)) {
                    _status.value = Status.WRONG_EMAIL_OR_PASSWORD
                } else {
                    _status.value = Status.USER_NOT_REGISTERED
                }
            }
        }
    }

    private fun validateData(): Boolean {
        val emailString = email.value
        val passwordString = password.value
        if (emailString.isNullOrEmpty() && passwordString.isNullOrEmpty()) {
            _status.value = Status.EMPTY_AND_PASSWORD
            return false
        } else if (emailString.isNullOrEmpty()) {
            _status.value = Status.EMPTY_EMAIL
            return false
        } else if (passwordString.isNullOrEmpty()) {
            _status.value = Status.EMPTY_PASSWORD
            return false
        }
        return true
    }

    fun resetStatus() {
        _status.value = null
    }

    fun resetEmailAndPassword() {
        email.value = null
        password.value = null
    }
}