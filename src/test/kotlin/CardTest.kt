import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.deck.card.Rank
import com.zwolsman.blackjack.deck.card.Suit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CardTest {
    @Test
    fun `icon matches definition`() {
        val spade10 = Card(Suit.SPADES, Rank.TEN)
        val expectedSpade10 = "♠ 10"
        assertEquals(expectedSpade10, spade10.icon)

        val diamondKing = Card(Suit.DIAMONDS, Rank.KING)
        val expectedDiamondKing = "♦ K"
        assertEquals(expectedDiamondKing, diamondKing.icon)

        val clubsQueen = Card(Suit.CLUBS, Rank.QUEEN)
        val expectedClubsQueen = "♣ Q"
        assertEquals(expectedClubsQueen, clubsQueen.icon)
    }
}