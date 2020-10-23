import discord4j.core.DiscordClientBuilder
import io.github.cdimascio.dotenv.dotenv
import listeners.messages.MeowListener
import util.listenForMessage
import util.listenForReady

fun main() {
    val client = DiscordClientBuilder.create(dotenv()["BOT_TOKEN"])
        .build()
        .login()
        .block()

    val messageListeners = listOf(MeowListener())
    client?.apply {
        listenForReady()
        listenForMessage(messageListeners)
        onDisconnect().block()
    }
}
