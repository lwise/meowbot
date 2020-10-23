package com.lwise.listeners.messages

import com.lwise.listeners.Listener

interface MessageListener : Listener {
    override val trigger: String
}