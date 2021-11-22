package com.lwise

import com.lwise.ListenerRegistry.messageListeners
import com.lwise.ListenerRegistry.reactionListeners
import com.lwise.util.launchDatabaseSyncRoutine
import com.lwise.util.player.MusicPlayer
import com.lwise.util.subscribeToDatabaseSync
import com.lwise.util.subscribeToMessages
import com.lwise.util.subscribeToReactionAdds
import com.lwise.util.subscribeToReactionRemoves
import com.lwise.util.subscribeToReady
import discord4j.core.DiscordClientBuilder
import io.github.cdimascio.dotenv.dotenv

fun main() {
    val dotenv = dotenv {
        ignoreIfMissing = true
    }

    // This initializes the MusicPlayer; objects are weird
    // This init needs to happen before the client initializes (according to the library creator)
    MusicPlayer

    val client = DiscordClientBuilder.create(dotenv["BOT_TOKEN"] ?: System.getenv("BOT_TOKEN"))
        .build()
        .login()
        .block()

    client?.apply {
        subscribeToReady()
        subscribeToMessages(messageListeners)
        subscribeToReactionAdds(reactionListeners)
        subscribeToReactionRemoves(reactionListeners)
        subscribeToDatabaseSync()
        launchDatabaseSyncRoutine(300000) // 5 minutes
        onDisconnect().block()
    }
}
