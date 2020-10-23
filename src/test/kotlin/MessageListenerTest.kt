import com.lwise.listeners.messages.MeowListener
import org.junit.Assert.assertEquals
import org.junit.Test
import util.MockMeowBot

class MessageListenerTest {
    @Test
    fun `test MeowListener`() {
        val meowListener = MeowListener()
        val meowBot = MockMeowBot(listOf(meowListener))
        assertEquals("meow!", meowBot.getResponseToMessage("meow"))
        assertEquals("meow!", meowBot.getResponseToMessage("nya"))
        assertEquals("meow!", meowBot.getResponseToMessage("nyanya"))
    }
}
