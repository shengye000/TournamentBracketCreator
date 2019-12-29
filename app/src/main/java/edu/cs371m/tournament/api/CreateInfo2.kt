package edu.cs371m.tournament.api

import com.google.gson.annotations.SerializedName

data class CreateInfo2(
    @SerializedName("participant")
    var createData : CreateParticipants
)

data class CreateParticipants(
    @SerializedName("name")
    var createName : String,
    @SerializedName("seed")
    var seed : Int
)