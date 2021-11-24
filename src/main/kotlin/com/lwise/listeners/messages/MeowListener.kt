package com.lwise.listeners.messages

import com.lwise.text.generators.MeowMessageGenerator

class MeowListener : MessageListener {

    override val regexString = "(meow|nya|miaou|miao|mew|miau|miaow|mnou)+[^\\w\\s]*$"
    private val messageGenerator = MeowMessageGenerator()

    override fun getResponseMessage(): String {
        return messageGenerator.generateText()
    }
}
