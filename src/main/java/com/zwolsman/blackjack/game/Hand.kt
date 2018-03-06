package com.zwolsman.blackjack.game

import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.deck.card.Rank
import sun.plugin.dom.exception.InvalidStateException
import java.util.Collections.unmodifiableList

class Hand(cards:Iterable<Card>) {
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
            return (0..aces).mapNotNull {
                val p = points + it * 10
                if(it == 0 || p <= 21)
                    p
                else
                null
            }
        }

    val options:List<Option>
        get() = Option.values().filter {
            it.isAvailable(this)
        }

    var status = Status.OK
    val cards:ArrayList<Card> = cards.toCollection(ArrayList())

    internal lateinit var playOption:(Hand, Option) -> Unit

    fun playOption(option: Option) {
        if(!options.contains(option))
            throw InvalidStateException("Option $option not in available options $options")

        playOption(this, option)
    }

    fun addCard(card:Card){
        cards.add(card)
        if(points.first() > 21)
            status = Status.BUSTED
        if(points.first() == 21)
            status = Status.FINISHED
    }

    override fun toString(): String {
        return ("Hand(POINTS=$points, CARDS=[${cards.joinToString {it.icon}}], OPTIONS=$options, STATUS=$status)")
    }
}