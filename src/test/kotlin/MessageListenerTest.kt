import com.lwise.listeners.messages.MeowListener
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.AnyOf.anyOf
import org.hamcrest.core.StringContains.containsString
import org.junit.Test
import util.MockMeowBot

class MessageListenerTest {

    val meowMatcher: Matcher<String?> = anyOf(
        containsString("meow"),
        containsString("nya"),
        containsString("miaou"),
        containsString("miao"),
        containsString("mew"),
        containsString("miau"),
        containsString("miaow"),
        containsString("mnou")
    )

    @Test
    fun `test MeowListener`() {
        val meowListener = MeowListener()
        val meowBot = MockMeowBot(listOf(meowListener))

        assertThat(meowBot.getResponseToMessage("meow"), meowMatcher)
        assertThat(meowBot.getResponseToMessage("nya"), meowMatcher)
        assertThat(meowBot.getResponseToMessage("nyanya"), meowMatcher)
    }
}
