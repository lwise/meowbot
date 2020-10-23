package listeners.messages

import listeners.Listener

interface MessageListener : Listener {
    override val trigger: String
}