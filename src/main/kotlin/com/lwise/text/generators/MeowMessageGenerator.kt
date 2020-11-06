package com.lwise.text.generators

import kotlin.random.Random

class MeowMessageGenerator {

    private val meowMessage = listOf("meow", "nya", "miaou", "miao", "mew", "miau", "miaow", "mnou")
    private val preMessage = listOf("...", "~~~~ ", "<3 ", ":3  ")
    private val postMessage = listOf(
        "  (づ｡◕‿‿◕｡)づ", " ≧◡≦", "!!", "!", "...", " /ᐠ｡ꞈ｡ᐟ\\\\ ", "  /ᐠ｡‸｡ᐟ\\\\ ", " /ᐠ｡ꞈ｡ᐟ✿\\\\ ",
        " /ᐠ｡ꞈ｡ᐟ❁\\\\∫", " /ᐠ｡▿｡ᐟ\\\\ *ᵖᵘʳʳ*", " /ᐠ｡ﻌ｡ᐟ\\\\", " /ᐠ.ꞈ.ᐟ\\\\", " ✧/ᐠ-ꞈ-ᐟ\\\\ ", " (^..^)ﾉ",
        " /ᐠ. ⱉ .ᐟ\\\\ﾉ   ", " /ᐠ｡ⱉ｡ᐟ\\\\ﾉ ᶠᵉᵉᵈ ᵐᵉ", "~~~", "!!!", ".......", "!", "~~~", "/ᐠ｡ퟑ｡ᐟ\\\\",
        " <3 ", "  \\\\/ᐠ-ᆽ-ᐟ \\\\ "
    )

    fun generateText(usePrefix: Boolean = true, usePostfix: Boolean = true): String {
        val arguments: MutableList<String> = mutableListOf()

        if (usePrefix && Random.nextInt(2) == 1) {
            arguments.add(preMessage[Random.nextInt(preMessage.size)])
        }

        arguments.add(meowMessage[Random.nextInt(meowMessage.size)])

        if (usePostfix && Random.nextInt(2) == 1) {
            arguments.add(postMessage[Random.nextInt(postMessage.size)])
        }

        return arguments.joinToString(separator = "")
    }
}
