import com.zwolsman.blackjack.deck.Deck
import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.deck.card.Rank
import com.zwolsman.blackjack.deck.card.Suit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DeckTest {
    @Test
    fun `1 deck is 52 cards`() {
        val deck = Deck(1)
        assertEquals(52, deck.cards.size)
    }

    @Test
    fun `2 decks is 104 cards`() {
        val deck = Deck(2)
        assertEquals(104, deck.cards.size)
    }


    @Test
    fun `seed sorts deck`() {
        val deck = Deck(1)
        assertEquals(Card(Suit.SPADES, Rank.TEN), deck.cards[0])
        assertEquals(Card(Suit.CLUBS, Rank.KING), deck.cards[1])
    }
}