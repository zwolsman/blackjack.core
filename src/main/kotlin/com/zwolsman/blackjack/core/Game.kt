package com.zwolsman.blackjack.core

import com.zwolsman.blackjack.core.deck.Deck
import com.zwolsman.blackjack.core.deck.card.Card
import com.zwolsman.blackjack.core.game.Hand
import com.zwolsman.blackjack.core.game.Option
import com.zwolsman.blackjack.core.game.Status

class Game(seed:Long = 0) {
    constructor(seed:Long, vararg handCount:Int) : this(seed) {

        if(handCount.size > 1)
            TODO("Reconstruct game with multiple hands")


        for ((index, h) in handCount.withIndex())
            for(i in 0 until h - 2) {
                player[index].playOption(Option.HIT)
            }
    }

    val deck = Deck(1, seed)
    val dealer = Hand(deck.cards[1], Card.BLANK)
    val player = mutableListOf(Hand(deck.cards[0], deck.cards[2]))

    private val cardIndex:Int
            get() = dealer.cards.size + player.map { it.cards.size }.sum()

    val nextCard: Card
        get() = deck[cardIndex]

    private fun playOption(hand: Hand, option: Option) {
        when(option) {
            Option.HIT -> {
                hand.addCard(nextCard)
            }
            Option.STAND -> {
                hand.status = Status.FINISHED
            }
            Option.SPLIT -> {
                val card = hand.cards.removeAt(1)
                player.add(Hand(card))
                fillHands()
            }
            Option.DOUBLE -> TODO()
            Option.INSURANCE -> TODO()
        }
        if(player.none { it.status.canPlay })
            playDealer()
    }

    private fun fillHands() {
        for(hand in player)
            if(hand.cards.size < 2)
                hand.addCard(nextCard)
    }


    private fun playDealer(stopsAtSoft17:Boolean = true) {
        while (true) {
            val points = if (stopsAtSoft17) {
                dealer.points.last()
            } else {
                dealer.points.first()
            }

            if(dealer.hasBlank) {
                dealer.cards[1] = deck[3]
                continue
            }

            if (points >= 17) {
                if(points <= 21)
                    dealer.status = Status.FINISHED
                break
            }

            dealer.playOption(Option.HIT)
        }
    }

    init {
        player.forEach { it.playOption = ::playOption }
        dealer.playOption = ::playOption
    }

    val isFinished: Boolean
        get() =
            !dealer.status.canPlay && player.none { it.status.canPlay }

    private val Hand.hasBlank: Boolean
        get() = this.cards.any { it == Card.BLANK }
}

