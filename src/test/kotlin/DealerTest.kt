import com.zwolsman.blackjack.core.Game
import com.zwolsman.blackjack.core.game.Option
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.*

class DealerTest : Spek({
    context("static seed") {
        given("a game with seed 1") {
            with("seed 0") {
                val game = Game(1)
                on("player stand") {
                    game.players[0].hands[0].playOption(Option.STAND)
                    it("should finish the game and have 20 points") {
                        val expectedCards = "♥ 2, ♥ 8, ♥ 4, ♥ 5".toCards()
                        val expectedPoints = 19
                        assertIterableEquals(expectedCards, game.dealer.cards)
                        assertEquals(listOf(expectedPoints), game.dealer.points)
                        assertTrue(game.isFinished)
                    }
                }
            }
        }
    }
    context("random seed") {
        val seed: Long = 10
        given("a game with seed $seed") {
            val game = Game(seed)
            on("player stand") {
                game.players[0].hands[0].playOption(Option.STAND)
                it("should finish the game") {
                    assertTrue(game.isFinished)
                }
                it("shouldn't have a blank card") {
                    assertFalse(game.dealer.hasBlank)
                }
            }
        }
    }
})