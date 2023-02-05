package com.example.nbaapp.data

data class Team(
    val id: String?,
    val abbreviation: String?,
    val city: String?,
    val conference: String?,
    val division: String?,
    val full_name: String?,
    val name: String?
)

data class TeamResponse(
    val data: List<Team>?
)
