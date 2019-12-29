package edu.cs371m.tournament.api

import com.google.gson.annotations.SerializedName

    data class ChallongeInfo(
        @SerializedName("participant")
        val data : Participant
    )

    data class Participant(
        @SerializedName("name")
        val name : String,
        @SerializedName("seed")
        val seed : Int,
        @SerializedName("id")
        val id : Int
    )


