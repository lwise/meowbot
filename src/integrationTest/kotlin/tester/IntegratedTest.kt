package integrated.tester

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
abstract class IntegratedTest {
    lateinit var testerBot: TesterBot

    @BeforeAll
    fun setup() {
        testerBot = TesterBot()
    }
}
