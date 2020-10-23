package com.lwise.listeners.messages

class MeowListener : MessageListener {
    override val regexString = "(meow|nya|miaou|miao|mew|miau|miaow)+[^\\w\\s]*$"

    override fun getResponseMessage(): String {
        return "meow!"
    }
}
