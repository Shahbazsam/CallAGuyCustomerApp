package com.example.callaguy.data.auth

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
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