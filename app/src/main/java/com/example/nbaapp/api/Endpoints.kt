package com.example.nbaapp.api

import com.example.nbaapp.data.Player
import com.example.nbaapp.data.PlayerResponse
import com.example.nbaapp.data.Team
import com.example.nbaapp.data.TeamResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoints {

    @GET("/api/v1/teams")
    fun teams(): Call<TeamResponse>

    @GET("/api/v1/players")
    fun players(): Call<PlayerResponse>

    @GET("/api/v1/players")
    fun searchPlayers(
        @Query("search") query: String
    ): Call<PlayerResponse>

    @GET("/api/v1/players/{id}")
    fun specificPlayer(
        @Path("id") id: String
    ): Call<Player>

    @GET("/api/v1/teams/{id}")
    fun specificTeam(
        @Path("id") id: String
    ): Call<Team>
}
