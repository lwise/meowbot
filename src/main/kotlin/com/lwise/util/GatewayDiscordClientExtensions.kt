package com.lwise.util

import com.lwise.alignment.AlignmentDefinitions
import com.lwise.clients.DatabaseClient
import com.lwise.listeners.messages.MessageListener
import com.lwise.listeners.reactions.ReactionListener
import com.lwise.transformers.UserDataListTransformer
import com.lwise.types.events.DatabaseSyncEvent
import com.lwise.types.events.toMessageEvent
import com.lwise.types.events.toReactionAddEvent
import com.lwise.types.events.toReactionRemoveEvent
import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.message.ReactionAddEvent
import discord4j.core.event.domain.message.ReactionRemoveEvent
import discord4j.gateway.ShardInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import reactor.core.publisher.Mono
import kotlin.String

fun GatewayDiscordClient.subscribeToReady() {
    eventDispatcher.on(ReadyEvent::class.java).subscribe { event: ReadyEvent ->
        event.self.let {
            log(this.javaClass.name, String.format("Logged in as %s#%s", it.username, it.discriminator))
        }
    }
}

fun GatewayDiscordClient.subscribeToMessages(listeners: List<MessageListener>) {
    var listener: MessageListener? = null
    eventDispatcher.on(MessageCreateEvent::class.java)
        .filter { event ->
            // don't respond to messages from self
            event.message.author.map { it.id != selfId }.orElse(false)
        }
        .filter { event ->
            listener = listeners.firstOrNull { it.isTriggered(event.message.content) }
            listener != null
        }
        .map(MessageCreateEvent::toMessageEvent)
        .flatMap {
            listener?.respond(it)
        }
        .subscribe()
}

fun GatewayDiscordClient.subscribeToReactionAdds(listeners: List<ReactionListener>) {
    var listener: ReactionListener? = null
    eventDispatcher.on(ReactionAddEvent::class.java)
        .filter { it.emoji.asCustomEmoji().isPresent || it.emoji.asUnicodeEmoji().isPresent }
        .filter { event ->
            event.emoji.asCustomEmoji().takeIf { it.isPresent }?.get()?.name?.let { name ->
                listener = listeners.firstOrNull { it.isTriggered(name) }
            }
            event.emoji.asUnicodeEmoji().takeIf { it.isPresent }?.get()?.raw?.let { name ->
                listener = listeners.firstOrNull { it.isTriggered(name) }
            }
            listener != null
        }
        .map(ReactionAddEvent::toReactionAddEvent)
        .flatMap {
            listener?.respond(it)
        }
        .subscribe()
}

fun GatewayDiscordClient.subscribeToReactionRemoves(listeners: List<ReactionListener>) {
    var listener: ReactionListener? = null
    eventDispatcher.on(ReactionRemoveEvent::class.java)
        .filter { it.emoji.asCustomEmoji().isPresent }
        .filter { event ->
            event.emoji.asCustomEmoji().takeIf { it.isPresent }?.get()?.name?.let { name ->
                listener = listeners.firstOrNull { it.isTriggered(name) }
            }
            listener != null
        }
        .map(ReactionRemoveEvent::toReactionRemoveEvent)
        .flatMap {
            listener?.respond(it)
        }
        .subscribe()
}

@Suppress("UNUSED_VARIABLE")
fun GatewayDiscordClient.launchDatabaseSyncRoutine(timeInterval: Long) {
    val job = CoroutineScope(Dispatchers.IO).launchPeriodicAsync(timeInterval) {
        val usersQuery = "SELECT * FROM users;"
        val users = DatabaseClient.query(usersQuery, UserDataListTransformer())!!
        eventDispatcher.publish(DatabaseSyncEvent(this, ShardInfo.create(0, 1), users))
    }
}

fun GatewayDiscordClient.subscribeToDatabaseSync() {
    eventDispatcher.on(DatabaseSyncEvent::class.java).map { event ->
        event.getUsers().forEach { user ->
            val alignmentRole = AlignmentDefinitions.calculateRole(user.chaoticPoints, user.lawfulPoints, user.goodPoints, user.evilPoints)
            val guild = getGuildById(Snowflake.of(user.guildId)).block()
            val memberToUpdate = guild?.members?.collectList()?.block()?.firstOrNull {
                it.username == user.userName
            }
            if (guild?.roles?.collectList()?.block()?.map { role -> role.id }?.contains(Snowflake.of(ConfigUtil.alignmentRoles["True Neutral"]!!)) == true) {
                memberToUpdate?.let { member ->
                    val usersCurrentAlignmentRole = member.roles
                        .map { role ->
                            role.name
                        }.filter { roleName ->
                            ConfigUtil.alignmentRoles.keys.contains(roleName)
                        }.collectList()
                    usersCurrentAlignmentRole.block()?.firstOrNull()?.let { currentRole ->
                        if (alignmentRole != currentRole) {
                            member.removeRole(Snowflake.of(ConfigUtil.alignmentRoles[currentRole]!!)).block()
                            member.addRole(Snowflake.of(ConfigUtil.alignmentRoles[alignmentRole]!!)).block()
                        }
                    }
                }
            }
        }
        Mono.empty<Message>()
    }.subscribe()
}
