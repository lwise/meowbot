package com.lwise.listeners.messages

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import reactor.core.publisher.Mono

class MeowListener : MessageListener {
    override val regexString = "(meow|nya|miaou|miao|mew|miau|miaow)+[^\\w\\s]*$"

    override fun getResponseMessage() : String {
        return "meow!"
    }
}
