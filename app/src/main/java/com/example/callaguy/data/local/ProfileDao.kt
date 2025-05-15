package com.example.callaguy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull


@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile WHERE id = 1 LIMIT 1")
    fun getProfile(): Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("""
        INSERT OR REPLACE INTO profile (id, userName, email, address, phone, profilePicture, isSynced)
        VALUES (1, :userName, :email, :address, :phone, 
                (SELECT profilePicture FROM profile WHERE id = 1),
                (SELECT isSynced FROM profile WHERE id = 1))
    """)
    suspend fun upsertProfileInfo(
        userName: String,
        email: String,
        address: String?,
        phone: String?
    )

    @Query("UPDATE profile SET profilePicture = :picture, isSynced = :isSynced WHERE id = 1")
    suspend fun updatePictureAndSync(picture: String?, isSynced: Boolean)

    @Query("DELETE FROM profile")
    suspend fun deleteProfile()

    @Transaction
    suspend fun updateProfileInfo(
        userName: String,
        email: String,
        address: String? = null,
        phone: String? = null
    ) {
        val existing = getProfile().firstOrNull()
        if (existing == null) {
            insertProfile(
                ProfileEntity(
                    id = 1,
                    userName = userName,
                    email = email,
                    address = address,
                    phone = phone,
                    profilePicture = null,
                    isSynced = false
                )
            )
        } else {
            upsertProfileInfo(userName, email, address, phone)
        }
    }
}