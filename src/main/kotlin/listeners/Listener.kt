package listeners

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import reactor.core.publisher.Mono

interface Listener {
    val trigger: Any
    fun respond(channel: MessageChannel): Mono<Message>
}