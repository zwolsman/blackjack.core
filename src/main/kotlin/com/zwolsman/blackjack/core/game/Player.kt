package com.zwolsman.blackjack.core.game

data class Player(val hands: MutableList<Hand> = mutableListOf(Hand())) {

    var gamePlayOption: ((Player, Hand, Option) -> Unit)? = null

    init {
        hands.forEach(::addHandler)
    }

    fun addHand(hand: Hand) {
        hands.add(hand)
        addHandler(hand)
    }

    private fun addHandler(hand: Hand) {
        hand.playOption = { hand, option ->
            gamePlayOption?.invoke(this, hand, option)
        }
    }
}