package edu.cs371m.tournament

import java.io.Serializable

data class Game(val name1: String, val name2: String, var winner: String) : Serializable