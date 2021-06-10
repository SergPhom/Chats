package ru.netology

data class Message(
    val id: Int,
    val fromId: Int,
    val toId: Int,
    val date: Int = 0,
    val text: String = "",
    val unread: Boolean = true,
){
    override fun toString(): String {
        return "||id=$id, from=$fromId, to=$toId, $text, $unread||"
    }
}
