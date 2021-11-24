package com.lwise.types.events

import com.lwise.types.UserData
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.Event
import discord4j.gateway.ShardInfo

class DatabaseSyncEvent(
    gateway: GatewayDiscordClient,
    shardInfo: ShardInfo,
    private val users: List<UserData>
) : Event(gateway, shardInfo) {
    fun getUsers(): List<UserData> { return users }
}
