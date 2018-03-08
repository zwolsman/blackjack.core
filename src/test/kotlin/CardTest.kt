import com.zwolsman.blackjack.core.deck.card.Card
import com.zwolsman.blackjack.core.deck.card.Rank
import com.zwolsman.blackjack.core.deck.card.Suit
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CardTest : Spek({
    given("a card") {
        on("creation of ten of spades") {
            it("should have the icon ♠ 10") {
                val expected = "♠ 10"
                val spade10 = Card(Suit.SPADES, Rank.TEN)
                assertEquals(expected, spade10.icon)
            }
        }
        on("creation of clubs queen") {
            it("should have the icon ♣ Q") {
                val expected = "♣ Q"
                val clubsQueen = Card(Suit.CLUBS, Rank.QUEEN)
                assertEquals(expected, clubsQueen.icon)
            }
        }
        on("creation of a diamonds king") {
            it("should have the icon ♦ K") {
                val expected = "♦ K"
                val diamondKing = Card(Suit.DIAMONDS, Rank.KING)
                assertEquals(expected, diamondKing.icon)
            }
        }

    }
})