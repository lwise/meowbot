package com.lwise.listeners

import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

interface Listener<T> {

    fun respond(responseVector: T): Mono<Message>
    fun isTriggered(content: String): Boolean
}
