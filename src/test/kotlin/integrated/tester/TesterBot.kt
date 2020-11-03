package integrated.tester

import discord4j.common.util.Snowflake
import discord4j.core.DiscordClientBuilder
import discord4j.core.`object`.entity.channel.MessageChannel
import io.github.cdimascio.dotenv.dotenv

class TesterBot {
    private val client = DiscordClientBuilder.create(dotenv { ignoreIfMissing = true }["TESTER_BOT_TOKEN"] ?: System.getenv("TESTER_BOT_TOKEN"))
        .build()
        .login()
        .block()
    private val testChannelId: Snowflake = Snowflake.of(dotenv { ignoreIfMissing = true }["TEST_CHANNEL_ID"])

    fun sendMessage(message: String) {
        (client!!.getChannelById(testChannelId).block() as MessageChannel).createMessage(message).block()
    }

    fun getLastMessage(): String? {
        val message = (client!!.getChannelById(testChannelId).block() as MessageChannel).lastMessage.block()!!
        return if (message.author.get().id == client.selfId) null else message.content
    }
}
