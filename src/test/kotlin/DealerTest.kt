import com.zwolsman.blackjack.Game
import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.deck.card.Rank
import com.zwolsman.blackjack.deck.card.Suit
import com.zwolsman.blackjack.game.Option
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DealerTest {

    @Test
    fun `dealers hits`() {
        val game = Game()
        //Stand on hand so the dealer plays
        game.player[0].playOption(Option.STAND)

        val expectedCards = arrayListOf(Card(Suit.CLUBS, Rank.KING), Card(Suit.HEARTS, Rank.QUEEN))
        val expectedPoints = 20
        assertEquals(expectedCards, game.dealer.cards)
        assertEquals(listOf(expectedPoints), game.dealer.points)
    }


}