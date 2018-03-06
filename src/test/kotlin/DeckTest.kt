import com.zwolsman.blackjack.deck.Deck
import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.deck.card.Rank
import com.zwolsman.blackjack.deck.card.Suit
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
        val expected = arrayListOf(Card(Suit.DIAMONDS, Rank.DEUCE), Card(Suit.DIAMONDS, Rank.THREE), Card(Suit.DIAMONDS, Rank.FOUR), Card(Suit.DIAMONDS, Rank.FIVE), Card(Suit.DIAMONDS, Rank.SIX), Card(Suit.DIAMONDS, Rank.SEVEN), Card(Suit.DIAMONDS, Rank.EIGHT), Card(Suit.DIAMONDS, Rank.NINE), Card(Suit.DIAMONDS, Rank.TEN), Card(Suit.DIAMONDS, Rank.JACK), Card(Suit.DIAMONDS, Rank.QUEEN), Card(Suit.DIAMONDS, Rank.KING), Card(Suit.DIAMONDS, Rank.ACE), Card(Suit.CLUBS, Rank.DEUCE), Card(Suit.CLUBS, Rank.THREE), Card(Suit.CLUBS, Rank.FOUR), Card(Suit.CLUBS, Rank.FIVE), Card(Suit.CLUBS, Rank.SIX), Card(Suit.CLUBS, Rank.SEVEN), Card(Suit.CLUBS, Rank.EIGHT), Card(Suit.CLUBS, Rank.NINE), Card(Suit.CLUBS, Rank.TEN), Card(Suit.CLUBS, Rank.JACK), Card(Suit.CLUBS, Rank.QUEEN), Card(Suit.CLUBS, Rank.KING), Card(Suit.CLUBS, Rank.ACE), Card(Suit.HEARTS, Rank.DEUCE), Card(Suit.HEARTS, Rank.THREE), Card(Suit.HEARTS, Rank.FOUR), Card(Suit.HEARTS, Rank.FIVE), Card(Suit.HEARTS, Rank.SIX), Card(Suit.HEARTS, Rank.SEVEN), Card(Suit.HEARTS, Rank.EIGHT), Card(Suit.HEARTS, Rank.NINE), Card(Suit.HEARTS, Rank.TEN), Card(Suit.HEARTS, Rank.JACK), Card(Suit.HEARTS, Rank.QUEEN), Card(Suit.HEARTS, Rank.KING), Card(Suit.HEARTS, Rank.ACE), Card(Suit.SPADES, Rank.DEUCE), Card(Suit.SPADES, Rank.THREE), Card(Suit.SPADES, Rank.FOUR), Card(Suit.SPADES, Rank.FIVE), Card(Suit.SPADES, Rank.SIX), Card(Suit.SPADES, Rank.SEVEN), Card(Suit.SPADES, Rank.EIGHT), Card(Suit.SPADES, Rank.NINE), Card(Suit.SPADES, Rank.TEN), Card(Suit.SPADES, Rank.JACK), Card(Suit.SPADES, Rank.QUEEN), Card(Suit.SPADES, Rank.KING), Card(Suit.SPADES, Rank.ACE))
        assertEquals(expected, cards.toCollection(ArrayList()))
        assertEquals(52, cards.size)
    }
}