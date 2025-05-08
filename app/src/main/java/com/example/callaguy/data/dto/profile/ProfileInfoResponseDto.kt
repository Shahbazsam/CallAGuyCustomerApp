package com.example.callaguy.data.dto.profile

import android.graphics.Picture
import kotlinx.serialization.Serializable


@Serializable
data class ProfileInfoResponseDto(
    val userName : String,
    val email : String,
    val address : String? ,
    val phone : String ?,
    val profilePicture: String?
)
