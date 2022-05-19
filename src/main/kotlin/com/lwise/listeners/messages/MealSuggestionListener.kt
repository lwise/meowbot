package com.lwise.listeners.messages

import com.lwise.clients.MealApiClient
import com.lwise.text.generators.MeowMessageGenerator
import java.util.Locale
import kotlin.random.Random

class MealSuggestionListener : MessageListener {
    override val regexString = "meowbot\\s.*\\s(eat|dinner|meal|supper|hungry)(\\s|\\?).*"

    override fun getResponseMessage(): String {
        val meal = MealApiClient.getRandomMeal()
        val arguments = mutableListOf<String>()
        val meowMessageGenerator = MeowMessageGenerator()
        if (Random.nextInt(100) > 75) {
            arguments.add(meowMessageGenerator.preMessage[Random.nextInt(meowMessageGenerator.preMessage.size)])
        }
        meal?.let {
            arguments.add("you should eat ")
            arguments.add(it.lowercase(Locale.getDefault()).replace("\\n", "").replace("\"", ""))
        }
        if (Random.nextInt(100) > 75) {
            arguments.add(meowMessageGenerator.postMessage[Random.nextInt(meowMessageGenerator.postMessage.size)])
        }
        return arguments.joinToString(separator = "")
    }
}
