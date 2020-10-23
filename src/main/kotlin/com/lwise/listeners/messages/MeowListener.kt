package com.lwise.listeners.messages

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import reactor.core.publisher.Mono

class MeowListener : MessageListener {
    override val regexString = "((\\W|\\b)(meow)+(\\W|\\b))|((\\W|\\b)(nya)+(\\W|\\b))|((\\W|\\b)(miao)+(\\W|\\b))|" +
            "((\\W|\\b)(miaow)+(\\W|\\b))|((\\W|\\b)(miau)+(\\W|\\b))"

    override fun getResponseMessage() : String {
        return "meow!"
    }
}
