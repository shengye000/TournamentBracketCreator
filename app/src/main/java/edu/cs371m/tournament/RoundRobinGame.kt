package edu.cs371m.tournament

import java.io.Serializable

data class RoundRobinGame(var edited: Boolean,
                          val p1: String, var p12: Int, var p13: Int, var p14: Int,
                          val p2: String, var p21: Int, var p23: Int, var p24: Int,
                          val p3: String, var p31: Int, var p32: Int, var p34: Int,
                          val p4: String, var p41: Int, var p42: Int, var p43: Int) : Serializable