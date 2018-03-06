package com.zwolsman.blackjack

import com.zwolsman.blackjack.deck.Deck
import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.game.Hand
import com.zwolsman.blackjack.game.Option
import com.zwolsman.blackjack.game.Status

class Game(seed:Long = 0) {

    val deck = Deck(1, seed)
    val dealer = Hand(deck.cards[1], Card.BLANK)
    val player = listOf(Hand(deck.cards[0], deck.cards[2]))

    private val cardIndex:Int
            get() = dealer.cards.size + player.map { it.cards.size }.sum()

    val nextCard:Card
        get() = deck[cardIndex]

    private fun playOption(hand: Hand, option: Option) {
        when(option) {
            Option.HIT -> {
                hand.cards.add(nextCard)
            }
            Option.STAND -> {
                hand.status = Status.FINISHED
                if(player.none { it.status.canPlay })
                    playDealer()
            }
            Option.SPLIT -> TODO()
            Option.DOUBLE -> TODO()
            Option.INSURANCE -> TODO()
        }
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

