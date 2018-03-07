package com.zwolsman.blackjack.core.game

import com.zwolsman.blackjack.core.deck.card.Card
import com.zwolsman.blackjack.core.deck.card.Rank

enum class Option {
    STAND,
    HIT,
    SPLIT {
        override fun isAvailable(hand: Hand): Boolean {
            if(hand.cards.size != 2)
                return false
            return hand.cards[0].rank == hand.cards[1].rank
        }
    },
    DOUBLE {
        override fun isAvailable(hand: Hand): Boolean {
            return hand.points.any {
                (9..11).contains(it)
            }
        }
    },
    INSURANCE {
        override fun isAvailable(hand: Hand): Boolean {
            if(hand.cards.size != 2 || !hand.hasBlank)
                return false
            return hand.cards[0].rank == Rank.ACE
        }
    };

    open fun isAvailable(hand: Hand) = hand.points.any { it < 21 }
}