package com.zwolsman.blackjack.core.game

import com.zwolsman.blackjack.core.deck.card.Card
import com.zwolsman.blackjack.core.deck.card.Rank
import sun.plugin.dom.exception.InvalidStateException

class Hand(cards:Iterable<Card>) {
    constructor() : this(arrayListOf())
    constructor(vararg cards: Card) : this(cards.asIterable())

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

    val isBlackjack:Boolean
        get() = cards.size == 2 && points.last() == 21

    var status = Status.OK
    val cards:ArrayList<Card> = cards.toCollection(ArrayList())

    internal lateinit var playOption:(Hand, Option) -> Unit

    fun playOption(option: Option) {
        if(!options.contains(option))
            throw InvalidStateException("Option $option not in available options $options")

        playOption(this, option)
    }

    fun addCard(card: Card){
        cards.add(card)
        if(points.first() > 21)
            status = Status.BUSTED
        if(points.first() == 21)
            status = Status.FINISHED
    }


    override fun toString(): String {
        return ("Hand(POINTS=$points, CARDS=[${cards.joinToString {it.icon}}], OPTIONS=$options, STATUS=$status)")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hand

        if (status != other.status) return false
        if (cards != other.cards) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + cards.hashCode()
        return result
    }
}