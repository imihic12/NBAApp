package com.example.nbaapp.data

data class Player(
    val id: String?,
    val first_name: String?,
    val last_name: String?,
    val position: String?,
    val height_feet: Int?,
    val height_inches: Int?,
    val weight_pounds: Int?,
    val team: Team
)

data class PlayerResponse(
    val data: List<Player>?
)
