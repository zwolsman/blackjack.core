package com.zwolsman.blackjack.core.deck

import com.zwolsman.blackjack.core.deck.card.Card
import com.zwolsman.blackjack.core.deck.card.Rank
import com.zwolsman.blackjack.core.deck.card.Suit
import com.zwolsman.blackjack.core.deck.shuffler.BasicShuffler
import com.zwolsman.blackjack.core.deck.shuffler.IShuffler

class Deck(decks: Int, val seed: Long = 0) : Iterable<Card> {
    private val shuffler: IShuffler = BasicShuffler(seed)

    companion object {
        val FULL_DECK = {
            val suits = Suit.values().filter { it != Suit.NONE }
            val ranks = Rank.values().filter { it != Rank.NONE }
            suits.flatMap { suit ->
                ranks.map { rank ->
                    Card(suit, rank)
                }
            }
        }()
    }


    var cards: ArrayList<Card>
        private set

    init {
        cards = Array(decks * FULL_DECK.size) {
            FULL_DECK[it % FULL_DECK.size]
        }.toCollection(ArrayList())
        shuffler.shuffle(cards)
    }

    override fun iterator() = cards.stream().skip(index.toLong()).iterator()!!
    operator fun get(index: Int): Card = cards[index]

    private var index = 0

    fun deal(blank: Boolean = false) = if (blank) {
        index++
        Card.BLANK
    } else {
        cards[index++]
    }

    override fun toString(): String {
        return "DECK(${cards.joinToString { it.icon }})"
    }
}