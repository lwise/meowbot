package util

import com.lwise.listeners.Listener
import com.lwise.listeners.messages.MessageListener
import com.lwise.listeners.reactions.ReactionListener

@Suppress("UNCHECKED_CAST")
class MockMeowBot(val listeners: List<Listener<*>>) {

    fun getResponseToMessage(inputMessage: String): String? {

        val listener = (listeners as List<MessageListener>)
            .firstOrNull { it.isTriggered(inputMessage) }
        listener?.let {
            return it.getResponseMessage()
        }
        return null
    }

    fun firesOnReaction(emojiName: String): Boolean {
        val listener = (listeners as List<ReactionListener>)
            .firstOrNull { it.isTriggered(emojiName) }
        return listener != null
    }
}
