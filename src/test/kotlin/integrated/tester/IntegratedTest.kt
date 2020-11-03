package integrated.tester

import org.junit.jupiter.api.BeforeEach

abstract class IntegratedTest {
    lateinit var testerBot: TesterBot

    @BeforeEach
    fun before() {
        testerBot = TesterBot()
    }
}
