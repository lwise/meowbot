package com.lwise.util.player

import com.lwise.util.log
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class TrackScheduler(private val player: AudioPlayer) : AudioEventAdapter() {
    private val queue: BlockingQueue<AudioTrack>
    val isEmpty: Boolean
        get() = queue.isEmpty()

    init {
        queue = LinkedBlockingQueue()
    }

    fun queue(track: AudioTrack) {
        if (!player.startTrack(track, true)) {
            queue.offer(track)
        }
    }

    fun removeFromQueue(index: Int): AudioTrack? {
        val removed = if (index >= 0 && index < queue.size) {
            val trackToRemove = queue.toList()[index]
            queue.remove(trackToRemove)
            trackToRemove
        } else null
        log("meow", "${removed?.info?.title}")
        return removed
    }

    fun getQueueTrackList(): List<AudioTrack> {
        return queue.toList()
    }

    fun clearQueue() {
        queue.clear()
    }

    fun nextTrack() {
        player.startTrack(queue.poll(), false)
    }

    fun getQueueSize(): Int {
        return queue.size
    }

    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {
        endReason?.let {
            if (it.mayStartNext) {
                nextTrack()
            }
        }
    }
}
