package com.zwolsman.blackjack

import com.zwolsman.blackjack.deck.Deck
import com.zwolsman.blackjack.game.Hand
import com.zwolsman.blackjack.game.Option
import sun.plugin.dom.exception.InvalidStateException

class Game {

    val deck = Deck(1)
    val dealer = Hand(deck.cards[1])
    val player = listOf(Hand(deck.cards[0], deck.cards[2]))

    fun cardIndex() = dealer.cards.size + player.map { it.cards.size }.sum()

    private fun playOption(hand: Hand, option: Option) {
        if(!hand.options.contains(option))
            throw InvalidStateException("Option $option not in available options (${hand.options.map { it.toString() }.joinToString (separator =  ", " )})")

        when(option) {
            Option.HIT -> {
                hand.cards.add(deck[cardIndex()])
            }
            Option.STAND -> TODO()
            Option.SPLIT -> TODO()
            Option.DOUBLE -> TODO()
            Option.INSURANCE -> TODO()
        }
    }

    init {
        player.forEach { it.playOption = ::playOption }
        dealer.playOption = ::playOption
    }
}