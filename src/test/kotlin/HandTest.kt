import com.zwolsman.blackjack.game.Hand
import com.zwolsman.blackjack.game.Option
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import sun.plugin.dom.exception.InvalidStateException

class HandTest {

    private val ace = "- A".toCard()
    private val five = "- 5".toCard()
    private val six = "- 6".toCard()
    private val king = "- K".toCard()

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

    @Test
    fun `Can't hit when above 21`() {
        val cards = "♣ 4, ♦ A, ♣ 6, ♦ 3".toCards()
        val hand = Hand(cards)
        assertTrue(Option.HIT.isAvailable(hand))
        hand.playOption = {h, option ->
            if(option == Option.HIT)
                h.cards.add(king)
        }
        hand.playOption(Option.HIT)

        assertFalse(Option.HIT.isAvailable(hand))
        assertThrows(InvalidStateException::class.java) {
            hand.playOption(Option.HIT)
        }
    }
}