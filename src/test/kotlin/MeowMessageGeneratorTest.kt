import com.lwise.text.generators.MeowMessageGenerator
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert
import org.hamcrest.core.AnyOf
import org.hamcrest.core.StringContains
import org.junit.jupiter.api.Test

class MeowMessageGeneratorTest {
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
    fun `the MeowMessageGenerator generates a message with a meow in it`() {
        MatcherAssert.assertThat(MeowMessageGenerator().generateText(), meowMatcher)
    }
}
