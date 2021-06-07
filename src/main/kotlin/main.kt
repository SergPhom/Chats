package ru.netology


fun main() {
    fun add(idOne: Int, idTwo: Int, text: String): Boolean{
        return ChatsService.addMessage(idOne,idTwo, text)
    }
//    add(1, 2, "Hello")
//    add(1, 2, "bye")
//    add(1,3,"hello")
//    add(1,3,"bye")
//    add(1,3,"more")
//    add(2,3,"next")
//    add(2,3,"CI")
//    ChatsService.deleteMessage(1,1)
//    ChatsService.getMessagesFromChat(1, 1, 2).forEach {  println(it) }
//    println()
//    ChatsService.getChats(1).forEach { chat -> println(chat)}
//    println()
//    ChatsService.getUsersChats().forEach { chat -> println(chat)}
//    println()
//    println(ChatsService.getUnreadChatsCount(1))

    ChatsService.addMessage(1,2,"first")
    ChatsService.addMessage(1,2,"second")
    ChatsService.addMessage(1,2,"third")
    ChatsService.addMessage(1,2,"forth")

    val result = ChatsService.getMessagesFromChat(1,2,3).map{it.text}
    println(result)
}