package com.zwolsman.blackjack.game

import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.deck.card.Rank

class Hand(val cards:ArrayList<Card>) {
    constructor(card:Card) : this(arrayListOf(card))
    constructor(cardA:Card, cardB:Card) : this(arrayListOf(cardA, cardB))

    val points:List<Int>
        get() {

            var aces = 0
            var points = 0

            for(card in cards) {
                points += card.rank.points
                if(card.rank == Rank.ACE)
                    aces += 1
            }
            return (0..aces).map {
                points + it * 10
            }
        }

    val options:List<Option>
        get() = Option.values().filter {
            it.isAvailable(this)
        }

    var status = Status.OK

    internal lateinit var playOption:(Hand, Option) -> Unit

    fun playOption(option: Option) = playOption(this, option)

    override fun toString(): String {
        return ("Hand(POINTS=$points, CARDS=[${cards.map { it.icon }.joinToString(separator = ", ")}], OPTIONS=[${options.map { it.toString() }.joinToString(",")}], STATUS=$status)")
    }
}