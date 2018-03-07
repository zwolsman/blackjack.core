package com.zwolsman.blackjack.core.deck.shuffler

import com.zwolsman.blackjack.core.deck.card.Card


interface IShuffler {
    val seed:Long
    fun shuffle(cards:ArrayList<Card>)
}