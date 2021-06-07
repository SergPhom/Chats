package ru.netology

import java.security.MessageDigest

data class Chat(
    val id: Int,
    val userIds: Pair<Int, Int>,
    val messages: MutableList<Message> = mutableListOf()
)