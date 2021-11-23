package com.lwise.listeners.messages

import com.lwise.text.generators.MeowMessageGenerator
import com.lwise.util.AdviceClient
import com.lwise.util.CatFactClient
import com.lwise.util.FortuneClient
import java.util.Locale
import kotlin.random.Random

class AdviceListener : MessageListener {
    override val regexString: String
        get() = "meowbot\\s.*"

    override fun getResponseMessage(): String {
        val random = Random.nextInt(100)
        val advice = when {
            random <= 40 -> FortuneClient.getFortune()
            random >= 80 -> CatFactClient.getCatFact()
            else -> AdviceClient.getAdvice()
        }
        val arguments = mutableListOf<String>()
        val meowMessageGenerator = MeowMessageGenerator()
        if (Random.nextInt(100) > 75) {
            arguments.add(meowMessageGenerator.preMessage[Random.nextInt(meowMessageGenerator.preMessage.size)])
        }
        advice?.let { arguments.add(it.lowercase(Locale.getDefault()).replace("\\n", "").replace("\"", "")) }
        if (Random.nextInt(100) > 75) {
            arguments.add(meowMessageGenerator.postMessage[Random.nextInt(meowMessageGenerator.postMessage.size)])
        }
        return arguments.joinToString(separator = "")
    }
}
