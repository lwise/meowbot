package com.lwise.alignment

import com.lwise.util.log

class AlignmentDefinitions {
    companion object {
        val EMOJI_NAMES = listOf("lawful", "chaotic", "good", "evil")
        private const val ARBITRARY_ALIGNMENT_THRESHOLD = 15

        fun calculateRole(chaotic: Int, lawful: Int, good: Int, evil: Int): String {
            val goodnessPoints = good - evil
            val lawfulnessPoints = lawful - chaotic
            val goodness = when {
                goodnessPoints > ARBITRARY_ALIGNMENT_THRESHOLD -> "Good"
                (goodnessPoints * -1) > ARBITRARY_ALIGNMENT_THRESHOLD -> "Evil"
                else -> "Neutral"
            }
            val lawfulness = when {
                lawfulnessPoints > ARBITRARY_ALIGNMENT_THRESHOLD -> "Lawful"
                (lawfulnessPoints * -1) > ARBITRARY_ALIGNMENT_THRESHOLD -> "Chaotic"
                else -> "Neutral"
            }
            val roleName = "$lawfulness $goodness"
            log(this::class.java.name, "role: $roleName")
            if (roleName == "Neutral Neutral") return "True Neutral"
            return roleName
        }
    }
}
