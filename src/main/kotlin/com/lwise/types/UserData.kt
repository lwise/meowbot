package com.lwise.types

data class UserData(
    val id: Long,
    val userName: String,
    val chaoticPoints: Int,
    val lawfulPoints: Int,
    val goodPoints: Int,
    val evilPoints: Int,
    val fishPoints: Int
)
