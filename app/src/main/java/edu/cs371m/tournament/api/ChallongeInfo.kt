package edu.cs371m.tournament.api

import com.google.gson.annotations.SerializedName
//class ChallongeInfo{
//    data class Challonge(
//        @SerializedName("participant")
//        val data : Participant
//    )
//
//    data class Participant(
//        @SerializedName("name")
//        val name : String,
//        @SerializedName("seed")
//        val seed : Int
//    )
//}

data class ChallongeInfo(
    @SerializedName("name")
    val name1: String,
    @SerializedName("seed")
    val seed1: Int

)

