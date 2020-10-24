package com.lwise

import com.lwise.listeners.messages.AlignmentOptInListener
import com.lwise.listeners.messages.MeowListener
import com.lwise.listeners.reactions.AlignmentReactionListener
import com.lwise.util.DatabaseClient
import com.lwise.util.TableTransformer
import com.lwise.util.launchDatabaseSyncRoutine
import com.lwise.util.log
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
    val client = DiscordClientBuilder.create(dotenv["BOT_TOKEN"] ?: System.getenv("BOT_TOKEN"))
        .build()
        .login()
        .block()

    // test the database connection
    val result = DatabaseClient.query("SELECT * FROM pg_catalog.pg_tables;", TableTransformer())
    log("MAIN", result.toString())

    val messageListeners = listOf(MeowListener(), AlignmentOptInListener())
    val reactionListeners = listOf(AlignmentReactionListener())
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
