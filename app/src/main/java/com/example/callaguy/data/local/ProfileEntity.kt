package com.example.callaguy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey
    val id : Int = 1 ,
    val userName : String ,
    val email : String,
    val address : String ,
    val phone : String ,
    val profilePicture : String?,
    val isSynced : Boolean
)
