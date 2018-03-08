import com.zwolsman.blackjack.core.deck.Deck
import com.zwolsman.blackjack.core.deck.card.Card
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.*

class DeckTest : Spek({

    given("a deck") {
        context("of size 1") {
            val deck = Deck(1)
            it("should have 52 cards") {
                assertEquals(52, deck.cards.size)
            }
        }

        context("of size 2") {
            val deck = Deck(2)
            it("should have 104 cards") {
                assertEquals(104, deck.cards.size)
            }
        }

        it("should be iterable") {
            val deck = Deck(1)
            assertTrue(deck is Iterable<Card>)
        }

    }
    given("a shuffled deck") {
        it("should be shuffled") {
            val deck = Deck(1)
            assertNotEquals(deck, Deck.FULL_DECK)
        }
    }

    given("a static deck") {
        val deck = Deck.FULL_DECK

        it("should not be shuffled") {
            val expected = (
                    "♦ 2, ♦ 3, ♦ 4, ♦ 5, ♦ 6, ♦ 7, ♦ 8, ♦ 9, ♦ 10, ♦ J, ♦ Q, ♦ K, ♦ A, " +
                            "♣ 2, ♣ 3, ♣ 4, ♣ 5, ♣ 6, ♣ 7, ♣ 8, ♣ 9, ♣ 10, ♣ J, ♣ Q, ♣ K, ♣ A, " +
                            "♥ 2, ♥ 3, ♥ 4, ♥ 5, ♥ 6, ♥ 7, ♥ 8, ♥ 9, ♥ 10, ♥ J, ♥ Q, ♥ K, ♥ A, " +
                            "♠ 2, ♠ 3, ♠ 4, ♠ 5, ♠ 6, ♠ 7, ♠ 8, ♠ 9, ♠ 10, ♠ J, ♠ Q, ♠ K, ♠ A").toCards()
            assertIterableEquals(expected, deck)
        }
    }
})