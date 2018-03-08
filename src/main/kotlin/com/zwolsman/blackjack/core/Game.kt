package com.zwolsman.blackjack.core

import com.zwolsman.blackjack.core.deck.Deck
import com.zwolsman.blackjack.core.game.Hand
import com.zwolsman.blackjack.core.game.Option
import com.zwolsman.blackjack.core.game.Status

class Game(seed: Long = 0) {
    //Reconstruct game from seed
    constructor(seed: Long, vararg handCount: Int) : this(seed) {

        if (handCount.size > 1)
            TODO("Reconstruct game with multiple hands")


        for ((index, h) in handCount.withIndex())
            for (i in 0 until h - 2) {
                hands[index].playOption(Option.HIT)
            }
    }

    val deck = Deck(1, seed)
    val dealer = Hand()
    val hands = mutableListOf(Hand())

    init {
        hands.forEach { it.playOption = ::playOption }
        dealer.playOption = ::playOption
        fillHands(true)
        checkHands()
    }

    val isFinished: Boolean
        get() =
            !dealer.status.canPlay && hands.none { it.status.canPlay }

    private fun playOption(hand: Hand, option: Option) {
        when (option) {
            Option.HIT -> {
                hand.addCard(deck.deal())
            }
            Option.STAND -> {
                hand.status = Status.FINISHED
            }
            Option.SPLIT -> {
                val card = hand.cards.removeAt(1)
                val newHand = Hand(card)
                newHand.playOption = ::playOption
                hands.add(newHand)
                fillHands()
            }
            Option.DOUBLE -> TODO()
            Option.INSURANCE -> TODO()
        }
        checkHands()
    }

    private fun checkHands() {
        if (hands.none { it.status.canPlay })
            playDealer()
    }

    private fun fillHands(setup: Boolean = false) {
        while (dealer.cards.size < 2 || hands.any { it.cards.size < 2 }) {
            for (hand in hands) {
                if (hand.cards.size < 2)
                    hand.addCard(deck.deal())
            }
            if (!setup || dealer.cards.size >= 2)
                continue

            val dealBlank = dealer.cards.size == 1
            dealer.addCard(deck.deal(dealBlank))
        }
    }

    private fun playDealer(stopsAtSoft17: Boolean = true) {
        while (true) {
            val points = if (stopsAtSoft17) {
                dealer.points.last()
            } else {
                dealer.points.first()
            }

            if (dealer.hasBlank) {
                dealer.cards[1] = deck[3]
                continue
            }

            if (points >= 17) {
                if (points <= 21)
                    dealer.status = Status.FINISHED
                break
            }

            dealer.playOption(Option.HIT)
        }
    }

}

