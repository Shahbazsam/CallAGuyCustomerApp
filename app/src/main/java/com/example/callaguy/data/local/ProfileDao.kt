package com.example.callaguy.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile Where id = 1 LIMIT 1")
    fun getProfile() : Flow<ProfileEntity?>

    @Query("""
    UPDATE profile 
    SET userName = :userName,
        email = :email,
        address = :address,
        phone = :phone
    WHERE id = 1
""")
    suspend fun updateProfileInfo(
        userName: String,
        email: String,
        address: String?,
        phone: String?
    )

    @Query("UPDATE profile SET profilePicture = :picture, isSynced = :isSynced WHERE id = 1")
    suspend fun updatePictureAndSync(picture: String?, isSynced: Boolean)


    @Query("DELETE FROM profile")
    suspend fun deleteProfile()
}