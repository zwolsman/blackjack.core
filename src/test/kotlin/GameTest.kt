import com.zwolsman.blackjack.core.Game
import com.zwolsman.blackjack.core.deck.Deck
import com.zwolsman.blackjack.core.deck.card.Card
import com.zwolsman.blackjack.core.game.Hand
import com.zwolsman.blackjack.core.game.Option
import com.zwolsman.blackjack.core.game.Status
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.*

class GameTest : Spek({

    given("a game") {
        val seed1: Long = 0
        context("seed $seed1") {
            val game = Game(seed1)
            it("should be finished") {
                assertTrue(game.isFinished)
            }

            context("hands") {
                it("should be 1") {
                    assertEquals(1, game.hands.size)
                }

                context("hand 0") {
                    it("has 2 cards") {
                        assertEquals(2, game.hands[0].cards.size)
                    }
                    it("is a blackjack") {
                        assertTrue(game.hands[0].isBlackjack)
                    }
                    it("is a blackjack") {
                        assertTrue(game.hands[0].isBlackjack)
                    }
                }
            }
            context("dealer") {
                it("has no blanks") {
                    assertFalse(game.dealer.hasBlank)
                }
                it("is finished") {
                    assertEquals(game.dealer.status, Status.FINISHED)
                }
            }
        }

        val seed2 = 6937158363516017698
        context("seed $seed2") {
            val game = Game(seed2)
            it("has the option to split") {
                assertTrue(Option.SPLIT.isAvailable(game.hands[0]))
            }
            it("has 1 hand") {
                assertEquals(1, game.hands.size)
            }
            it("splits hand") {
                assertEquals(1, game.hands.size)
                game.hands[0].playOption(Option.SPLIT)
                assertEquals(2, game.hands.size)
                val h1 = Hand("♣ 5, ♥ A".toCards())
                val h2 = Hand("♥ 5, ♣ J".toCards())
                assertIterableEquals(listOf(h1, h2), game.hands)
            }
        }

        val seed3 = 5631132222
        context("seed $seed3") {
            val game = Game(seed3)
            it("adds card to hand") {
                val nextCard = game.deck.next

                assertEquals(2, game.hands[0].cards.size)
                game.hands[0].playOption(Option.HIT)
                assertEquals(3, game.hands[0].cards.size)
                assertEquals(nextCard, game.hands[0].cards.last())
            }
        }

        val seed4 = 98776545890
        context("seed $seed4") {
            val game = Game(seed4)
            it("should let the dealer play if user busts") {
                assertTrue(game.dealer.hasBlank)
                while (game.hands[0].status != Status.BUSTED)
                    game.hands[0].playOption(Option.HIT)

                assertFalse(game.dealer.hasBlank)
                assertNotEquals(Status.OK, game.dealer.status)
                assertTrue(game.isFinished)
            }
        }
    }

    given("a seed and hand count") {
        data class TestData(val seed: Long, val expectedHand: String, val expectedDealer: String) {
            val cardCount: Int
                get() = expectedHand.toCards().size
            val dealerCount: Int
                get() = expectedDealer.toCards().size
        }

        listOf(
                TestData(7903218775403745707, "♦ 10, ♦ J", "♦ 7, - -"),
                TestData(-2061957228015400700, "♠ 4, ♠ 10, ♦ 2", "♦ 6, - -")
        ).forEach {
            context("seed ${it.seed}, card count: ${it.cardCount}") {
                val game = Game(it.seed, it.cardCount)
                context("player") {
                    it("has ${it.cardCount} cards in hand") {
                        assertEquals(it.cardCount, game.hands[0].cards.size)
                    }
                }
                context("dealer") {
                    it("has ${it.dealerCount} cards in hand") {
                        assertEquals(it.dealerCount, game.dealer.cards.size)
                    }
                    it("has ${it.expectedDealer}") {
                        assertIterableEquals(it.expectedDealer.toCards(), game.dealer.cards)
                    }
                }
            }
        }

    }
})

private val Deck.next: Card
    get() = this.first()
