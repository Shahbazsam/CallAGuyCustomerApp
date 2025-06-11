package com.example.callaguy.presentation.Service.model

import androidx.annotation.DrawableRes

data class TipItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    @DrawableRes val imageUrl: Int,
    val description: String
)
