package com.zwolsman.blackjack.deck.card

data class Card(val suit: Suit, val rank: Rank) {
    override fun toString(): String {
        return "Card($icon)"
    }

    val icon:String
        get() = "${suit.icon} ${rank.icon}"
}