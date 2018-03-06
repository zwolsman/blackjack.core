package com.zwolsman.blackjack.deck.shuffler

import com.zwolsman.blackjack.deck.card.Card


interface IShuffler {
    val seed:Long
    fun shuffle(cards:ArrayList<Card>)
}