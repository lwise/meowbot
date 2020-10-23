package util

import com.lwise.listeners.Listener
import com.lwise.listeners.messages.MessageListener

@Suppress("UNCHECKED_CAST")
class MockMeowBot(val listeners: List<Listener>) {
    fun getResponseToMessage(inputMessage: String): String? {
        val listener = (listeners as List<MessageListener>).firstOrNull { it.trigger.equals(inputMessage, ignoreCase = true) }
        listener?.let {
            return it.response as String
        }
        return null
    }
}
