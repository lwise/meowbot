package integrated

import integrated.tester.IntegratedTest
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.AnyOf
import org.hamcrest.core.StringContains
import org.junit.jupiter.api.Test

class MeowListenerTest : IntegratedTest() {

    private val meowMatcher: Matcher<String?> = AnyOf.anyOf(
        StringContains.containsString("meow"),
        StringContains.containsString("nya"),
        StringContains.containsString("miaou"),
        StringContains.containsString("miao"),
        StringContains.containsString("mew"),
        StringContains.containsString("miau"),
        StringContains.containsString("miaow"),
        StringContains.containsString("mnou")
    )

    @Test
    fun `MeowBot responds with a meow when meowed at`() {
        testerBot.sendMessage("meow")
        assertThat(testerBot.getLastMessage(), meowMatcher)
    }

    @Test
    fun `MeowBot responds to meows with symbols at the end`() {
        testerBot.sendMessage("meow~~~~")
        assertThat(testerBot.getLastMessage(), meowMatcher)
    }

    @Test
    fun `MeowBot responds to multiple meows`() {
        testerBot.sendMessage("nya nya nya")
        assertThat(testerBot.getLastMessage(), meowMatcher)
    }

    @Test
    fun `MeowBot responds to a message with a meow at the end`() {
        testerBot.sendMessage("help I am sad meow")
        assertThat(testerBot.getLastMessage(), meowMatcher)
    }
}
