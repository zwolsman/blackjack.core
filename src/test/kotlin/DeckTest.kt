import com.zwolsman.blackjack.core.deck.Deck
import com.zwolsman.blackjack.core.deck.card.Card
import com.zwolsman.blackjack.core.deck.card.Rank
import com.zwolsman.blackjack.core.deck.card.Suit
import org.junit.jupiter.api.Assertions.*
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

    @Test
    fun `deck is iterable`() {
        val deck = Deck(1)
        assertTrue(deck.iterator().hasNext())
        assertIterableEquals(deck.cards, deck)
    }

    @Test
    fun `deck index get works`() {
        val deck = Deck(1)
        val expected = Card(Suit.SPADES, Rank.TEN)
        assertEquals(expected, deck.get(0))
        assertEquals(expected, deck[0])
    }
    @Test
    fun `full deck = 52 cards`() {
        val cards = Deck.FULL_DECK
        val expected = (
                "♦ 2, ♦ 3, ♦ 4, ♦ 5, ♦ 6, ♦ 7, ♦ 8, ♦ 9, ♦ 10, ♦ J, ♦ Q, ♦ K, ♦ A, " +
                "♣ 2, ♣ 3, ♣ 4, ♣ 5, ♣ 6, ♣ 7, ♣ 8, ♣ 9, ♣ 10, ♣ J, ♣ Q, ♣ K, ♣ A, " +
                "♥ 2, ♥ 3, ♥ 4, ♥ 5, ♥ 6, ♥ 7, ♥ 8, ♥ 9, ♥ 10, ♥ J, ♥ Q, ♥ K, ♥ A, " +
                "♠ 2, ♠ 3, ♠ 4, ♠ 5, ♠ 6, ♠ 7, ♠ 8, ♠ 9, ♠ 10, ♠ J, ♠ Q, ♠ K, ♠ A").toCards()
        assertEquals(expected, cards.toCollection(ArrayList()))
        assertEquals(52, cards.size)
    }
}