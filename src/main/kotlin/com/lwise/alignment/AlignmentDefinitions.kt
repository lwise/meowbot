package com.lwise.alignment

import com.lwise.util.log

class AlignmentDefinitions {
    companion object {
        val EMOJI_NAMES = listOf("lawful", "chaotic", "good", "evil")
        val ALIGNMENT_ROLES = mapOf(
            Pair("Chaotic Evil", "769426100178386965"),
            Pair("Chaotic Good", "769425366526853130"),
            Pair("Chaotic Neutral", "769424331037999104"),
            Pair("True Neutral", "769424037201444944"),
            Pair("Lawful Neutral", "769424130629959691"),
            Pair("Lawful Good", "769424575116083230"),
            Pair("Lawful Evil", "769425124755505183"),
            Pair("Neutral Good", "769611488868433930"),
            Pair("Neutral Evil", "769611569465786429")
        )
        private const val ARBITRARY_ALIGNMENT_THRESHOLD = 20

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
