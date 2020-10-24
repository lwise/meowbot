import com.lwise.alignment.AlignmentDefinitions
import com.lwise.listeners.reactions.AlignmentReactionListener
import org.junit.Assert
import org.junit.Test
import util.MockMeowBot

class ReactionListenerTest {
    @Test
    fun `test AlignmentListener triggers`() {
        val alignmentListener = AlignmentReactionListener()
        val meowBot = MockMeowBot(listOf(alignmentListener))
        AlignmentDefinitions.EMOJI_NAME_LIST.forEach {
            Assert.assertEquals(true, meowBot.firesOnReaction(it))
        }
        Assert.assertEquals(false, meowBot.firesOnReaction("some_other_custom_emoji"))
    }
}
