package com.zwolsman.blackjack.deck.card

data class Card(val suit: Suit, val rank: Rank) {
    companion object {
        val BLANK = Card(Suit.NONE, Rank.NONE)
        fun fromString(str: String) : Card{
            val icon = str.substring(0..1).trim()
            val rank = str.substring(1 until str.length).trim()
            return Card(Suit.values().first { it.icon == icon }, Rank.values().first { it.icon == rank })
        }
    }
    override fun toString() = "Card($icon)"

    val icon:String
        get() = "${suit.icon} ${rank.icon}"
}