package com.zwolsman.blackjack

import com.zwolsman.blackjack.deck.Deck
import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.game.Hand
import com.zwolsman.blackjack.game.Option
import com.zwolsman.blackjack.game.Status
import sun.plugin.dom.exception.InvalidStateException

class Game(seed:Long = 0) {

    val deck = Deck(1, seed)
    val dealer = Hand(deck.cards[1], Card.BLANK)
    val player = listOf(Hand(deck.cards[0], deck.cards[2]))
    private val stopsAtSoft17 = true
    fun cardIndex() = dealer.cards.size + player.map { it.cards.size }.sum()

    private fun playOption(hand: Hand, option: Option) {
        when(option) {
            Option.HIT -> {
                hand.cards.add(deck[cardIndex()])
                if(hand.points[0] > 21)
                    hand.status = Status.BUSTED
            }
            Option.STAND -> {
                hand.status = Status.FINISHED
                if(player.all { it.status == Status.FINISHED || it.status == Status.BUSTED })
                    playDealer()
            }
            Option.SPLIT -> TODO()
            Option.DOUBLE -> TODO()
            Option.INSURANCE -> TODO()
        }
    }


    private fun playDealer() {
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

            if ((17..21).contains(points)) {
                dealer.status = Status.FINISHED
                break
            } else if(points > 21)
            {
                dealer.status = Status.BUSTED
                break
            } else if (points >= 17) {
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
            (dealer.status == Status.FINISHED || dealer.status == Status.BUSTED) &&
                    player.all { it.status == Status.FINISHED || it.status == Status.BUSTED }

    private val Hand.hasBlank: Boolean
        get() = this.cards.any { it == Card.BLANK }
}

