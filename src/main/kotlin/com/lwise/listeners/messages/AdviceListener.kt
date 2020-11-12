package com.lwise.listeners.messages

import com.lwise.text.generators.MeowMessageGenerator
import com.lwise.util.AdviceClient
import com.lwise.util.CatFactClient
import kotlin.random.Random

class AdviceListener : MessageListener {
    override val regexString: String
        get() = "meowbot\\s.*"

    override fun getResponseMessage(): String {
        val advice = if (Random.nextInt(100) > 45) AdviceClient.getAdvice() else CatFactClient.getCatFact()
        val arguments = mutableListOf<String>()
        val meowMessageGenerator = MeowMessageGenerator()
        if (Random.nextInt(100) > 75) {
            arguments.add(meowMessageGenerator.preMessage[Random.nextInt(meowMessageGenerator.preMessage.size)])
        }
        advice?.let { arguments.add(it.toLowerCase()) }
        if (Random.nextInt(100) > 75) {
            arguments.add(meowMessageGenerator.postMessage[Random.nextInt(meowMessageGenerator.postMessage.size)])
        }
        return arguments.joinToString(separator = "")
    }
}
