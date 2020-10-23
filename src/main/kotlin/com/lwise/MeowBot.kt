package com.lwise

import discord4j.core.DiscordClientBuilder
import io.github.cdimascio.dotenv.dotenv
import com.lwise.listeners.messages.MeowListener
import com.lwise.util.subscribeToMessages
import com.lwise.util.subscribeToReady

fun main() {
    val dotenv = dotenv {
        ignoreIfMissing = true
    }
    val client = DiscordClientBuilder.create(dotenv["BOT_TOKEN"] ?: System.getenv("BOT_TOKEN"))
        .build()
        .login()
        .block()

    val messageListeners = listOf(MeowListener())
    client?.apply {
        subscribeToReady()
        subscribeToMessages(messageListeners)
        onDisconnect().block()
    }
}
