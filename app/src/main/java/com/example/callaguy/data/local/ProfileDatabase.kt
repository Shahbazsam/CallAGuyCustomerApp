package com.example.callaguy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ProfileEntity::class],
    exportSchema = false,
    version = 1
)
abstract class ProfileDatabase : RoomDatabase() {

    abstract fun dao() : ProfileDao

}