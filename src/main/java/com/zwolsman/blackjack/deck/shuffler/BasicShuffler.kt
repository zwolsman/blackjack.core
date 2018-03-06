package com.zwolsman.blackjack.deck.shuffler

import com.zwolsman.blackjack.deck.card.Card
import java.util.*

class BasicShuffler(override val seed: Long) : IShuffler {

    private val rng = Random(seed)
    override fun shuffle(cards: ArrayList<Card>) {
        var counter = cards.size
        while(counter > 0) {
            val index = rng.nextInt(counter)
            counter -= 1
            val tmp = cards[counter]
            cards[counter] = cards[index]
            cards[index] = tmp
        }
    }
}