package com.lwise

import com.lwise.listeners.messages.AdviceListener
import com.lwise.listeners.messages.AlignmentOptInListener
import com.lwise.listeners.messages.CatPictureListener
import com.lwise.listeners.messages.ClearQueueListener
import com.lwise.listeners.messages.MeowListener
import com.lwise.listeners.messages.PauseMusicListener
import com.lwise.listeners.messages.QueueSongListener
import com.lwise.listeners.messages.RemoveFromQueueListener
import com.lwise.listeners.messages.ResumeMusicListener
import com.lwise.listeners.messages.SecretSantaListener
import com.lwise.listeners.messages.ShowQueueListener
import com.lwise.listeners.messages.SkipSongListener
import com.lwise.listeners.messages.VoiceJoinListener
import com.lwise.listeners.reactions.AlignmentReactionListener
import com.lwise.listeners.reactions.FishReactionListener

object ListenerRegistry {
    val messageListeners = listOf(
        MeowListener(),
        AlignmentOptInListener(),
        CatPictureListener(),
        AdviceListener(),
        SecretSantaListener(),
        VoiceJoinListener(),
        QueueSongListener(),
        ShowQueueListener(),
        RemoveFromQueueListener(),
        ClearQueueListener(),
        PauseMusicListener(),
        ResumeMusicListener(),
        SkipSongListener()
    )

    val reactionListeners = listOf(
        AlignmentReactionListener(),
        FishReactionListener()
    )
}
