package com.bookstore.admin.ui

data class Latihan(
    val id : String,
    val name : String,
    val ppu : Double,
    val batters :Batters,
    val topping: ArrayList<Topping>
   )

data class Batters (
    val batters: List<Batter>
)
data class Topping (
    val id: String,
    val type: String
)

data class Batter (
    val id: String,
    val type: String
)



