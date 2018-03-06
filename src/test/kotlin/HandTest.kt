import com.zwolsman.blackjack.deck.Deck
import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.deck.card.Rank
import com.zwolsman.blackjack.deck.card.Suit
import com.zwolsman.blackjack.game.Hand
import com.zwolsman.blackjack.game.Option
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class HandTest {

    val ace = Card(Suit.CLUBS, Rank.ACE)
    val five = Card(Suit.CLUBS, Rank.FIVE)
    val six = Card(Suit.CLUBS, Rank.SIX)
    val king = Card(Suit.DIAMONDS, Rank.KING)

    @Test
    fun `ace + five`() {
        val hand = Hand(ace, five)
        val expected = listOf(6, 16)
        assertEquals(expected, hand.points)
    }

    @Test
    fun `ace + ace + five`() {
        val hand = Hand(arrayListOf(ace, ace,five))
        val expected = listOf(7,17)
        assertEquals(expected, hand.points)
    }

    @Test
    fun king() {
        val hand = Hand(king)
        val expected = listOf(10)
        assertEquals(expected, hand.points)
    }

    @Test
    fun `can split`() {
        val hand = Hand(five, five)
        assertTrue(hand.options.contains(Option.SPLIT))
    }

    @Test
    fun `can hit`() {
        val hand = Hand(five, five)
        assertTrue(hand.options.contains(Option.HIT))
    }

    @Test
    fun `can double down`() {
        val hand = Hand(five, six)
        assertTrue(hand.options.contains(Option.DOUBLE))
    }

    @Test
    fun `can't double down`() {
        val hand = Hand(five, king)
        assertFalse(hand.options.contains(Option.DOUBLE))
    }

}