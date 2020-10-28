package com.lwise.text.generators

import kotlin.random.Random

class MeowMessageGenerator {

    val meowMessage = listOf<String>("meow", "nya", "miaou", "miao", "mew", "miau", "miaow", "mnou")
    val preMessage = listOf<String>("...", "~~~~ ", "<3 ", ":3  ")
    val postMessage = listOf<String>(
        "  (づ｡◕‿‿◕｡)づ", " ≧◡≦", "!!", "!", "...", " /ᐠ｡ꞈ｡ᐟ\\\\ ", "  /ᐠ｡‸｡ᐟ\\\\ ", " /ᐠ｡ꞈ｡ᐟ✿\\\\ ",
        " /ᐠ｡ꞈ｡ᐟ❁\\\\∫", " /ᐠ｡▿｡ᐟ\\\\ *ᵖᵘʳʳ*", " /ᐠ｡ﻌ｡ᐟ\\\\", " /ᐠ.ꞈ.ᐟ\\\\", " ✧/ᐠ-ꞈ-ᐟ\\\\ ", " (^..^)ﾉ",
        " /ᐠ. ⱉ .ᐟ\\\\ﾉ   ", " /ᐠ｡ⱉ｡ᐟ\\\\ﾉ ᶠᵉᵉᵈ ᵐᵉ", "~~~", "!!!", ".......", "!", "~~~", "/ᐠ｡ퟑ｡ᐟ\\\\",
        " <3 ", "  \\\\/ᐠ-ᆽ-ᐟ \\\\ "
    )

    fun generateText(usePrefix: Boolean = true, usePostfix: Boolean = true): String {
        val arguments: MutableList<String> = mutableListOf<String>()

        if (usePrefix && Random.nextInt(2) == 1) {
            arguments.add(preMessage.get(Random.nextInt(preMessage.size)))
        }

        arguments.add(meowMessage.get(Random.nextInt(meowMessage.size)))

        if (usePostfix && Random.nextInt(2) == 1) {
            arguments.add(postMessage.get(Random.nextInt(postMessage.size)))
        }

        return arguments.joinToString(separator = "")
    }
}
