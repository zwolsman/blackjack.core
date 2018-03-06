package com.zwolsman.blackjack.deck

import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.deck.card.Rank
import com.zwolsman.blackjack.deck.card.Suit
import com.zwolsman.blackjack.deck.shuffler.BasicShuffler
import com.zwolsman.blackjack.deck.shuffler.IShuffler

class Deck(decks: Int, seed: Long = 0) : Iterable<Card> {
    private val shuffler: IShuffler = BasicShuffler(seed)

    companion object {
        val FULL_DECK =
                Suit.values().flatMap {suit ->
                    Rank.values().map {rank ->
                        Card(suit, rank)
                    }
                }.toTypedArray()
        }


    var cards:ArrayList<Card>
        private set

    init {
        cards = Array(decks * FULL_DECK.size) {
            FULL_DECK[it % FULL_DECK.size]
        }.toCollection(ArrayList())
        shuffler.shuffle(cards)
    }

    override fun iterator() = cards.iterator()
    operator fun get(index: Int): Card = cards[index]

    override fun toString(): String {
        return "DECK(CARDS=[${cards.map { it.toString() }.joinToString(separator = ", ")}])"
    }
}