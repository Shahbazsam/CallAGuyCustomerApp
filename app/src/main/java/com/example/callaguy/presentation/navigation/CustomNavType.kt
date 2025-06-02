package com.example.callaguy.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.callaguy.domain.model.GetServiceRequestModel
import kotlinx.serialization.json.Json

object CustomNavType {
    val orderType = object : NavType<GetServiceRequestModel>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): GetServiceRequestModel? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): GetServiceRequestModel {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: GetServiceRequestModel): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: GetServiceRequestModel) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }
}