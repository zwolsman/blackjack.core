package com.zwolsman.blackjack.deck.card

data class Card(val suit: Suit, val rank: Rank) {
    companion object {
        val BLANK = Card(Suit.NONE, Rank.NONE)
        fun fromString(str: String) = Card(Suit.values().first { it.icon == str.substringBefore(" ") }, Rank.values().first { it.icon == str.substringAfter(" ") })

    }
    override fun toString() = "Card($icon)"

    val icon:String
        get() = "${suit.icon} ${rank.icon}"
}