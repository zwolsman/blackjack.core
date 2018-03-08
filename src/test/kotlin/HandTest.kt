import com.zwolsman.blackjack.core.game.Hand
import com.zwolsman.blackjack.core.game.Option
import com.zwolsman.blackjack.core.game.Status
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.*
import sun.plugin.dom.exception.InvalidStateException


class HandTest : Spek({

    given("a hand") {
        val cards1 = "♦ A, ♣ 5"
        context("cards $cards1") {
            it("should be 6 or 16 points") {
                val hand = Hand(cards1.toCards())
                val expected = listOf(6, 16)
                assertEquals(expected, hand.points)
            }
        }

        val cards2 = "♣ A, ♣ A, ♦ 5"
        context("cards $cards2") {
            it("should be 7 or 17 points") {
                val hand = Hand(cards2.toCards())
                val expected = listOf(7, 17)
                assertEquals(expected, hand.points)
            }
        }
        val cards3 = "♦ K"
        context("cards $cards3") {
            it("should be 10 points") {
                val hand = Hand(cards3.toCards())
                val expected = listOf(10)
                assertEquals(expected, hand.points)
            }
        }

        val cards4 = "♣ 5, ♦ 5"
        context("cards $cards4") {
            it("can split") {
                val hand = Hand(cards4.toCards())
                assertTrue(hand.options.contains(Option.SPLIT))
            }
            it("can double down") {
                val hand = Hand(cards4.toCards())
                assertTrue(hand.options.contains(Option.DOUBLE))
            }
            it("can hit") {
                val hand = Hand(cards4.toCards())
                assertTrue(hand.options.contains(Option.HIT))
            }
            it("should be 10 points") {
                val hand = Hand(cards4.toCards())
                assertEquals(listOf(10), hand.points)
            }
        }

        val cards5 = "♣ 4, ♦ A, ♣ 6, ♦ 3, ♦ K"
        context("cards $cards5") {
            it("can't hit above 21") {
                val hand = Hand(cards5.toCards())
                assertFalse(hand.options.contains(Option.HIT))
            }

            it("should throw when trying to hit when it's not an option") {
                val hand = Hand(cards5.toCards())
                assertThrows(InvalidStateException::class.java) {
                    hand.playOption(Option.HIT)
                }
            }

            it("should be 25 points") {
                val hand = Hand(cards5.toCards())
                assertEquals(listOf(24), hand.points)
            }
        }
        val blackjacks = listOf(
                "♦ A, ♦ K",
                "♦ A, ♦ Q",
                "♦ A, ♦ J",
                "♦ A, ♦ 10"
        )
        blackjacks.forEach {
            context("cards $it") {
                val hand = Hand(it.toCards())
                it("should be a blackjack") {
                    assertTrue(hand.isBlackjack)
                }
                it("should be 21 points") {
                    assertEquals(listOf(21), hand.points)
                }
                it("should have finished") {
                    assertEquals(hand.status, Status.FINISHED)
                }
            }
        }

        val cards6 = "♦ A, - -"
        context("cards $cards6") {
            it("could close insurance") {
                val hand = Hand(cards6.toCards())
                assertTrue(hand.options.contains(Option.INSURANCE))
            }
        }
    }
})