package com.example.callaguy.data.auth

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

interface TokenProvider {
    fun getToken():String?
}


class SharedPrefTokenProvider @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : TokenProvider {
    override fun getToken(): String? {
        val prefs = sharedPreferences.getString("jwt" , null)
        return prefs
    }
}