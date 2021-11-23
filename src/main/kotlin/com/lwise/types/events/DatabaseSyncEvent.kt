package com.lwise.types.events

import com.lwise.types.UserData
import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.Guild
import discord4j.core.event.domain.Event
import discord4j.gateway.ShardInfo
import reactor.core.publisher.Mono

class DatabaseSyncEvent(
    private val gateway: GatewayDiscordClient,
    shardInfo: ShardInfo,
    private val guildId: Snowflake,
    private val users: List<UserData>
) : Event(gateway, shardInfo) {
    fun getGuild(): Mono<Guild> { return gateway.getGuildById(guildId) }
    fun getUsers(): List<UserData> { return users }
}
