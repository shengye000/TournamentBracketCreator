package edu.cs371m.tournament.api

import com.google.gson.annotations.SerializedName

data class CreateInfo(
    @SerializedName("tournament")
    var createData : CreateTournament
)

data class CreateTournament(
    @SerializedName("name")
    var createName : String,
    @SerializedName("url")
    var createURL : String,
    @SerializedName("tournament_type")
    var createType : String,
    @SerializedName("description")
    var createDescription : String
)