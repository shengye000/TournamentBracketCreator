package edu.cs371m.tournament.api

import com.google.gson.annotations.SerializedName

data class TournamentInfo(
    @SerializedName("tournament")
    val data2 : Tournament
)

data class Tournament(
    @SerializedName("name")
    val name2 : String,
    @SerializedName("url")
    val url2: String,
    @SerializedName("participants_count")
    val participant2 : Int
)