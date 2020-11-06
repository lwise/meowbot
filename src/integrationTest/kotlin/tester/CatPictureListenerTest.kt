package integrated.tester

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.StringContains
import org.junit.jupiter.api.Test

class CatPictureListenerTest : IntegratedTest() {
    @Test
    fun `MeowBot responds to the command m!pic with a cat picture from the cat api`() {
        testerBot.sendMessage("m!pic")
        assertThat(testerBot.getLastMessage(1000), StringContains.containsString("thecatapi.com"))
    }
}
