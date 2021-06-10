package ru.netology

fun main() {

    fun add(idOne: Int, idTwo: Int, text: String): Boolean{
        return ChatsService.addMessage(idOne,idTwo, text)
    }
    ChatsService.clear()
    add(1, 2, "Hello")
    add(2, 1, "Hey")
    add(1,2,"bye")
    add(2,1,"chao")
    add(1,3,"hello")
    add(1,3,"bye")
    add(1,3,"more")
    add(2,3,"next")
    add(2,3,"CI")

    ChatsService.getUsersChats().forEach { chat -> println(chat)}
    println()
    ChatsService.getChats(1).forEach { chat -> println(chat)}
    println()
    ChatsService.getUsersChats().forEach { chat -> println(chat)}
    println()
    println(ChatsService.getUnreadChatsCount(1))
    println()

    ChatsService.clear()
    add(1,2,"first")
    add(1,2,"second")
    add(1,2,"third")
    add(1,2,"forth")

    val result = ChatsService.getMessagesFromChat(1,2,3)
        .map{it.text}
    println(result)
}