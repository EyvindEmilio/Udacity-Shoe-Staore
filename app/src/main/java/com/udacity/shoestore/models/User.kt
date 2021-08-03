package com.udacity.shoestore.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val email: String, val password: String) : Parcelable{
    override fun toString(): String {
        return "User(email='$email', password='$password')"
    }
}