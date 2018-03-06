import com.zwolsman.blackjack.Game
import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.deck.card.Rank
import com.zwolsman.blackjack.deck.card.Suit
import com.zwolsman.blackjack.game.Option
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream


class DealerTest {
    companion object {
        val rng = Random(0)
        @JvmStatic
        fun createSeeds() : Stream<Long> =
            Stream.iterate(0) {
                it + 1
            }.limit(10).map {
                rng.nextLong()
            }
        }

    @Test
    fun `dealers hits with 0 seed`() {
        val game = Game()
        //Stand on hand so the dealer plays
        game.player[0].playOption(Option.STAND)

        val expectedCards = arrayListOf(Card(Suit.CLUBS, Rank.KING), Card(Suit.HEARTS, Rank.QUEEN))
        val expectedPoints = 20
        assertEquals(expectedCards, game.dealer.cards)
        assertEquals(listOf(expectedPoints), game.dealer.points)
    }

    @ParameterizedTest
    @MethodSource("createSeeds")
    fun `dealers hits random seed`(seed:Long) {
        val game = Game(seed)
        val before = game.dealer.points
        game.player[0].playOption(Option.STAND)
        val after = game.dealer.points

        assertNotSame(after, before)
        assertTrue(game.dealer.points.last() >= 17)
        assertTrue(game.isFinished)
    }
}