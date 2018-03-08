package com.zwolsman.blackjack.core.game

import com.zwolsman.blackjack.core.deck.card.Card
import com.zwolsman.blackjack.core.deck.card.Rank
import sun.plugin.dom.exception.InvalidStateException

class Hand(cards: Iterable<Card>) {
    constructor() : this(arrayListOf())
    constructor(vararg cards: Card) : this(cards.asIterable())

    val points: List<Int>
        get() {
            val aces = cards.count { it.rank == Rank.ACE }
            val basePoints = cards.sumBy { it.rank.points }
            val options = (0..aces).map { basePoints + it * 10 }.filterIndexed { index, i ->
                index == 0 || i <= 21
            }
            return if (options.any { it == 21 }) listOf(21) else options
        }

    val options: List<Option>
        get() = Option.values().filter {
            it.isAvailable(this)
        }

    val isBlackjack: Boolean
        get() = cards.size == 2 && points.any { it == 21 }

    internal val hasBlank: Boolean
        get() = this.cards.any { it == Card.BLANK }

    var status = Status.OK

    val cards: ArrayList<Card> = cards.toCollection(ArrayList())

    init {
        if (points.first() > 21)
            status = Status.BUSTED
        if (points.any { it == 21 })
            status = Status.FINISHED
    }

    internal var playOption: ((Hand, Option) -> Unit)? = null

    fun playOption(option: Option) {
        if (!options.contains(option))
            throw InvalidStateException("Option $option not in available options $options")

        playOption?.invoke(this, option)
    }

    fun addCard(card: Card) {
        cards.add(card)
        if (points.first() > 21)
            status = Status.BUSTED
        if (points.any { it == 21 })
            status = Status.FINISHED
    }

    override fun toString(): String {
        return ("Hand(POINTS=$points, CARDS=[${cards.joinToString { it.icon }}], OPTIONS=$options, STATUS=$status)")
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