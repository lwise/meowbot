package com.lwise

import com.lwise.listeners.messages.AdviceListener
import com.lwise.listeners.messages.AlignmentOptInListener
import com.lwise.listeners.messages.CatPictureListener
import com.lwise.listeners.messages.ClearQueueListener
import com.lwise.listeners.messages.MealSuggestionListener
import com.lwise.listeners.messages.MeowListener
import com.lwise.listeners.messages.SecretSantaListener
import com.lwise.listeners.messages.musicplayer.PauseMusicListener
import com.lwise.listeners.messages.musicplayer.QueueSongListener
import com.lwise.listeners.messages.musicplayer.RemoveFromQueueListener
import com.lwise.listeners.messages.musicplayer.ResumeMusicListener
import com.lwise.listeners.messages.musicplayer.ShowQueueListener
import com.lwise.listeners.messages.musicplayer.ShuffleMusicListener
import com.lwise.listeners.messages.musicplayer.SkipSongListener
import com.lwise.listeners.messages.musicplayer.VoiceJoinListener
import com.lwise.listeners.reactions.AlignmentReactionListener
import com.lwise.listeners.reactions.FishReactionListener

object ListenerRegistry {
    val messageListeners = listOf(
        QueueSongListener(), // Top priority in case a song gets queued with a keyword in name
        MeowListener(),
        AlignmentOptInListener(),
        CatPictureListener(),
        MealSuggestionListener(),
        AdviceListener(),
        SecretSantaListener(),
        VoiceJoinListener(),
        ShowQueueListener(),
        RemoveFromQueueListener(),
        ClearQueueListener(),
        PauseMusicListener(),
        ResumeMusicListener(),
        SkipSongListener(),
        ShuffleMusicListener()
    )

    val reactionListeners = listOf(
        AlignmentReactionListener(),
        FishReactionListener()
    )
}
