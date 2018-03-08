package com.zwolsman.blackjack.core

import com.zwolsman.blackjack.core.deck.Deck
import com.zwolsman.blackjack.core.game.Hand
import com.zwolsman.blackjack.core.game.Option
import com.zwolsman.blackjack.core.game.Player
import com.zwolsman.blackjack.core.game.Status
import sun.plugin.dom.exception.InvalidStateException

class Game(seed: Long) {
    constructor(seed: Long, vararg players: Player) : this(seed) {
        players.forEach(::addPlayer)
        start()
    }

    val deck = Deck(1, seed)
    val dealer = Hand()
    val players = mutableListOf<Player>()

    var isStarted: Boolean = false
        private set

    init {
        players.forEach {
            it.gamePlayOption = ::playOption
        }
        dealer.playOption = ::playDealer
    }

    fun start() {
        if (isStarted)
            throw InvalidStateException("Game is already started")
        fillHands(true)
        checkHands()
        isStarted = true
    }

    fun addPlayer(player: Player) {
        player.gamePlayOption = ::playOption
        players.add(player)
    }

    val isFinished: Boolean
        get() =
            !dealer.status.canPlay && players.flatMap { it.hands }.none { it.status.canPlay } && isStarted

    private fun playOption(player: Player, hand: Hand, option: Option) {
        if (!isStarted)
            throw InvalidStateException("Game is not started!")

        when (option) {
            Option.HIT -> {
                hand.addCard(deck.deal())
            }
            Option.STAND -> {
                hand.status = Status.FINISHED
            }
            Option.SPLIT -> {
                val card = hand.cards.removeAt(1)
                player.addHand(Hand(card))
                fillHands()
            }
            Option.DOUBLE -> TODO()
            Option.INSURANCE -> TODO()
        }
        checkHands()
    }

    private fun checkHands() {
        if (players.flatMap { it.hands }.none { it.status.canPlay })
            playDealer()
    }

    private fun fillHands(setup: Boolean = false) {
        while (dealer.cards.size < 2 || players.flatMap { it.hands }.any { it.cards.size < 2 }) {
            for (player in players) {
                for (hand in player.hands) {
                    if (hand.cards.size < 2)
                        hand.addCard(deck.deal())
                }
            }
            if (!setup || dealer.cards.size >= 2)
                continue

            val dealBlank = dealer.cards.size == 1
            dealer.addCard(deck.deal(dealBlank))
        }
    }

    private fun playDealer(hand: Hand, option: Option) {
        when (option) {
            Option.HIT -> {
                hand.addCard(deck.deal())
            }
            else -> throw NotImplementedError("Option $option is not implemented for the dealer")
        }
        checkHands()
    }

    private fun playDealer(stopsAtSoft17: Boolean = true) {
        while (true) {
            val points = if (stopsAtSoft17) {
                dealer.points.last()
            } else {
                dealer.points.first()
            }

            if (dealer.hasBlank) {
                dealer.cards[1] = deck[players.size + 2]
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

