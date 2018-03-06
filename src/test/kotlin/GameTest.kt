import com.zwolsman.blackjack.Game
import com.zwolsman.blackjack.deck.card.Card
import com.zwolsman.blackjack.deck.card.Card.Companion.BLANK
import com.zwolsman.blackjack.deck.card.Rank
import com.zwolsman.blackjack.deck.card.Suit
import com.zwolsman.blackjack.game.Option
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import sun.plugin.dom.exception.InvalidStateException

class GameTest {

    @Test
    fun `initial game card index`() {
        val game = Game()
        val nextCard = game.nextCard
        assertEquals(Card(Suit.CLUBS, Rank.FOUR), nextCard)
    }

    @Test
    fun `test initial states`() {
        val game = Game()
        assertEquals(1, game.player.size)
        assertEquals(2, game.dealer.cards.size)
        assertEquals(BLANK, game.dealer.cards[1])
        assertEquals(2, game.player[0].cards.size)
    }

    @Test
    fun `player hit success`() {
        val game = Game()
        val nextCard = game.nextCard

        assertEquals(2, game.player[0].cards.size)
        game.player[0].playOption(Option.HIT)
        assertEquals(3, game.player[0].cards.size)
        assertEquals(nextCard, game.player[0].cards.last())
    }

    @Test
    fun `player hit no success`() {
        val game = Game()
        for(i in 0..5) //play 5 turns
            game.player[0].addCard(game.nextCard)
        //Can't hit since points > 21
        assertTrue(game.player[0].points[0] > 21)
        assertThrows(InvalidStateException::class.java) {
            game.player[0].playOption(Option.HIT)
        }
    }

}